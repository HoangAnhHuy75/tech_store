import apiClient from "@/lib/axios";

export interface SpecificationRequest {
    name : string;
    unit: string;
}
export const createSpecification = async (data: SpecificationRequest) => {
    const response = await apiClient.post("/specifications", data);
    return response.data;
}

export const updateSpecification = async (id: number, data: SpecificationRequest) => {
    const response = await apiClient.put(`/specifications/${id}`, data);
    return response.data;
}

export const deleteSpecification = async (id: number) => {
    const response = await apiClient.delete(`/specifications/${id}`);
    return response.data;
}

export const findAll = async () => {
    const response = await apiClient.get("/specifications");
    return response.data.result;
}

export const findByCategories_Id = async (id: number) => {
    const response = await apiClient.get(`/specifications/categories/${id}`);
    return response.data.result;
}
