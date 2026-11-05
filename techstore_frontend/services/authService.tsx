import apiClient from "@/lib/axios";
import { useAuthStore } from "@/stores/authStore";
import { jwtDecode } from "jwt-decode";

interface RegisterUserRequest {
  name: string;
  username: string;
  password: string;
  phone: string;
}

interface LoginRequest {
  username: string;
  password: string;
}

export const registerUser = async (data: RegisterUserRequest) => {
  const response = await apiClient.post("/users", data);
  return response.data;
};

// Login
export const loginUser = async (data: LoginRequest) => {
  const response = await apiClient.post("/auth/login", data);
  const result = response.data?.result;
  if (result?.token) {
    const decoded = jwtDecode<{ sub: string; scope: string }>(
      result.token
    );
    useAuthStore.getState().setAuth({
      token: result.token,
      userName: decoded.sub,
      role: decoded.scope?.replace(/^ROLE_/, "") ?? null,
    });
  }
  return response.data;
};

// Refresh Access Token
export const refreshToken = async () => {
  const response = await apiClient.post("/auth/refresh");
  const result = response.data?.result;
  if (result?.token) {
    useAuthStore.getState().setToken(result.token);
  }
  return response.data;
};

// Logout
export const logoutUser = async () => {
  const token = useAuthStore.getState().token;
  try {
    await apiClient.post("/auth/logout", { token });
  } finally {
    useAuthStore.getState().clearAuth();
  }
};