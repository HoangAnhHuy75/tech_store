"use client";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import axios from "axios";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { registerUser } from "@/services/authService";
import { registerSchema } from "@/lib/validations/auth";

type RegisterFormValues = z.infer<typeof registerSchema>;
export default function RegisterForm() {
    const router = useRouter();

    const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<RegisterFormValues>({
        resolver: zodResolver(registerSchema)
    });

    const onSubmit = async (data: RegisterFormValues) => {
        try {
            await registerUser({
                name: data.name,
                username: data.username,
                phone: data.phone,
                password: data.password
            });
            alert("Đăng ký thành công!");
            router.push("/login");
        } catch (error: unknown) {
            console.error(error);
            if (axios.isAxiosError(error)) {
                alert(error.response?.data?.message || "Đăng ký thất bại!");
            } else {
                alert("Đã xảy ra lỗi không xác định!");
            }
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 my-5" >

            {/* Full Name */}
            < div className="space-y-1" >
                <Label htmlFor="name" > Full Name </Label>
                < Input id="name" placeholder="Tên của bạn" {...register("name")} />
                {
                    errors.name && (
                        <p className="text-xs text-red-500" >
                            {errors.name.message}
                        </p>
                    )
                }
            </div>

            {/* Username */}
            <div className="space-y-1" >
                <Label htmlFor="username" > Username </Label>
                < Input id="username" placeholder="anhhuy123" {...register("username")} />
                {
                    errors.username && (
                        <p className="text-xs text-red-500" >
                            {errors.username.message}
                        </p>
                    )
                }
            </div>

            {/* Phone */}
            <div className="space-y-1" >
                <Label htmlFor="phone" > Phone Number </Label>
                < Input id="phone" type="tel" placeholder="0912345678" {...register("phone")} />

                {
                    errors.phone && (
                        <p className="text-xs text-red-500" >
                            {errors.phone.message}
                        </p>
                    )
                }
            </div>

            {/* Password */}
            <div className="space-y-1" >
                <Label htmlFor="password" > Password </Label>
                < Input id="password" type="password" placeholder="Create a password" {...register("password")} />

                {
                    errors.password && (
                        <p className="text-xs text-red-500" >
                            {errors.password.message}
                        </p>
                    )
                }
            </div>

            {/* Confirm Password */}
            <div className="space-y-1" >
                <Label htmlFor="confirmPassword" >Confirm Password</Label>
                < Input id="confirmPassword" type="password" placeholder="Confirm your password" {...register("confirmPassword")} />

                {
                    errors.confirmPassword && (
                        <p className="text-xs text-red-500" >
                            {errors.confirmPassword.message}
                        </p>
                    )
                }
            </div>

            {/* Submit */}
            <Button type="submit" disabled={isSubmitting} className="w-full bg-black text-white hover:bg-slate-800 transition-colors">
                {isSubmitting ? "Đang xử lý..." : "Đăng ký"}
            </Button>

        </form>
    );
}