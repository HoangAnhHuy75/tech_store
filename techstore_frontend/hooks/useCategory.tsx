import { useState } from "react";
import { findAll, findByParentId } from "@/services/categoryService";
import { findByParentIdIsNill } from "@/services/categoryService";
interface CategoryOption {
    id: number;
    name: string;
    parent: CategoryOption | null;
}

export default function useCategory() {
    const [categories, setCategories] = useState<CategoryOption[]>([]);
    const [categoriesParent, setCategoriesParent] = useState<CategoryOption[]>([]);
    const [categoriesParentId, setCategoriesParentId] = useState<CategoryOption[]>([]);
    const fetchCategories = async () => {
        try {
            const response = await findAll();
            setCategories(response);
        } catch (error) {
            console.error("Lỗi khi lấy danh sách category:", error);
        }
    };

    const fetchCategoriesParentId = async (id: number) => {
        try {
            const response = await findByParentId(id);
            setCategoriesParentId(response);
        } catch (error) {
            console.error("Lỗi khi lấy danh sách category:", error);
        }
    };

    
    
    const fetchCategoriesParent = async () => {
        try {
            const response = await findByParentIdIsNill();
            setCategoriesParent(response);
        } catch (error) {
            console.log("Lỗi không thể lấy", error)
        }
    }

    return {categories, fetchCategories, categoriesParent, fetchCategoriesParent, categoriesParentId,setCategoriesParentId, fetchCategoriesParentId};
}