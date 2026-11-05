import apiClient from "@/lib/axios";

export type ChatUser = {
  id: number;
  name: string | null;
  username?: string;
};

export type ChatMessage = {
  senderId: number;
  receiverId: number;
  content: string;
  createAt: string;
};

const resultOf = <T,>(response: { data?: { result?: T } }) => response.data?.result as T;

export async function getCurrentUser() {
  return resultOf<ChatUser>(await apiClient.get("/users/me"));
}

export async function getAdministrator() {
  return resultOf<ChatUser>(await apiClient.get("/users/admin"));
}

export async function getMessageUsers(userId: number, key?: string) {
  return resultOf<ChatUser[]>(await apiClient.get(`/messages/${userId}`, {
    params: key ? { key } : undefined,
  })) ?? [];
}

export async function getConversation(senderId: number, receiverId: number) {
  return resultOf<ChatMessage[]>(await apiClient.get("/messages/conversation", {
    params: { senderId, receiverId },
  })) ?? [];
}

export async function sendMessage(senderId: number, receiverId: number, content: string) {
  return resultOf<ChatMessage>(await apiClient.post("/messages", { senderId, receiverId, content }));
}
