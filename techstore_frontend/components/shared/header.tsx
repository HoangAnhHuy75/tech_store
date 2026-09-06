"use client";

import { useState } from "react";
import { Label } from "../ui/label";
import { Button } from "../ui/button";
import { Search, User, ShoppingBag, Menu, X } from "lucide-react";
import Link from "next/link";

export default function Header() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);

    return (
        <header className="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-gray-100 shadow-sm">
            <div className="max-w-7xl mx-auto flex justify-between items-center py-4 px-4 sm:px-6">

                {/* Logo */}
                <Link href="/" className="shrink-0">
                    <Label className="font-extrabold text-2xl text-gray-900 cursor-pointer tracking-tight">
                        Tech<span className="text-blue-600">Store</span>
                    </Label>
                </Link>

                {/* Desktop Navigation */}
                <nav className="hidden lg:block">
                    <ul className="flex space-x-6 xl:space-x-8 font-medium text-md text-gray-600">
                        <li>
                            <Link href="/" className="hover:text-blue-600 transition-colors">
                                Trang chủ
                            </Link>
                        </li>

                        <li className="relative group">
                            <Link href="/products" className="hover:text-blue-600 transition-colors">
                                Sản phẩm
                            </Link>

                            {/* Dropdown */}
                            <div className=" absolute left-1/2 top-full z-50 w-[700px] -translate-x-1/2 
                            rounded-xl border border-gray-200 bg-white shadow-xl invisible opacity-0 
                            translate-y-2 group-hover:visible group-hover:opacity-100 group-hover:translate-y-0
                            transition-all duration-300">
                                <div className="grid grid-cols-3 gap-6 p-6">

                                    {/* Laptop */}
                                    <div>
                                        <h3 className="font-semibold text-gray-900">
                                            Laptop
                                        </h3>

                                        <div className="mt-3 space-y-2">
                                            <Link
                                                href="/category/laptop-gaming"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                Laptop Gaming
                                            </Link>

                                            <Link
                                                href="/category/laptop-office"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                Laptop Văn phòng
                                            </Link>

                                            <Link
                                                href="/category/macbook"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                MacBook
                                            </Link>
                                        </div>
                                    </div>

                                    {/* Điện thoại */}
                                    <div>
                                        <h3 className="font-semibold text-gray-900">
                                            Điện thoại
                                        </h3>

                                        <div className="mt-3 space-y-2">
                                            <Link
                                                href="/category/iphone"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                iPhone
                                            </Link>

                                            <Link
                                                href="/category/samsung"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                Samsung
                                            </Link>

                                            <Link
                                                href="/category/xiaomi"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                Xiaomi
                                            </Link>
                                        </div>
                                    </div>

                                    {/* Phụ kiện */}
                                    <div>
                                        <h3 className="font-semibold text-gray-900">
                                            Phụ kiện
                                        </h3>

                                        <div className="mt-3 space-y-2">
                                            <Link
                                                href="/category/headphone"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                Tai nghe
                                            </Link>

                                            <Link
                                                href="/category/smartwatch"
                                                className="block text-sm text-gray-500 hover:text-blue-600"
                                            >
                                                Smartwatch
                                            </Link>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </li>

                        <li>
                            <Link href="/categories" className="hover:text-blue-600 transition-colors">
                                Loại sản phẩm
                            </Link>
                        </li>

                        <li>
                            <Link href="/promotions" className="hover:text-blue-600 transition-colors">
                                Khuyến mãi
                            </Link>
                        </li>

                        <li>
                            <Link href="/contact" className="hover:text-blue-600 transition-colors" >
                                Liên hệ
                            </Link>
                        </li>
                    </ul>
                </nav>

                {/* Right Actions */}
                <div className="flex items-center space-x-1">
                    {/* Search */}
                    <Button variant="ghost" size="icon" aria-label="Search">
                        <Search className="h-5 w-5 text-gray-700" />
                    </Button>

                    {/* Account */}
                    <Button variant="ghost" size="icon" asChild aria-label="Account">
                        <Link href="/login">
                            <User className="h-5 w-5 text-gray-700" />
                        </Link>
                    </Button>

                    {/* Cart */}
                    <Button variant="ghost" size="icon" className="relative" aria-label="Cart">
                        <ShoppingBag className="h-5 w-5 text-gray-700" />
                        <span className="absolute -top-1 -right-1 flex h-4 w-4 items-center justify-center rounded-full bg-red-500 text-[10px] font-bold text-white shadow-sm">
                            0
                        </span>
                    </Button>

                    {/* Mobile Menu Button */}
                    <Button variant="ghost" size="icon" className="lg:hidden" aria-label="Menu" onClick={() => setIsMenuOpen(!isMenuOpen)}>
                        {isMenuOpen ? (<X className="h-6 w-6 text-gray-700" />) : (<Menu className="h-6 w-6 text-gray-700" />)}
                    </Button>
                </div>
            </div>

            {/* Mobile Navigation */}
            {isMenuOpen && (
                <nav className="lg:hidden border-t border-gray-100 bg-white">
                    <ul className="flex flex-col px-4 py-3 font-medium text-gray-600">
                        <li>
                            <Link href="/" onClick={() => setIsMenuOpen(false)} className="block py-3 hover:text-blue-600 transition-colors">
                                Trang chủ
                            </Link>
                        </li>

                        <li>
                            <Link href="/products" onClick={() => setIsMenuOpen(false)} className="block py-3 hover:text-blue-600 transition-colors">
                                Sản phẩm
                            </Link>
                        </li>

                        <li>
                            <Link href="/categories" onClick={() => setIsMenuOpen(false)} className="block py-3 hover:text-blue-600 transition-colors">
                                Loại sản phẩm
                            </Link>
                        </li>

                        <li>
                            <Link href="/promotions" onClick={() => setIsMenuOpen(false)} className="block py-3 hover:text-blue-600 transition-colors">
                                Khuyến mãi
                            </Link>
                        </li>

                        <li>
                            <Link href="/contact" onClick={() => setIsMenuOpen(false)} className="block py-3 hover:text-blue-600 transition-colors">
                                Liên hệ
                            </Link>
                        </li>
                    </ul>
                </nav>
            )}
        </header>
    );
}