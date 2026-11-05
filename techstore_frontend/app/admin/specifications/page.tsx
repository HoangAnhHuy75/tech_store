"use client";

import { useEffect, useState } from "react";
import { Pencil, Plus, Settings, Trash2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import useSpecification, { type SpecificationOption } from "@/hooks/useSpecification";
import { createSpecification, deleteSpecification, updateSpecification } from "@/services/specificationService";

export default function SpecificationPage() {
    const { specifications, fetchSpecifications } = useSpecification();
    const [openForm, setOpenForm] = useState(false);
    const [editing, setEditing] = useState<SpecificationOption | null>(null);
    const [name, setName] = useState("");
    const [unit, setUnit] = useState("");
    const [saving, setSaving] = useState(false);

    useEffect(() => { void fetchSpecifications(); }, []);

    const openCreate = () => { setEditing(null); setName(""); setUnit(""); setOpenForm(true); };
    const openEdit = (specification: SpecificationOption) => { setEditing(specification); setName(specification.name); setUnit(specification.unit); setOpenForm(true); };

    const saveSpecification = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setSaving(true);
        try {
            const data = { name: name.trim(), unit: unit.trim() };
            if (editing) await updateSpecification(editing.id, data);
            else await createSpecification(data);
            setOpenForm(false);
            await fetchSpecifications();
        } finally { setSaving(false); }
    };

    const removeSpecification = async (specification: SpecificationOption) => {
        if (!window.confirm(`Bạn có chắc muốn xóa thông số "${specification.name}" không?`)) return;
        await deleteSpecification(specification.id);
        await fetchSpecifications();
    };

    return (
        <div className="min-h-full bg-slate-50 p-4 sm:p-8">
            <div className="mb-8 flex items-center justify-between gap-4"><div><div className="flex items-center gap-3"><div className="rounded-lg bg-blue-100 p-2"><Settings className="h-6 w-6 text-blue-600" /></div><h1 className="text-2xl font-bold text-gray-900">Thông số kỹ thuật</h1></div><p className="mt-2 text-sm text-gray-500">Quản lý tên và đơn vị của các thông số sản phẩm.</p></div><Button onClick={openCreate}><Plus className="mr-2 h-4 w-4" />Thêm thông số</Button></div>
            <div className="overflow-hidden rounded-xl border bg-white shadow-sm"><table className="w-full"><thead className="border-b bg-gray-50"><tr><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Tên</th><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Đơn vị</th><th className="px-6 py-4 text-center text-sm font-semibold text-gray-700">Chỉnh sửa và xóa</th></tr></thead><tbody className="divide-y">{specifications.length === 0 ? <tr><td colSpan={3} className="px-6 py-10 text-center text-sm text-gray-500">Chưa có thông số kỹ thuật.</td></tr> : specifications.map((specification) => <tr key={specification.id} className="hover:bg-gray-50"><td className="px-6 py-4 text-center font-medium text-gray-900">{specification.name}</td><td className="px-6 py-4 text-center text-sm text-gray-600">{specification.unit || "—"}</td><td className="px-6 py-4"><div className="flex justify-center gap-2"><Button variant="outline" size="icon" title="Sửa" onClick={() => openEdit(specification)}><Pencil className="h-4 w-4" /></Button><Button variant="outline" size="icon" title="Xóa" className="text-red-500 hover:text-red-600" onClick={() => void removeSpecification(specification)}><Trash2 className="h-4 w-4" /></Button></div></td></tr>)}</tbody></table></div>
            <Dialog open={openForm} onOpenChange={setOpenForm}><DialogContent className="sm:max-w-[500px]"><DialogHeader><DialogTitle>{editing ? "Sửa thông số kỹ thuật" : "Thêm thông số kỹ thuật"}</DialogTitle></DialogHeader><form onSubmit={saveSpecification} className="space-y-4"><div><label className="mb-2 block text-sm font-medium">Tên thông số</label><Input required value={name} onChange={(event) => setName(event.target.value)} placeholder="Ví dụ: Kích thước màn hình" /></div><div><label className="mb-2 block text-sm font-medium">Đơn vị</label><Input value={unit} onChange={(event) => setUnit(event.target.value)} placeholder="Ví dụ: inch, kg, GB" /></div><Button type="submit" disabled={saving} className="w-full">{saving ? "Đang lưu..." : "Lưu thông số"}</Button></form></DialogContent></Dialog>
        </div>
    );
}
