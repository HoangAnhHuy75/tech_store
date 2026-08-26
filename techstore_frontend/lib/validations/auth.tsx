import { z } from "zod";
const registerSchema = z.object({
    name: z.string().min(1, "Họ tên không được để trống"),
    username: z.string().min(1, "Username không được để trống"),
    password: z.string().min(6, "Mật khẩu phải có ít nhất 6 ký tự"),
    phone: z.string().regex(/^[0-9]{10}$/, "Số điện thoại không hợp lệ"),
    confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
    message: "Mật khẩu xác nhận không trùng khớp",
    path: ["confirmPassword"],
});

const loginSchema = z.object({
    username: z.string().min(1,"Username không được để trống"),
    password: z.string().min(1,"Mật khẩu không được để trống")
});
export {registerSchema, loginSchema};