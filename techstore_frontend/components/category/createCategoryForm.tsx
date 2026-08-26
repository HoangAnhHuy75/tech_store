"use client";

import { useEffect, useState } from "react";
import { Label } from "../ui/label";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import useCategory from "@/hooks/useCategory";
import { categorySchema } from "@/lib/validations/category";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";

import { createCategory } from "@/services/categoryService";

type CreateCategoryFormValues = z.infer<typeof categorySchema>;


export default function CreateCategoryForm() {
    const { categoriesParent, fetchCategoriesParent } = useCategory();


    const { register, handleSubmit, formState: { errors, isSubmitting }, } = useForm<CreateCategoryFormValues>({
        resolver: zodResolver(categorySchema),
    });

    useEffect(() => {
        fetchCategoriesParent();
    }, []);

    const onSubmit = async (data: CreateCategoryFormValues) => {
        try {
            const response = await createCategory({
                name: data.name,
                parentId: data.parentId ? Number(data.parentId) : null,
            });
            console.log("Response tạo category:", response);
            if (response?.code === 201) {
                await fetchCategoriesParent();
            }
        } catch (error: unknown) {
            console.error("Lỗi khi tạo category:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 my-5" >
            <div className="space-y-1">
                <Label>Tên danh mục</Label>
                <Input {...register("name")} type="text" placeholder="Nhập tên danh mục" />

                {errors.name && (
                    <p className="text-sm text-red-500">
                        {errors.name.message}
                    </p>
                )}
            </div>

            <div className="space-y-1">
                <Label>Danh mục cha</Label>

                <select {...register("parentId")} className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm">
                    <option value=""> Không có danh mục cha</option>
                    {categoriesParent.map((category) => (
                        <option key={category.id} value={category.id}>
                            {category.name}
                        </option>
                    ))}
                </select>

                {errors.parentId && (
                    <p className="text-sm text-red-500">{errors.parentId.message}</p>
                )}
            </div>

            <div>
                <Button disabled={isSubmitting} type="submit" className="w-full"> {isSubmitting ? "Đang tạo ..." : "Tạo danh mục"}</Button>
            </div>
        </form>
    );
}