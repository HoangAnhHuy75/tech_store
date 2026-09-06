import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import LoginForm from "@/components/auth/loginForm";
import Link from "next/link";
export default function LoginPage() {
    return (
        <div className="h-screen w-screen flex justify-center items-center bg-slate-100">
            <div className="sm:shadow-xl sm:w-100  p-8 bg-white rounded-xl">
                <h1 className="font-semibold flex justify-center text-3xl">Welcome back</h1>
                <p className="text-sm flex justify-center opacity-50">Enter your credentials to access your account</p>
                <LoginForm/>
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