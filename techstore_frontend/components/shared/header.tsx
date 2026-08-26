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

                        <li>
                            <Link href="/products" className="hover:text-blue-600 transition-colors">
                                Sản phẩm
                            </Link>
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
                    <Button variant="ghost" size="icon"  asChild  aria-label="Account">
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
                        {isMenuOpen ? ( <X className="h-6 w-6 text-gray-700" />) : (<Menu className="h-6 w-6 text-gray-700" />)}
                    </Button>
                </div>
            </div>

            {/* Mobile Navigation */}
            {isMenuOpen && (
                <nav className="lg:hidden border-t border-gray-100 bg-white">
                    <ul className="flex flex-col px-4 py-3 font-medium text-gray-600">
                        <li>
                            <Link  href="/" onClick={() => setIsMenuOpen(false)} className="block py-3 hover:text-blue-600 transition-colors">
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