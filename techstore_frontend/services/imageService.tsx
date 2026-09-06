import apiClient from "@/lib/axios"

export const createImages = async (data: FormData) => {
    const response = await apiClient.post("/images", data, {
        headers: { "Content-Type": "multipart/form-data" }
    });
    return response.data;
}