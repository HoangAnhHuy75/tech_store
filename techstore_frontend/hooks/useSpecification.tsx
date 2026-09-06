import { useState } from "react";
import {
    findAll,
    findByCategories_Id,
} from "@/services/specificationService";

interface SpecificationOption {
    id: number;
    name: string;
    unit: string;
}

export default function useSpecification() {
    const [specifications, setSpecifications] = useState<SpecificationOption[]>([]);
    const [categorySpecifications, setCategorySpecifications] =
        useState<SpecificationOption[]>([]);

    const fetchSpecifications = async () => {
        try {
            const response = await findAll();
            setSpecifications(response);
        } catch (error) {
            console.log("Lỗi", error);
        }
    };

    const fetchCategorySpecifications = async (categoryId: number) => {
        try {
            const response = await findByCategories_Id(categoryId);
            setCategorySpecifications(response);
        } catch (error) {
            console.log("Lỗi", error);
        }
    };

    return {
        specifications,
        fetchSpecifications,
        categorySpecifications,
        setCategorySpecifications,
        fetchCategorySpecifications
    };
}