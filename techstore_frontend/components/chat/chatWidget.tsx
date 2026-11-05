"use client";

import { FormEvent, useEffect, useRef, useState } from "react";
import { MessageCircle, Send, X } from "lucide-react";
import { usePathname } from "next/navigation";
import { useAuthStore } from "@/stores/authStore";
import {
  ChatMessage,
  getAdministrator,
  getConversation,
  getCurrentUser,
  sendMessage,
} from "@/services/messageService";

export default function ChatWidget() {
  const pathname = usePathname();
  const token = useAuthStore((state) => state.token);
  const [open, setOpen] = useState(false);
  const [userId, setUserId] = useState<number | null>(null);
  const [adminId, setAdminId] = useState<number | null>(null);
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [content, setContent] = useState("");
  const [loading, setLoading] = useState(false);
  const endRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!token || pathname.startsWith("/admin")) return;
    void Promise.all([getCurrentUser(), getAdministrator()]).then(([user, admin]) => {
      setUserId(user.id);
      setAdminId(admin.id);
    }).catch(() => undefined);
  }, [pathname, token]);

  useEffect(() => {
    if (!open || userId === null || adminId === null) return;
    const load = () => getConversation(userId, adminId).then(setMessages).catch(() => undefined);
    void load();
    const timer = window.setInterval(load, 5000);
    return () => window.clearInterval(timer);
  }, [adminId, open, userId]);

  useEffect(() => {
    endRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  if (!token || pathname.startsWith("/admin") || userId === null || adminId === null) return null;

  const submit = async (event: FormEvent) => {
    event.preventDefault();
    const text = content.trim();
    if (!text || loading) return;
    setLoading(true);
    try {
      const sent = await sendMessage(userId, adminId, text);
      setMessages((current) => [...current, sent]);
      setContent("");
    } finally {
      setLoading(false);
    }
  };

  return <div className="fixed right-6 top-20 z-[60] flex flex-col items-end">
    {open && <div className="mb-3 flex h-[min(520px,calc(100vh-7rem))] w-[min(360px,calc(100vw-2rem))] flex-col overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-2xl">
      <div className="flex items-center justify-between bg-blue-600 px-4 py-3 text-white">
        <div><p className="font-semibold">Hỗ trợ TechStore</p><p className="text-xs text-blue-100">Chúng tôi sẽ phản hồi sớm</p></div>
        <button type="button" aria-label="Đóng chat" onClick={() => setOpen(false)}><X className="h-5 w-5" /></button>
      </div>
      <div className="flex-1 space-y-3 overflow-y-auto bg-slate-50 p-4">
        {messages.length === 0 && <p className="pt-8 text-center text-sm text-slate-500">Hãy gửi tin nhắn cho admin.</p>}
        {messages.map((message, index) => <div key={`${message.createAt}-${index}`} className={`flex ${message.senderId === userId ? "justify-end" : "justify-start"}`}><div className={`max-w-[80%] rounded-2xl px-3 py-2 text-sm ${message.senderId === userId ? "rounded-br-sm bg-blue-600 text-white" : "rounded-bl-sm bg-white text-slate-700 shadow-sm"}`}>{message.content}</div></div>)}
        <div ref={endRef} />
      </div>
      <form onSubmit={submit} className="flex gap-2 border-t bg-white p-3"><input value={content} onChange={(event) => setContent(event.target.value)} placeholder="Nhập tin nhắn..." className="min-w-0 flex-1 rounded-xl border border-slate-200 px-3 py-2 text-sm outline-none focus:border-blue-500" /><button type="submit" aria-label="Gửi tin nhắn" className="rounded-xl bg-blue-600 p-2 text-white hover:bg-blue-700 disabled:opacity-50" disabled={loading}><Send className="h-4 w-4" /></button></form>
    </div>}
    <button type="button" aria-label="Mở chat với admin" onClick={() => setOpen((value) => !value)} className="flex h-14 w-14 items-center justify-center rounded-full bg-blue-600 text-white shadow-lg transition hover:scale-105 hover:bg-blue-700"><MessageCircle className="h-6 w-6" /></button>
  </div>;
}
