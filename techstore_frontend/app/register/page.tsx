import Link from "next/link";
import RegisterForm from "@/components/auth/registerForm";

export default function RegisterPage() {
  return (
    <div className="h-screen w-screen flex justify-center items-center bg-slate-100">
      <div className="sm:shadow-xl sm:w-100 p-8 bg-white rounded-xl">

        <h1 className="font-semibold flex justify-center text-3xl">Create an account</h1>

        <p className="text-sm flex justify-center opacity-50 mt-1">Enter your details below to create your account</p>

        <RegisterForm />

        <p className="text-center text-sm">
          Already have an account?{" "}
          <Link className="text-indigo-500 hover:underline" href="/login">
            Login
          </Link>
        </p>

      </div>
    </div>
  );
}