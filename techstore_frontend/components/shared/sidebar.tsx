"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { useRouter } from "next/navigation";
import { Home, Tags, Package, Settings, Users, ShoppingCart, LogOut, MessageCircle } from "lucide-react";
import { logoutUser } from "@/services/authService";

const menuGroups = [
    {
        title: "Quản lý",
        items: [
            { href: "/admin", label: "Trang chủ", icon: Home },
            { href: "/admin/category", label: "Loại sản phẩm", icon: Tags },
            { href: "/admin/product", label: "Sản phẩm", icon: Package },
            { href: "/admin/specifications", label: "Thông số kỹ thuật", icon: Settings },
        ],
    },
    {
        title: "Khác",
        items: [
            { href: "/admin/users", label: "Người dùng", icon: Users },
            { href: "/admin/orders", label: "Đơn hàng", icon: ShoppingCart },
            { href: "/admin/messages", label: "Tin nhắn", icon: MessageCircle },
        ],
    },
];

export default function SideBar() {
    const pathname = usePathname();
    const router = useRouter();

    const handleLogout = async () => {
        try {
            await logoutUser();
        } finally {
            router.push("/");
        }
    };

    const isActive = (href: string) => href === "/admin"
        ? pathname === href
        : pathname === href || pathname.startsWith(`${href}/`);

    return (
        <aside className="fixed left-0 top-0 z-40 h-screen w-64 border-r bg-white">
            <div className="flex h-16 items-center border-b px-6">
                <Link href="/admin" className="text-2xl font-extrabold tracking-tight text-gray-900">
                    Tech<span className="text-blue-600">Store</span>
                </Link>
            </div>

            <nav className="p-4">
                {menuGroups.map((group, groupIndex) => (
                    <div key={group.title} className={groupIndex > 0 ? "mt-8" : ""}>
                        <p className="mb-3 px-3 text-xs font-semibold uppercase tracking-wider text-gray-400">{group.title}</p>
                        <div className="space-y-1">
                            {group.items.map((item) => {
                                const active = isActive(item.href);
                                const Icon = item.icon;

                                return (
                                    <Link
                                        key={item.href}
                                        href={item.href}
                                        aria-current={active ? "page" : undefined}
                                        className={`flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition ${active
                                            ? "bg-blue-600 text-white shadow-sm hover:bg-blue-700"
                                            : "text-gray-600 hover:bg-gray-100 hover:text-gray-900"
                                            }`}
                                    >
                                        <Icon className="h-5 w-5" />
                                        {item.label}
                                    </Link>
                                );
                            })}
                        </div>
                    </div>
                ))}
                <button
                    type="button"
                    onClick={handleLogout}
                    className="mt-8 flex w-full items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-red-50 hover:text-red-600"
                >
                    <LogOut className="h-5 w-5" />
                    Đăng xuất
                </button>
            </nav>
        </aside>
    );
}
