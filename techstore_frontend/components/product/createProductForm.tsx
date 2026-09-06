"use client";
import { useEffect, useState } from "react";
import { Label } from "../ui/label";
import Image from "next/image";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { Checkbox } from "../ui/checkbox";
import { createProduct } from "@/services/productService";
import useCategory from "@/hooks/useCategory";
import useSpecification from "@/hooks/useSpecification";
import { productSchema } from "@/lib/validations/product";
import z from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { createImages } from "@/services/imageService";

type CreateProductFormValues = z.infer<typeof productSchema>;
export default function CreateProductForm() {
    const { categoriesParent, fetchCategoriesParent, categoriesParentId, setCategoriesParentId, fetchCategoriesParentId } = useCategory();
    const { categorySpecifications, setCategorySpecifications, fetchCategorySpecifications } = useSpecification();
    const [images, setImages] = useState<File[]>([]);
    const [previews, setPreviews] = useState<string[]>([]);
    const { register, handleSubmit, setValue, formState: { errors, isSubmitting } } = useForm<CreateProductFormValues>({
        resolver: zodResolver(productSchema),
        defaultValues: {
            name: "",
            price: "",
            quantity: "",
            categoryId: "",
            specifications: [],
        }
    });

    useEffect(() => {
        fetchCategoriesParent();
    }, [])

    useEffect(() => {
        setValue("specifications", categorySpecifications.map((specification) => ({
            specificationId: specification.id,
            value: "",
        }))
        );
    }, [categorySpecifications, setValue]);

    const uploadImages = async (files: File[], productId: number) => {
        const formData = new FormData();
        files.forEach((file) => {
            formData.append("files", file);
        });
        formData.append("productId", productId.toString());
        return await createImages(formData);
    };

    const createProductData = async (data: CreateProductFormValues) => {
        const productData = {
            name: data.name,
            price: Number(data.price),
            quantity: Number(data.quantity),
            categoryId: Number(data.categoryId),
            specifications: data.specifications.map((specification) => ({
                specificationId: specification.specificationId,
                value: specification.value,
            })),
        };
        return await createProduct(productData);
    };

    const onSubmit = async (data: CreateProductFormValues) => {
        if (images.length === 0) {
            alert("Vui lòng chọn hình ảnh sản phẩm");
            return;
        }
        // Bước 1: Tạo sản phẩm
        const productResponse = await createProductData(data);
        if (productResponse?.code !== 201) {
            alert("Tạo sản phẩm thất bại");
            return;
        }
        const productId = productResponse.result.id;
        
        // Bước 2: Upload ảnh cho sản phẩm vừa tạo
        const imageResponse = await uploadImages(images, productId);

        if (imageResponse?.code === 201) {
            alert("Tạo sản phẩm thành công");
        } else {
            alert("Tạo sản phẩm thành công nhưng upload ảnh thất bại");
        }
    };


    const onChangeSelectParent = (id: number) => {
        if (id) {
            fetchCategoriesParentId(id);
            fetchCategorySpecifications(id)
        } else {
            setCategoriesParentId([]);
            setCategorySpecifications([]);
        }
    };

    const onChangeImages = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newFiles = Array.from(e.target.files || []);
        setImages((prev) => {
            return [...prev, ...newFiles]
        });
        const newPreviews = newFiles.map((file) => URL.createObjectURL(file));
        setPreviews((prev) => [...prev, ...newPreviews]);
    };


    return (
        <form className="scroll-auto" onSubmit={handleSubmit(onSubmit)}>
            <div className="grid grid-cols-1 gap-6 p-4 lg:grid-cols-3">

                <div className="px-2">
                    <h1 className="text-gray-900 flex justify-center pb-4 pt-2 font-bold">THÔNG TIN SẢN PHẨM</h1>
                    <div className="grid grid-cols-2 gap-4">
                        <div className="space-y-2">
                            <Label>Tên sản phẩm</Label>
                            <Input {...register("name")} />

                            {
                                errors.name && (
                                    <p className="text-red-600 text-sm">{errors.name.message}</p>
                                )
                            }
                        </div>

                        <div className="space-y-2">
                            <Label>Giá sản phẩm</Label>
                            <Input {...register("price")} />
                            {
                                errors.price && (
                                    <p className="text-red-500 text-sm">{errors.price.message}</p>
                                )
                            }
                        </div>

                        <div className="space-y-2">
                            <Label>Danh mục cha</Label>
                            <select onChange={(e) => onChangeSelectParent(Number(e.target.value))} className="flex w-full rounded-md border border-input bg-transparent px-3 py-2 text-sm shadow-sm">
                                <option value="">Chọn danh mục cha</option>
                                {categoriesParent.map((category) => (
                                    <option key={category.id} value={category.id}>{category.name}</option>
                                ))}
                            </select>
                        </div>

                        <div className="space-y-2">
                            <Label>Danh mục con</Label>
                            <select {...register("categoryId")} className="flex w-full rounded-md border border-input bg-transparent px-3 py-2 text-sm shadow-sm">
                                <option value="">Chọn danh mục con</option>
                                {categoriesParentId.map((category) => (
                                    <option key={category.id} value={category.id}>
                                        {category.name}
                                    </option>
                                ))}
                            </select>
                            {errors.categoryId && (
                                <p className="text-sm text-red-500">
                                    {errors.categoryId.message}
                                </p>
                            )}
                        </div>

                        <div className="space-y-2">
                            <Label>Số lượng</Label>

                            <Input type="number" {...register("quantity")} />

                            {errors.quantity && (
                                <p className="text-sm text-red-500">
                                    {errors.quantity.message}
                                </p>
                            )}
                        </div>

                    </div>
                </div>

                <div className="px-2 border-t-2 lg:border-t-0 lg:border-l-2">
                    <h1 className="text-gray-900 flex justify-center pb-4 pt-2 font-bold">
                        THÔNG SỐ KỸ THUẬT
                    </h1>

                    <div className="space-y-4">
                        {categorySpecifications.length === 0 && (
                            <p className="text-center">Hãy chọn danh mục cha để thêm thông số kỹ thuật cho sản phẩm</p>
                        )}
                        {categorySpecifications.map((specification, index) => (
                            <div key={specification.id} className="grid grid-cols-3 items-center gap-4">
                                <Label>
                                    {specification.name}
                                </Label>

                                <div className="col-span-2 flex items-center gap-2">
                                    <Input {...register(`specifications.${index}.value`)} />

                                    {specification.unit && (
                                        <span className="text-sm text-gray-500">
                                            {specification.unit}
                                        </span>
                                    )}
                                </div>
                                {errors.specifications?.[index]?.value && (
                                    <p className="col-span-3 text-sm text-red-500">
                                        {errors.specifications[index].value.message}
                                    </p>
                                )}
                            </div>
                        ))}
                    </div>
                </div>

                <div className="px-2 border-t-2 lg:border-t-0 lg:border-l-2">
                    <h1 className="text-gray-900 flex justify-center pb-4 pt-2 font-bold">
                        HÌNH ẢNH SẢN PHẨM
                    </h1>

                    <div className="flex justify-center">
                        <Label htmlFor="product-images" className="cursor-pointer">
                            <Button type="button" asChild>
                                <span>Chọn hình ảnh</span>
                            </Button>
                        </Label>

                        <Input
                            id="product-images"
                            type="file"
                            accept="image/*"
                            multiple
                            className="hidden"
                            onChange={(e) => onChangeImages(e)}
                        />
                    </div>

                    {/* Preview */}
                    {previews.length > 0 && (
                        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3 mt-4">
                            {previews.map((url, idx) => (
                                <div key={idx} className="relative overflow-hidden rounded-xl border border-gray-200 shadow-sm">
                                    <Image width={300} height={50} src={url} alt={`preview-${idx}`} className="w-full h-32 object-cover hover:scale-105 transition-transform duration-300" />
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>
            <div className="flex justify-center mt-4">
                <Button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? "Đang tạo..." : "Tạo sản phẩm"}
                </Button>
            </div>
        </form>
    );
}