import { useAuthStore } from "@/stores/authStore";
import axios from "axios"
const BASE_URL = "http://localhost:8080/api/v1";

const apiClient = axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
});

// Đánh dấu request đã được thử refresh
declare module "axios" {
    export interface InternalAxiosRequestConfig {
        _retry?: boolean;
    }
}

// Dùng chung một Promise để tránh refresh nhiều lần đồng thời
let refreshPromise: Promise<string> | null = null;


apiClient.interceptors.request.use(
    (config) => {
        const method = config.method?.toUpperCase();
        const url = config.url || "";

        const isPublicEndpoint =
            (method === "POST" && url.includes("/auth/login")) ||
            (method === "POST" && url.includes("/auth/introspect")) ||
            (method === "POST" && url.includes("/users"));

        if (!isPublicEndpoint) {
            const token = useAuthStore.getState().token;

            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
        }

        return config;
    },
    (error) => Promise.reject(error)
);

// Response Interceptor
apiClient.interceptors.response.use(
    (response) => response, async (error) => {
        if (!error) {
            return Promise.reject(new Error("Unknown HTTP error"));
        }
        const originalRequest = error.config;

        // Chỉ xử lý khi nhận được HTTP 401
        if (error.response?.status !== 401 || !originalRequest || originalRequest._retry) {
            return Promise.reject(error);
        }

        // Không refresh nếu chính request login/refresh/introspect bị lỗi
        const url = originalRequest.url || "";

        if (url.includes("/auth/login") || url.includes("/auth/refresh") || url.includes("/auth/introspect")) {
            return Promise.reject(error);
        }

        originalRequest._retry = true;

        try {
            // Nếu chưa có request refresh thì tạo mới
            if (!refreshPromise) {
                refreshPromise = axios.post(`${BASE_URL}/auth/refresh`, {}, { withCredentials: true })
                    .then((response) => {
                        const newToken = response.data?.result?.token;
                        if (!newToken) {
                            throw new Error("Không nhận được Access Token mới");
                        }
                        useAuthStore.getState().setToken(newToken);
                        return newToken;
                    });

                // Reset Promise khi refresh thành công hoặc thất bại
                refreshPromise.then(() => { refreshPromise = null }, () => { refreshPromise = null }
                );
            }

            // Các request 401 khác sẽ chờ cùng một lần refresh
            const newToken = await refreshPromise;

            // Gắn Access Token mới vào request ban đầu
            originalRequest.headers.Authorization = `Bearer ${newToken}`;

            // Gửi lại request ban đầu
            return apiClient(originalRequest);

        } catch (refreshError) {
            // Refresh thất bại: xóa trạng thái đăng nhập
            localStorage.removeItem("token");

            // Nếu đang dùng Zustand Auth Store
            // useAuthStore.getState().clearAuth();

            return Promise.reject(refreshError);
        }
    }
);

export default apiClient;
