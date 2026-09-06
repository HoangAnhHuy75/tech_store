import apiClient from "@/lib/axios";

interface CreateSpecificationRequest {
    name : string;
    unit: string;
}
export const createSpecification = async (data: CreateSpecificationRequest) => {
    const response = await apiClient.post("/specifications", data);
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