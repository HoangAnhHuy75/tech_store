
"use client";
import {
    Plus,
    Package,
    Search,
} from "lucide-react";
import CreateProductForm from "@/components/product/createProductForm";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input";
import useProduct from "@/hooks/useProduct";

export default function ProductPage() {
    const [openCreateForm, setOpenCreateForm] = useState(false);
    const [search, setSearch] = useState("");

    const { products, fetchProducts } = useProduct();

    useEffect(() => {
        fetchProducts();
    }, []);

    const filteredProducts =
        products.filter((product) => `${product.name} ${product.category.name}`.toLocaleLowerCase("vi").includes(search.toLocaleLowerCase("vi"))
    );

    return (
        <div className="p-8 border-2">
            <div className="mb-8 flex items-center justify-between">
                <div>
                    <div className="flex items-center gap-3">
                        <div className="rounded-lg bg-blue-100 p-2">
                            <Package className="h-6 w-6 text-blue-600" />
                        </div>
                        <h1 className="text-2xl font-bold text-gray-900">
                            Sản phẩm
                        </h1>
                    </div>
                    <p className="mt-2 text-sm text-gray-500">
                        Quản lý các sản phẩm trong cửa hàng.
                    </p>
                </div>
                <Button onClick={() => setOpenCreateForm(true)}>
                    <Plus className="mr-2 h-4 w-4" />
                    Thêm sản phẩm
                </Button>
            </div>

            <div className="mb-6 flex gap-3">
                <div className="relative max-w-sm flex-1">
                    <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-gray-400" />
                    <Input
                        placeholder="Tìm kiếm sản phẩm..."
                        className="pl-9"
                        value={search}
                        onChange={(event) => setSearch(event.target.value)}
                    />
                </div>
            </div>

            <div className="overflow-x-auto rounded-xl border bg-white shadow-sm">
                <table className="w-full">
                    <thead className="border-b bg-gray-50">
                        <tr>
                            <th className="px-6 py-4 text-left text-sm font-semibold text-gray-700">ID</th>
                            <th className="px-6 py-4 text-left text-sm font-semibold text-gray-700">Tên sản phẩm</th>
                            <th className="px-6 py-4 text-left text-sm font-semibold text-gray-700">Loại sản phẩm</th>
                            <th className="px-6 py-4 text-right text-sm font-semibold text-gray-700">Giá</th>
                            <th className="px-6 py-4 text-right text-sm font-semibold text-gray-700">Số lượng</th>
                            <th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y">
                        {filteredProducts.length === 0 ? (
                            <tr>
                                <td colSpan={6} className="px-6 py-8 text-center text-sm text-gray-500">
                                    {search ? "Không tìm thấy sản phẩm phù hợp." : "Chưa có sản phẩm nào."}
                                </td>
                            </tr>
                        ) : (
                            filteredProducts.map((product) => (
                                <tr key={product.id} className="transition hover:bg-gray-50">
                                    <td className="px-6 py-4 text-sm text-gray-500">{product.id}</td>
                                    <td className="px-6 py-4 font-medium text-gray-900">{product.name}</td>
                                    <td className="px-6 py-4 text-sm text-gray-500">{product.category.name}</td>
                                    <td className="px-6 py-4 text-right text-sm font-medium text-gray-900">
                                        {(product.price ?? 0).toLocaleString("vi-VN")}₫
                                    </td>
                                    <td className="px-6 py-4 text-right text-sm text-gray-700">{product.quantity}</td>
                                    <td className="px-6 py-4 text-center">
                                        <span className={`rounded-full px-3 py-1 text-xs font-medium ${product.isActive
                                            ? "bg-green-50 text-green-700"
                                            : "bg-gray-100 text-gray-600"
                                            }`}>
                                            {product.isActive ? "Đang bán" : "Ngừng bán"}
                                        </span>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            <Dialog open={openCreateForm} onOpenChange={setOpenCreateForm}>
                <DialogContent className="lg:max-w-[1200px] sm:max-w-[650px] overflow-auto h-[60vh]">
                    <DialogHeader>
                        <DialogTitle className="text-center font-bold mb-2">THÊM SẢN PHẨM</DialogTitle>
                    </DialogHeader>
                    <CreateProductForm onCreated={() => void fetchProducts()} />
                </DialogContent>
            </Dialog>
        </div>
    );
}
