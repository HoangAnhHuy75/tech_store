import apiClient from "@/lib/axios";

interface CreateProductRequest {
    name: string;
    price: number;
    quantity: number;
    categoryId: number;
    specifications: {
        specificationId: number;
        value: string;
    }[];
}

export const createProduct = async (data: CreateProductRequest) => {
    const response = await apiClient.post("/products",data);
    return response.data;
}