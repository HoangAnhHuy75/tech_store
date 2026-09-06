export default function AdminPage() {
    return (
        <>
            {/* Header */}
            <header className="flex h-16 items-center justify-between border-b bg-white px-8">
                <h2 className="text-lg font-semibold text-gray-900">
                    Trang chủ
                </h2>

                <div className="flex items-center gap-3">
                    <div className="flex h-9 w-9 items-center justify-center rounded-full bg-blue-100 text-sm font-semibold text-blue-600">
                        A
                    </div>

                    <div>
                        <p className="text-sm font-medium text-gray-900">
                            Admin
                        </p>

                        <p className="text-xs text-gray-500">
                            Quản trị viên
                        </p>
                    </div>
                </div>
            </header>

            {/* Dashboard */}
            <div className="p-8">

                <div className="mb-8">
                    <h1 className="text-2xl font-bold text-gray-900">
                        Xin chào, Admin 👋
                    </h1>

                    <p className="mt-1 text-sm text-gray-500">
                        Chào mừng bạn đến với trang quản trị TechStore.
                    </p>
                </div>

                <div className="grid grid-cols-1 gap-5 md:grid-cols-2 xl:grid-cols-4">

                    <div className="rounded-xl border bg-white p-5 shadow-sm">
                        <p className="text-sm text-gray-500">
                            Sản phẩm
                        </p>

                        <p className="mt-2 text-3xl font-bold">
                            128
                        </p>
                    </div>

                    <div className="rounded-xl border bg-white p-5 shadow-sm">
                        <p className="text-sm text-gray-500">
                            Loại sản phẩm
                        </p>

                        <p className="mt-2 text-3xl font-bold">
                            12
                        </p>
                    </div>

                    <div className="rounded-xl border bg-white p-5 shadow-sm">
                        <p className="text-sm text-gray-500">
                            Người dùng
                        </p>

                        <p className="mt-2 text-3xl font-bold">
                            342
                        </p>
                    </div>

                    <div className="rounded-xl border bg-white p-5 shadow-sm">
                        <p className="text-sm text-gray-500">
                            Đơn hàng
                        </p>

                        <p className="mt-2 text-3xl font-bold">
                            56
                        </p>
                    </div>

                </div>

            </div>
        </>
    );
}