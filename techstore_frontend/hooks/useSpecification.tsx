import { useState } from "react";
import { findAll, findByCategories_Id } from "@/services/specificationService";

export interface SpecificationOption {
    id: number;
    name: string;
    unit: string;
}

export default function useSpecification() {
    const [specifications, setSpecifications] = useState<SpecificationOption[]>([]);
    const [categorySpecifications, setCategorySpecifications] = useState<SpecificationOption[]>([]);

    const fetchSpecifications = async () => {
        try {
            const response = await findAll();
            setSpecifications(response);
            return response as SpecificationOption[];
        } catch (error) {
            console.log("Không thể lấy thông số:", error);
            return [];
        }
    };

    const fetchCategorySpecifications = async (categoryId: number): Promise<SpecificationOption[]> => {
        try {
            const response = await findByCategories_Id(categoryId);
            setCategorySpecifications(response);
            return response as SpecificationOption[];
        } catch (error) {
            console.log("Không thể lấy thông số theo loại:", error);
            return [];
        }
    };

    return { specifications, fetchSpecifications, categorySpecifications, setCategorySpecifications, fetchCategorySpecifications };
}
