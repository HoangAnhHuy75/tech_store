"use client";

import { FormEvent, useEffect, useRef, useState } from "react";
import { MessageCircle, Search, Send } from "lucide-react";
import {
  ChatMessage,
  ChatUser,
  getConversation,
  getCurrentUser,
  getMessageUsers,
  sendMessage,
} from "@/services/messageService";

export default function AdminMessagesPage() {
  const [adminId, setAdminId] = useState<number | null>(null);
  const [users, setUsers] = useState<ChatUser[]>([]);
  const [selectedUser, setSelectedUser] = useState<ChatUser | null>(null);
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [search, setSearch] = useState("");
  const [content, setContent] = useState("");
  const endRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    void getCurrentUser().then((admin) => {
      setAdminId(admin.id);
      return getMessageUsers(admin.id);
    }).then(setUsers).catch(() => undefined);
  }, []);

  useEffect(() => {
    if (adminId === null || selectedUser === null) return;
    const load = () => getConversation(adminId, selectedUser.id).then(setMessages).catch(() => undefined);
    void load();
    const timer = window.setInterval(load, 5000);
    return () => window.clearInterval(timer);
  }, [adminId, selectedUser]);

  useEffect(() => {
    endRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const searchUsers = async (value: string) => {
    setSearch(value);
    if (adminId !== null) setUsers(await getMessageUsers(adminId, value).catch(() => []));
  };

  const submit = async (event: FormEvent) => {
    event.preventDefault();
    const text = content.trim();
    if (!text || adminId === null || selectedUser === null) return;
    const sent = await sendMessage(adminId, selectedUser.id, text);
    setMessages((current) => [...current, sent]);
    setContent("");
  };

  return <>
    <header className="flex h-16 items-center border-b bg-white px-8"><h1 className="text-lg font-semibold text-gray-900">Tin nhắn</h1></header>
    <main className="p-8"><div className="mb-6"><h2 className="text-2xl font-bold text-slate-900">Hộp thư hỗ trợ</h2><p className="mt-1 text-sm text-slate-500">Quản lý các cuộc trò chuyện với khách hàng.</p></div>
      <div className="grid h-[calc(100vh-12rem)] min-h-[520px] grid-cols-[280px_1fr] overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
        <aside className="border-r border-slate-200"><div className="border-b p-4"><div className="relative"><Search className="absolute left-3 top-2.5 h-4 w-4 text-slate-400" /><input value={search} onChange={(event) => void searchUsers(event.target.value)} placeholder="Tìm khách hàng" className="w-full rounded-xl bg-slate-100 py-2 pl-9 pr-3 text-sm outline-none focus:ring-2 focus:ring-blue-200" /></div></div><div className="overflow-y-auto">{users.length === 0 ? <p className="p-5 text-center text-sm text-slate-500">Chưa có cuộc trò chuyện.</p> : users.map((user) => <button key={user.id} type="button" onClick={() => setSelectedUser(user)} className={`flex w-full items-center gap-3 border-b border-slate-100 px-4 py-3 text-left hover:bg-blue-50 ${selectedUser?.id === user.id ? "bg-blue-50" : ""}`}><div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-100 font-semibold text-blue-600">{(user.name || user.username || "U").charAt(0).toUpperCase()}</div><div className="min-w-0"><p className="truncate text-sm font-semibold text-slate-800">{user.name || user.username || `Người dùng #${user.id}`}</p><p className="text-xs text-slate-500">ID: {user.id}</p></div></button>)}</div></aside>
        <section className="flex min-w-0 flex-col">{selectedUser ? <><div className="flex items-center gap-3 border-b px-5 py-4"><div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-100 font-semibold text-blue-600">{(selectedUser.name || "U").charAt(0).toUpperCase()}</div><div><p className="font-semibold text-slate-900">{selectedUser.name || `Người dùng #${selectedUser.id}`}</p><p className="text-xs text-slate-500">Đang hỗ trợ khách hàng</p></div></div><div className="flex-1 space-y-3 overflow-y-auto bg-slate-50 p-6">{messages.map((message, index) => <div key={`${message.createAt}-${index}`} className={`flex ${message.senderId === adminId ? "justify-end" : "justify-start"}`}><div className={`max-w-[70%] rounded-2xl px-4 py-2.5 text-sm ${message.senderId === adminId ? "rounded-br-sm bg-blue-600 text-white" : "rounded-bl-sm bg-white text-slate-700 shadow-sm"}`}>{message.content}</div></div>)}<div ref={endRef} /></div><form onSubmit={submit} className="flex gap-3 border-t bg-white p-4"><input value={content} onChange={(event) => setContent(event.target.value)} placeholder="Nhập nội dung trả lời..." className="flex-1 rounded-xl border border-slate-200 px-4 py-2.5 text-sm outline-none focus:border-blue-500" /><button type="submit" className="flex items-center gap-2 rounded-xl bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white hover:bg-blue-700"><Send className="h-4 w-4" /> Gửi</button></form></> : <div className="flex flex-1 flex-col items-center justify-center text-center text-slate-400"><MessageCircle className="h-12 w-12" /><p className="mt-3 font-medium">Chọn một khách hàng để bắt đầu</p><p className="mt-1 text-sm">Các tin nhắn mới sẽ xuất hiện trong danh sách bên trái.</p></div>}</section>
      </div>
    </main>
  </>;
}
