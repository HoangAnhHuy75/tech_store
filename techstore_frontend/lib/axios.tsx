import axios from "axios"
import Cookies from "js-cookie";

const apiClient = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-Type": "application/json",
    },
});


apiClient.interceptors.request.use(
    (config) => {
        const method = config.method?.toUpperCase();
        const url = config.url || "";

        // Kiểm tra đúng các Endpoint PUBLIC (Khớp với SecurityConfig ở Backend)
        const isPublicEndpoint =
            (method === "POST" && url.includes("/auth/login")) ||
            (method === "POST" && url.includes("/auth/introspect")) ||
            (method === "POST" && url.includes("/users")); // Chỉ POST /users (đăng ký) mới là public

        // Nếu KHÔNG PHẢI public endpoint thì mới đính kèm Token
        if (!isPublicEndpoint) {
            const token = Cookies.get("token");
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
        }

        return config;
    },
    (error) => Promise.reject(error)
);

export default apiClient;
