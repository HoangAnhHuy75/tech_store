"use client";
import {
    Plus,
    Tags,
} from "lucide-react";
import CreateProductForm from "@/components/product/createProductForm";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { Dialog, DialogContent,DialogHeader,DialogTitle} from "@/components/ui/dialog"

export default function ProductPage() {
    const [openCreateForm, setOpenCreateForm] = useState(false);
    return (
        <div className="p-8 border-2">
            <div className="flex items-center border justify-between">
                <div>
                    <div className="flex items-center gap-3">
                        <div className="rounded-lg bg-blue-100 p-2">
                            <Tags className="h-6 w-6 text-blue-600" />
                        </div>

                        <h1 className="text-2xl font-bold text-gray-900">
                            Sản phẩm
                        </h1>

                    </div>

                    <p className="mt-2 text-sm text-gray-500">
                        Quản lý các sản phẩm trong cửa hàng.
                    </p>
                </div>

                {/* Button thêm */}
                <Button onClick={() => setOpenCreateForm(true)}>
                    <Plus className="mr-2 h-4 w-4" />
                    Thêm loại sản phẩm
                </Button>

            </div>
            <Dialog open={openCreateForm} onOpenChange={setOpenCreateForm}>
                <DialogContent className="lg:max-w-[1200px] sm:max-w-[650px] overflow-auto h-[60vh]">
                    <DialogHeader>
                        <DialogTitle className="text-center font-bold mb-2">THÊM SẢN PHẨM</DialogTitle>
                    </DialogHeader>
                    <CreateProductForm/>
                </DialogContent>
            </Dialog>
        </div>
    );
}