import apiClient from "@/lib/axios";

export interface CreateCategoryRequest {
  name: string;
  parentId: number | null;
  specificationIds: number[];
}

export const createCategory = async (data: CreateCategoryRequest) => {
  const response = await apiClient.post("/categories", data);
  return response.data;
};

export const findAll = async () => {
  const response = await apiClient.get("/categories");
  return response.data.result;
};

export const findByParentIdIsNill = async () => {
  const response = await apiClient.get("/categories/parents");
  return response.data.result;
}

export const findByParentId = async (id: number) => {
  const response = await apiClient.get(`/categories/parents/${id}`);
  return response.data.result;
}