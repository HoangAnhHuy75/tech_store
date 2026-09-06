"use client";

import { useEffect } from "react";
import { Label } from "../ui/label";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { Checkbox } from "../ui/checkbox";
import useCategory from "@/hooks/useCategory";
import { categorySchema } from "@/lib/validations/category";
import { useForm, useWatch } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import useSpecification from "@/hooks/useSpecification";
import { createCategory } from "@/services/categoryService";

type CreateCategoryFormValues = z.infer<typeof categorySchema>;


export default function CreateCategoryForm() {
    const { categoriesParent, fetchCategoriesParent } = useCategory();
    const { specifications, fetchSpecifications } = useSpecification();

    const { register, handleSubmit, setValue, control, reset, formState: { errors, isSubmitting } } = useForm<CreateCategoryFormValues>({
        resolver: zodResolver(categorySchema),
        defaultValues: {
            name: "",
            parentId: "",
            specificationIds: [],
        }
    });

    const selectedSpecifications = useWatch({
        control,
        name: "specificationIds",
    });

    const handleSpecificationChange = (specificationId: number, checked: boolean) => {
        const currentIds = selectedSpecifications ?? [];
        if (checked) {
            setValue("specificationIds", [...currentIds, specificationId]);
        } else {
            setValue("specificationIds", currentIds.filter(id => id !== specificationId));
        }
    };

    useEffect(() => {
        fetchCategoriesParent();
        fetchSpecifications();
    }, []);

    const onSubmit = async (data: CreateCategoryFormValues) => {
        try {
            const response = await createCategory({
                name: data.name,
                parentId: data.parentId ? Number(data.parentId) : null,
                specificationIds: data.specificationIds,
            });
            if (response?.code === 201) {
                await fetchCategoriesParent();
                reset()
            }
        } catch (error: unknown) {
            console.error("Lỗi khi tạo category:", error);
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 my-5" >
            <div className="grid grid-cols-2 gap-6">

                {/* Thông tin category */}
                <div className="rounded-lg border bg-white p-5 space-y-5">
                    <div className="space-y-2">
                        <Label>Tên danh mục</Label>
                        <Input {...register("name")} type="text" placeholder="Nhập tên danh mục" />

                        {errors.name && (
                            <p className="text-sm text-red-500">
                                {errors.name.message}
                            </p>
                        )}
                    </div>

                    <div className="space-y-2">
                        <Label>Danh mục cha</Label>
                        <select {...register("parentId")} className="flex h-10 w-full rounded-md border border-input bg-transparent px-3 py-2 text-sm shadow-sm">
                            <option value="">
                                Không có danh mục cha
                            </option>

                            {categoriesParent.map((category) => (
                                <option key={category.id} value={category.id}>
                                    {category.name}
                                </option>
                            ))}
                        </select>

                        {errors.parentId && (
                            <p className="text-sm text-red-500">
                                {errors.parentId.message}
                            </p>
                        )}
                    </div>

                </div>


                {/* Specifications */}
                <div className="rounded-lg border bg-white p-5">
                    <Label className="text-base font-semibold"> Thông số kỹ thuật</Label>
                    <div className="mt-4 grid grid-cols-2 gap-x-4 gap-y-4">
                        {specifications.map((specification) => (
                            <div key={specification.id} className="flex items-center gap-2">
                                <Checkbox
                                    checked={selectedSpecifications.includes(specification.id)}
                                    onCheckedChange={(checked) => handleSpecificationChange(specification.id, checked === true)}
                                />

                                <Label className="cursor-pointer">{specification.name}</Label>
                            </div>
                        ))}
                    </div>
                </div>

            </div>

            <Button
                disabled={isSubmitting}
                type="submit"
                className="w-full h-10"
            >
                {isSubmitting ? "Đang tạo ..." : "Tạo danh mục"}
            </Button>
        </form>
    );
}