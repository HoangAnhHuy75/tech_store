// services/authService.ts
import apiClient from "@/lib/axios";
import Cookies from "js-cookie";
// Hàm Đăng ký
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

export const loginUser = async (data: LoginRequest) => {
  const response = await apiClient.post("/auth/login", data);
  const token = response.data?.result?.token;
  if (token) {
    Cookies.set("token", token, { expires: 7 }); // Lưu Token vào Cookie 7 ngày
  }
  return response.data;
};