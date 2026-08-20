import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Link from "next/link";
export default function LoginPage() {
    return (
        <div className="h-screen w-screen flex justify-center items-center bg-slate-100">
            <div className="sm:shadow-xl sm:w-100  p-8 bg-white rounded-xl">
                <h1 className="font-semibold flex justify-center text-3xl">Welcome back</h1>
                <p className="text-sm flex justify-center opacity-50">Enter your credentials to access your account</p>
                <form action="" className="space-y-4 my-5">
                    <div className="space-y-1">
                        <Label>Email</Label>
                        <Input type="email" placeholder="anhhuy@gmail.com" />
                    </div>
                    <div className="space-y-1">
                        <Label>Password</Label>
                        <Input type="password" placeholder="Enter your password" />
                    </div>
                    <div className="flex items-center justify-between">
                        <div className="flex items-center space-x-1">
                            <Checkbox/>
                            <Label className="text-sm opacity-70">Remember me</Label>
                        </div>
                        <a className="text-sm opacity-70" href="">Forgot password?</a>
                    </div>
                    <div>
                        <button className="border-2 w-full bg-black rounded-md text-sm text-white px-4 py-1 shadow">Đăng nhập</button>
                    </div>
                </form>
                <p className="text-center">
                    Need to create account?{' '}
                    <Link className="text-indigo-500 hover:underline" href="/register">
                        Create Account
                    </Link>{' '}
                </p>
            </div>
        </div>
    );
}