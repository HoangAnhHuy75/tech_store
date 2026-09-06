import z from "zod";

export const productSchema = z.object({
    name: z.string().min(1, "Tên sản phẩm không được để trống"),
    price: z.string().min(1, "Giá không được để trống"),
    quantity: z.string().min(1, "Số lượng không được để trống"),
    categoryId: z.string().min(1, "Vui lòng chọn loại sản phẩm"),
    specifications: z.array(z.object({
            specificationId: z.number(),
            value: z.string().trim().min(1, "Vui lòng nhập thông số"),
        })
    ),
});