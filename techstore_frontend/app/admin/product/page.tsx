"use client";

import { useEffect, useState } from "react";
import { Package, Pencil, Plus, Search, Trash2 } from "lucide-react";
import CreateProductForm from "@/components/product/createProductForm";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import useProduct from "@/hooks/useProduct";
import { deleteProduct, updateProduct, type Product } from "@/services/productService";

export default function ProductPage() {
    const [openCreateForm, setOpenCreateForm] = useState(false);
    const [search, setSearch] = useState("");
    const [editingProduct, setEditingProduct] = useState<Product | null>(null);
    const [editName, setEditName] = useState("");
    const [editPrice, setEditPrice] = useState("");
    const [editQuantity, setEditQuantity] = useState("");
    const { products, fetchProducts } = useProduct();

    useEffect(() => { void fetchProducts(); }, []);

    const filteredProducts = products.filter((product) =>
        `${product.name} ${product.category.name}`.toLocaleLowerCase("vi").includes(search.toLocaleLowerCase("vi"))
    );

    const openEdit = (product: Product) => {
        setEditingProduct(product);
        setEditName(product.name);
        setEditPrice(String(product.price ?? ""));
        setEditQuantity(String(product.quantity));
    };

    const saveEdit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!editingProduct) return;
        await updateProduct(editingProduct.id, { name: editName, price: Number(editPrice), quantity: Number(editQuantity), categoryId: editingProduct.category.id, productSpecificationRequests: [] });
        setEditingProduct(null);
        await fetchProducts();
    };

    const removeProduct = async (product: Product) => {
        if (!window.confirm(`Bạn có chắc muốn xóa sản phẩm "${product.name}" không?`)) return;
        await deleteProduct(product.id);
        await fetchProducts();
    };

    return (
        <div className="min-h-full bg-slate-50 p-4 sm:p-8">
            <div className="mb-8 flex items-center justify-between"><div><div className="flex items-center gap-3"><div className="rounded-lg bg-blue-100 p-2"><Package className="h-6 w-6 text-blue-600" /></div><h1 className="text-2xl font-bold text-gray-900">Sản phẩm</h1></div><p className="mt-2 text-sm text-gray-500">Quản lý các sản phẩm trong cửa hàng.</p></div><Button onClick={() => setOpenCreateForm(true)}><Plus className="mr-2 h-4 w-4" />Thêm sản phẩm</Button></div>
            <div className="mb-6 flex gap-3"><div className="relative max-w-sm flex-1"><Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-gray-400" /><Input placeholder="Tìm kiếm sản phẩm..." className="pl-9" value={search} onChange={(event) => setSearch(event.target.value)} /></div></div>
            <div className="overflow-x-auto rounded-xl border bg-white shadow-sm"><table className="w-full"><thead className="border-b bg-gray-50"><tr><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">ID</th><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Tên sản phẩm</th><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Loại sản phẩm</th><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Giá</th><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Số lượng</th><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Hành động</th></tr></thead><tbody className="divide-y">{filteredProducts.length === 0 ? <tr><td colSpan={6} className="px-6 py-8 text-center text-sm text-gray-500">{search ? "Không tìm thấy sản phẩm phù hợp." : "Chưa có sản phẩm nào."}</td></tr> : filteredProducts.map((product) => <tr key={product.id} className="transition hover:bg-gray-50"><td className="px-6 py-4 text-center text-sm text-gray-500">{product.id}</td><td className="px-6 py-4 text-center font-medium text-gray-900">{product.name}</td><td className="px-6 py-4 text-center text-sm text-gray-500">{product.category.name}</td><td className="px-6 py-4 text-center text-sm font-medium text-gray-900">{(product.price ?? 0).toLocaleString("vi-VN")}đ</td><td className="px-6 py-4 text-center text-sm text-gray-700">{product.quantity}</td><td className="px-6 py-4"><div className="flex justify-center gap-2"><Button variant="outline" size="icon" title="Cập nhật" onClick={() => openEdit(product)}><Pencil className="h-4 w-4" /></Button><Button variant="outline" size="icon" title="Xóa" className="text-red-500 hover:text-red-600" onClick={() => void removeProduct(product)}><Trash2 className="h-4 w-4" /></Button></div></td></tr>)}</tbody></table></div>
            <Dialog open={openCreateForm} onOpenChange={setOpenCreateForm}><DialogContent className="lg:max-w-[1200px] sm:max-w-[650px] overflow-auto h-[60vh]"><DialogHeader><DialogTitle className="text-center font-bold mb-2">THÊM SẢN PHẨM</DialogTitle></DialogHeader><CreateProductForm onCreated={() => void fetchProducts()} /></DialogContent></Dialog><Dialog open={editingProduct !== null} onOpenChange={(open) => { if (!open) setEditingProduct(null); }}><DialogContent className="sm:max-w-[500px]"><DialogHeader><DialogTitle>Cập nhật sản phẩm</DialogTitle></DialogHeader><form onSubmit={saveEdit} className="space-y-4"><div><label className="mb-2 block text-sm font-medium">Tên sản phẩm</label><Input required value={editName} onChange={(event) => setEditName(event.target.value)} /></div><div><label className="mb-2 block text-sm font-medium">Giá sản phẩm</label><Input required type="number" min="0" value={editPrice} onChange={(event) => setEditPrice(event.target.value)} /></div><div><label className="mb-2 block text-sm font-medium">Số lượng</label><Input required type="number" min="0" value={editQuantity} onChange={(event) => setEditQuantity(event.target.value)} /></div><Button type="submit" className="w-full">Lưu thay đổi</Button></form></DialogContent></Dialog>
        </div>
    );
}
