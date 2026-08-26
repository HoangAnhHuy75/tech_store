'use client'
import { Label } from '../ui/label';
import { Input } from '../ui/input';
import { Checkbox } from '../ui/checkbox';
import { Button } from '../ui/button';
import { loginSchema } from '@/lib/validations/auth';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useRouter } from 'next/navigation';
import { loginUser } from '@/services/authService';
type LoginFormValues = z.infer<typeof loginSchema>;
export default function LoginForm() {
    const router = useRouter();
    const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<LoginFormValues>({
        resolver: zodResolver(loginSchema)
    });

    const onSubmit = async (data: LoginFormValues) => {
        try {
            const response = await loginUser({
                username: data.username,
                password: data.password,
            });
            console.log("response là",response);
            if (response?.code === 201) {
                console.log("Đăng nhập thành công:", response.message);
                router.push('/home');
            }
        } catch (error: unknown) {
            console.error("Lỗi khi đăng nhập:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 my-5">
            <div className="space-y-1">
                <Label>Username</Label>
                <Input {...register('username')} type="text" placeholder="Enter your username" />
                {
                    errors.username && (
                        <p className='text-sm text-red-500'>{errors.username.message}</p>
                    )
                }
            </div>
            <div className="space-y-1">
                <Label>Password</Label>
                <Input {...register('password')} type="password" placeholder="Enter your password" />
                {
                    errors.password && (
                        <p className='text-sm text-red-500'>{errors.password.message}</p>
                    )
                }
            </div>
            <div className="justify-between flex items-center">
                <div className="flex items-center space-x-2">
                    <Checkbox id="remember" />
                    <Label className="opacity-50 text-sm" htmlFor="remember">Remember me</Label>
                </div>
                <a href="" className="opacity-50 text-sm">Forgot password?</a>
            </div>
            <div>
                <Button disabled={isSubmitting} type='submit' className='w-full'>{isSubmitting ? "Đang đăng nhập ..." : "Đăng nhập"}</Button>
            </div>
        </form>
    );
}