"use client";

import { useState } from "react";
import {
    Plus,
    Search,
    Pencil,
    Trash2,
    Tags,
} from "lucide-react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

import {Dialog,DialogContent,DialogHeader,DialogTitle} from "@/components/ui/dialog";

import CreateCategoryForm from "@/components/category/createCategoryForm";

export default function CategoryPage() {

    const [openCreateForm, setOpenCreateForm] = useState(false);

    return (
        <div className="p-8 border-2">

            {/* Header */}
            <div className="mb-8 flex items-center justify-between">
                <div>
                    <div className="flex items-center gap-3">
                        <div className="rounded-lg bg-blue-100 p-2">
                            <Tags className="h-6 w-6 text-blue-600" />
                        </div>

                        <h1 className="text-2xl font-bold text-gray-900">
                            Loại sản phẩm
                        </h1>

                    </div>

                    <p className="mt-2 text-sm text-gray-500">
                        Quản lý các loại sản phẩm trong cửa hàng.
                    </p>
                </div>

                {/* Button thêm */}
                <Button onClick={() => setOpenCreateForm(true)}>
                    <Plus className="mr-2 h-4 w-4" />
                    Thêm loại sản phẩm
                </Button>

            </div>


            {/* Search */}
            <div className="mb-6 flex gap-3">
                <div className="relative max-w-sm flex-1">
                    <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-gray-400" />
                    <Input placeholder="Tìm kiếm loại sản phẩm..." className="pl-9"/>
                </div>
            </div>


            {/* Table */}
            <div className="overflow-hidden rounded-xl border bg-white shadow-sm">
                <table className="w-full">
                    <thead className="border-b bg-gray-50">
                        <tr>
                            <th className="px-6 py-4 text-left text-sm font-semibold text-gray-700">
                                Tên loại
                            </th>
                            <th className="px-6 py-4 text-left text-sm font-semibold text-gray-700">
                                Loại cha
                            </th>
                            <th className="px-6 py-4 text-left text-sm font-semibold text-gray-700">
                                Thông số
                            </th>
                            <th className="px-6 py-4 text-right text-sm font-semibold text-gray-700">
                                Thao tác
                            </th>
                        </tr>

                    </thead>

                    <tbody className="divide-y">
                        <tr className="transition hover:bg-gray-50">
                            <td className="px-6 py-4">
                                <div className="font-medium text-gray-900">
                                    Laptop
                                </div>

                                <div className="text-xs text-gray-500">
                                    ID: 1
                                </div>
                            </td>

                            <td className="px-6 py-4 text-sm text-gray-500">
                                —
                            </td>

                            <td className="px-6 py-4">
                                <span className="rounded-full bg-blue-50 px-3 py-1 text-xs font-medium text-blue-600">
                                    5 thông số
                                </span>
                            </td>

                            <td className="px-6 py-4">
                                <div className="flex justify-end gap-2">
                                    <Button variant="outline" size="icon">
                                        <Pencil className="h-4 w-4" />
                                    </Button>

                                    <Button variant="outline" size="icon" className="text-red-500 hover:text-red-600">
                                        <Trash2 className="h-4 w-4" />
                                    </Button>
                                </div>
                            </td>
                        </tr>
                    </tbody>

                </table>

            </div>


            {/* Dialog thêm Category */}
            <Dialog open={openCreateForm} onOpenChange={setOpenCreateForm}>
                <DialogContent className="sm:max-w-[700px]">
                    <DialogHeader>
                        <DialogTitle>Thêm loại sản phẩm</DialogTitle>
                    </DialogHeader>

                    <CreateCategoryForm />

                </DialogContent>

            </Dialog>

        </div>
    );
}