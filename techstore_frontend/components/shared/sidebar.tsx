import Link from "next/link";
import {Home,Tags,Package,Settings,Users,ShoppingCart} from "lucide-react";
export default function SideBar() {
    return (
        <aside className="fixed left-0 top-0 z-40 h-screen w-64 border-r bg-white">

            {/* Logo */}
            <div className="flex h-16 items-center border-b px-6">
                <h1 className="text-2xl font-extrabold tracking-tight text-gray-900">
                    Tech<span className="text-blue-600">Store</span>
                </h1>
            </div>

            {/* Menu */}
            <nav className="p-4">
                <p className="mb-3 px-3 text-xs font-semibold uppercase tracking-wider text-gray-400">
                    Quản lý
                </p>

                <div className="space-y-1">

                    {/* Trang chủ */}
                    <Link href="/admin" className="flex items-center gap-3 rounded-lg bg-blue-50 px-3 py-2.5 text-sm font-medium text-blue-600">
                        <Home className="h-5 w-5" />
                        Trang chủ
                    </Link>

                    {/* Category */}
                    <Link href="/admin/category" className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-gray-100 hover:text-gray-900">
                        <Tags className="h-5 w-5" />
                        Loại sản phẩm
                    </Link>

                    {/* Product */}
                    <Link href="/admin/product" className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-gray-100 hover:text-gray-900">
                        <Package className="h-5 w-5" />
                        Sản phẩm
                    </Link>

                    {/* Specification */}
                    <Link href="/admin/specifications" className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-gray-100 hover:text-gray-900">
                        <Settings className="h-5 w-5" />
                        Thông số kỹ thuật
                    </Link>

                </div>

                {/* Other */}
                <p className="mb-3 mt-8 px-3 text-xs font-semibold uppercase tracking-wider text-gray-400">
                    Khác
                </p>

                <div className="space-y-1">

                    <Link
                        href="/admin/users"
                        className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-gray-100 hover:text-gray-900"
                    >
                        <Users className="h-5 w-5" />
                        Người dùng
                    </Link>

                    <Link
                        href="/admin/orders"
                        className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium text-gray-600 transition hover:bg-gray-100 hover:text-gray-900"
                    >
                        <ShoppingCart className="h-5 w-5" />
                        Đơn hàng
                    </Link>

                </div>
            </nav>
        </aside>
    );

}