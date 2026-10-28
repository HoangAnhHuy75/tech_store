import apiClient from "@/lib/axios";

export interface Product {
    id: number;
    name: string;
    price: number | null;
    quantity: number;
    isActive: boolean;
    category: {
        id: number;
        name: string;
        parent: {
            id: number;
            name: string;
        } | null;
    };
}

interface CreateProductRequest {
    name: string;
    price: number;
    quantity: number;
    categoryId: number;
    productSpecificationRequests: {
        specificationId: number;
        value: string;
    }[];
}

export const findAll = async (): Promise<Product[]> => {
    const response = await apiClient.get<{ result: Product[] }>("/products");
    return response.data.result;
};

export const createProduct = async (data: CreateProductRequest) => {
    const response = await apiClient.post("/products",data);
    return response.data;
}