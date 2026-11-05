"use client";

import { useEffect, useState } from "react";
import { Eye, Pencil, Plus, Tags, Trash2 } from "lucide-react";

import CreateCategoryForm from "@/components/category/createCategoryForm";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import useCategory, { type CategoryOption } from "@/hooks/useCategory";
import useSpecification from "@/hooks/useSpecification";
import { deleteCategory } from "@/services/categoryService";

export default function CategoryPage() {
    const { categoriesParent, fetchCategoriesParent, categoriesParentId, fetchCategoriesParentId } = useCategory();
    const { categorySpecifications, fetchCategorySpecifications } = useSpecification();
    const [openCreateForm, setOpenCreateForm] = useState(false);
    const [openEditForm, setOpenEditForm] = useState(false);
    const [openDetails, setOpenDetails] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState<CategoryOption | null>(null);

    useEffect(() => {
        void fetchCategoriesParent();
    }, []);

    const refreshCategories = async () => {
        await fetchCategoriesParent();
    };

    const handleViewDetails = async (category: CategoryOption) => {
        setSelectedCategory(category);
        setOpenDetails(true);
        await Promise.all([
            fetchCategoriesParentId(category.id),
            fetchCategorySpecifications(category.id),
        ]);
    };

    const handleDelete = async (category: CategoryOption) => {
        if (!window.confirm(`Bạn có chắc muốn xóa loại sản phẩm "${category.name}" không?`)) {
            return;
        }

        try {
            await deleteCategory(category.id);
            await refreshCategories();
        } catch (error) {
            console.error("Không thể xóa loại sản phẩm:", error);
            window.alert("Xóa loại sản phẩm thất bại. Vui lòng thử lại.");
        }
    };

    const handleEdit = (category: CategoryOption) => {
        setSelectedCategory(category);
        setOpenEditForm(true);
    };

    return (
        <div className="min-h-full bg-slate-50 p-4 sm:p-8">
            <div className="mb-8 flex items-center justify-between gap-4">
                <div>
                    <div className="flex items-center gap-3">
                        <div className="rounded-lg bg-blue-100 p-2"><Tags className="h-6 w-6 text-blue-600" /></div>
                        <h1 className="text-2xl font-bold text-gray-900">Loại sản phẩm</h1>
                    </div>
                    <p className="mt-2 text-sm text-gray-500">Quản lý các loại sản phẩm cha trong cửa hàng.</p>
                </div>
                <Button onClick={() => setOpenCreateForm(true)}><Plus className="mr-2 h-4 w-4" />Thêm loại sản phẩm</Button>
            </div>

            <div className="overflow-hidden rounded-xl border bg-white shadow-sm">
                <table className="w-full">
                    <thead className="border-b bg-gray-50">
                        <tr>
                            <th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">
                                Tên loại
                            </th>
                            <th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">
                                Thao tác
                            </th>
                        </tr>
                    </thead>
                    <tbody className="divide-y">
                        {categoriesParent.length === 0 ? (
                            <tr><td colSpan={2} className="px-6 py-10 text-center text-sm text-gray-500">Chưa có loại sản phẩm cha nào.</td></tr>
                        ) : categoriesParent.map((category) => (
                            <tr key={category.id} className="transition hover:bg-gray-50">
                                <td className="px-6 py-4 text-center"><div className="font-medium text-gray-900">{category.name}</div></td>
                                <td className="px-6 py-4"><div className="flex justify-center gap-2">
                                    <Button variant="outline" size="icon" title="Xem chi tiết" onClick={() => void handleViewDetails(category)}><Eye className="h-4 w-4" /></Button>
                                    <Button variant="outline" size="icon" title="Sửa" onClick={() => handleEdit(category)}><Pencil className="h-4 w-4" /></Button>
                                    <Button variant="outline" size="icon" title="Xóa" className="text-red-500 hover:text-red-600" onClick={() => void handleDelete(category)}><Trash2 className="h-4 w-4" /></Button>
                                </div></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <Dialog open={openCreateForm} onOpenChange={setOpenCreateForm}>
                <DialogContent className="sm:max-w-[700px]"><DialogHeader><DialogTitle>Thêm loại sản phẩm</DialogTitle></DialogHeader><CreateCategoryForm onSaved={() => { setOpenCreateForm(false); void refreshCategories(); }} /></DialogContent>
            </Dialog>

            <Dialog open={openEditForm} onOpenChange={setOpenEditForm}>
                <DialogContent className="sm:max-w-[700px]"><DialogHeader><DialogTitle>Sửa loại sản phẩm</DialogTitle></DialogHeader><CreateCategoryForm category={selectedCategory} onSaved={() => { setOpenEditForm(false); void refreshCategories(); }} /></DialogContent>
            </Dialog>

            <Dialog open={openDetails} onOpenChange={setOpenDetails}>
                <DialogContent className="max-h-[85vh] overflow-y-auto sm:max-w-[900px]">
                    <DialogHeader><DialogTitle>Chi tiết loại sản phẩm: {selectedCategory?.name}</DialogTitle></DialogHeader>
                    <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
                        <section className="rounded-lg border bg-white p-5">
                            <h3 className="mb-4 border-b pb-3 text-base font-semibold text-gray-900">Danh sách loại con</h3>
                            {categoriesParentId.length > 0 ? <ul className="space-y-3">{categoriesParentId.map((category) => <li key={category.id} className="rounded-md bg-slate-50 px-4 py-3 text-sm text-gray-700">{category.name}</li>)}</ul> : <p className="text-sm text-gray-500">Chưa có loại con.</p>}
                        </section>
                        <section className="rounded-lg border bg-white p-5">
                            <h3 className="mb-4 border-b pb-3 text-base font-semibold text-gray-900">Danh sách thông số</h3>
                            {categorySpecifications.length > 0 ? <ul className="space-y-3">{categorySpecifications.map((specification) => <li key={specification.id} className="flex items-center justify-between rounded-md bg-slate-50 px-4 py-3 text-sm"><span className="text-gray-700">{specification.name}</span>{specification.unit && <span className="text-gray-500">{specification.unit}</span>}</li>)}</ul> : <p className="text-sm text-gray-500">Chưa có thông số.</p>}
                        </section>
                    </div>
                </DialogContent>
            </Dialog>
        </div>
    );
}
