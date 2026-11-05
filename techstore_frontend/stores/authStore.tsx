import { create } from "zustand";
import { persist } from "zustand/middleware";

type AuthData = {
    token: string | null;
    userName: string | null;
    role: string | null;
};

type AuthStore = AuthData & {
    setAuth: (auth: AuthData) => void;
    setToken: (token: string | null) => void;
    clearAuth: () => void;
};

export const useAuthStore = create<AuthStore>()(
    persist(
        (set) => ({
            token: null,
            userName: null,
            role: null,

            setAuth: ({ token, userName, role }) => set({ token, userName, role }),
            setToken: (token) => set({ token }),
            clearAuth: () => set({ token: null, userName: null, role: null }),
        }),
        {
            name: "auth-storage",
            partialize: (state) => ({
                token: state.token,
                userName: state.userName,
                role: state.role,
            }),
        },
    ),
);
