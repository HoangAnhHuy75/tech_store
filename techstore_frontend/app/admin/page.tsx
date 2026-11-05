"use client";

import { useEffect, useState } from "react";
import { ClipboardList, Package, Tags, Users } from "lucide-react";
import { findAll as findAllProducts } from "@/services/productService";
import { findAll as findAllCategories } from "@/services/categoryService";
import apiClient from "@/lib/axios";

type DashboardStats = { products: number; categories: number; users: number; orders: number };
const emptyStats: DashboardStats = { products: 0, categories: 0, users: 0, orders: 0 };

async function fetchResourceCount(path: string) {
    try {
        const response = await apiClient.get<{ result?: unknown[] }>(path);
        return Array.isArray(response.data.result) ? response.data.result.length : 0;
    } catch (error) {
        console.error(`Không thể lấy dữ liệu ${path}:`, error);
        return 0;
    }
}

export default function AdminPage() {
    const [stats, setStats] = useState<DashboardStats>(emptyStats);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadDashboard = async () => {
            try {
                const [products, categories, users, orders] = await Promise.all([
                    findAllProducts().then((items) => items.length).catch(() => 0),
                    findAllCategories().then((items: unknown[]) => items.length).catch(() => 0),
                    fetchResourceCount("/users"),
                    fetchResourceCount("/orders"),
                ]);
                setStats({ products, categories, users, orders });
            } finally {
                setLoading(false);
            }
        };
        void loadDashboard();
    }, []);

    const cards = [
        { label: "Sản phẩm", value: stats.products, icon: Package, color: "blue" },
        { label: "Loại sản phẩm", value: stats.categories, icon: Tags, color: "violet" },
        { label: "Người dùng", value: stats.users, icon: Users, color: "emerald" },
        { label: "Đơn hàng", value: stats.orders, icon: ClipboardList, color: "orange" },
    ];

    return <><header className="flex h-16 items-center justify-between border-b bg-white px-8"><h2 className="text-lg font-semibold text-gray-900">Trang chủ</h2><div className="flex items-center gap-3"><div className="flex h-9 w-9 items-center justify-center rounded-full bg-blue-100 text-sm font-semibold text-blue-600">A</div><div><p className="text-sm font-medium text-gray-900">Admin</p><p className="text-xs text-gray-500">Quản trị viên</p></div></div></header><div className="p-8"><div className="mb-8"><h1 className="text-2xl font-bold text-gray-900">Xin chào, Admin 👋</h1><p className="mt-1 text-sm text-gray-500">Tổng quan dữ liệu hiện tại của TechStore.</p></div><div className="grid grid-cols-1 gap-5 md:grid-cols-2 xl:grid-cols-4">{cards.map((card) => { const Icon = card.icon; return <div key={card.label} className="rounded-xl border bg-white p-5 shadow-sm"><div className="flex items-center justify-between"><p className="text-sm text-gray-500">{card.label}</p><div className="rounded-lg bg-blue-50 p-2 text-blue-600"><Icon className="h-5 w-5" /></div></div><p className="mt-4 text-3xl font-bold text-gray-900">{loading ? "—" : card.value}</p></div>; })}</div></div></>;
}
