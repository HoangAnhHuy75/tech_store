"use client";

import { startTransition, useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import Cookies from "js-cookie";
import SideBar from "@/components/shared/sidebar";

export default function AdminLayout({children} : {children: React.ReactNode} ) {
    return (
        <div className="min-h-screen bg-gray-50">
            <SideBar />
            <main className="ml-64 min-h-screen">{children}</main>
        </div>
    );
}
