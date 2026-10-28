import { useState } from "react";
import { findAll, type Product } from "@/services/productService";

export default function useProduct() {
    const [products, setProducts] = useState<Product[]>([]);

    const fetchProducts = async () => {
        try {
            const response = await findAll();
            setProducts(response);
        } catch (error) {
            console.error("Lỗi khi lấy danh sách sản phẩm:", error);
        }
    };

    return {
        products,
        fetchProducts,
    };
}