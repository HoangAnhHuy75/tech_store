"use client";

import { useEffect, useMemo } from "react";
import Link from "next/link";
import { ArrowRight, PackageSearch, ShoppingBag, Sparkles } from "lucide-react";

import Header from "@/components/shared/header";
import useProduct from "@/hooks/useProduct";
import type { Product } from "@/services/productService";

const CATEGORY_LIMIT = 4;
const PRODUCTS_PER_CATEGORY = 4;

function getCategoryName(product: Product) {
  return product.category.parent?.name || product.category.name || "Sản phẩm khác";
}

function formatPrice(price: number | null) {
  return price === null ? "Liên hệ" : `${price.toLocaleString("vi-VN")}₫`;
}

function ProductCard({ product }: { product: Product }) {
  return (
    <Link href={`/products/${product.id}`} className="group overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm transition hover:-translate-y-1 hover:border-blue-200 hover:shadow-xl">
      <div className="flex h-48 items-center justify-center bg-gradient-to-br from-blue-50 via-slate-50 to-indigo-100">
        <ShoppingBag className="h-16 w-16 text-blue-200 transition group-hover:scale-110 group-hover:text-blue-400" />
      </div>
      <div className="p-4">
        <p className="mb-2 line-clamp-2 min-h-12 font-semibold text-slate-900 group-hover:text-blue-600">{product.name}</p>
        <div className="flex items-center justify-between gap-2">
          <span className="font-bold text-blue-600">{formatPrice(product.price)}</span>
          <span className="text-xs text-slate-500">Còn {product.quantity}</span>
        </div>
      </div>
    </Link>
  );
}

export default function Home() {
  const { products, fetchProducts } = useProduct();

  useEffect(() => {
    void fetchProducts();
  }, []);

  const productGroups = useMemo(() => {
    const groups = new Map<string, Product[]>();

    products.filter((product) => product.isActive && product.quantity > 0).forEach((product) => {
      const categoryName = getCategoryName(product);
      const categoryProducts = groups.get(categoryName) ?? [];
      if (categoryProducts.length < PRODUCTS_PER_CATEGORY) {
        groups.set(categoryName, [...categoryProducts, product]);
      }
    });

    return Array.from(groups.entries()).slice(0, CATEGORY_LIMIT).map(([name, categoryProducts]) => ({ name, products: categoryProducts }));
  }, [products]);

  return (
    <div className="min-h-screen bg-slate-50">
      <Header />
      <main>
        <section className="bg-gradient-to-br from-slate-950 via-blue-950 to-blue-700 px-4 py-20 text-white sm:px-6 lg:px-8">
          <div className="mx-auto max-w-7xl"><div className="max-w-2xl">
            <div className="mb-5 inline-flex items-center gap-2 rounded-full bg-white/10 px-4 py-2 text-sm text-blue-100"><Sparkles className="h-4 w-4" /> Công nghệ chính hãng, giá tốt mỗi ngày</div>
            <h1 className="text-4xl font-black tracking-tight sm:text-6xl">Nâng cấp công nghệ của bạn</h1>
            <p className="mt-5 text-lg leading-8 text-blue-100">Khám phá những sản phẩm nổi bật được chọn theo từng danh mục.</p>
            <Link href="/products" className="mt-8 inline-flex items-center gap-2 rounded-xl bg-white px-5 py-3 font-semibold text-blue-700 transition hover:bg-blue-50">Xem tất cả sản phẩm <ArrowRight className="h-4 w-4" /></Link>
          </div></div>
        </section>

        <section className="mx-auto max-w-7xl px-4 py-14 sm:px-6 lg:px-8">
          <div className="mb-10 flex items-end justify-between gap-4"><div><p className="mb-2 text-sm font-bold uppercase tracking-[0.2em] text-blue-600">Khám phá ngay</p><h2 className="text-3xl font-black text-slate-900">Sản phẩm theo danh mục</h2></div><Link href="/products" className="hidden items-center gap-1 font-semibold text-blue-600 hover:text-blue-700 sm:flex">Xem thêm <ArrowRight className="h-4 w-4" /></Link></div>
          {productGroups.length > 0 ? <div className="space-y-14">{productGroups.map((group) => <section key={group.name} aria-labelledby={`category-${group.name}`}><div className="mb-5 flex items-center justify-between border-b border-slate-200 pb-3"><h3 id={`category-${group.name}`} className="text-2xl font-bold text-slate-900">{group.name}</h3><span className="text-sm text-slate-500">{group.products.length} sản phẩm</span></div><div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">{group.products.map((product) => <ProductCard key={product.id} product={product} />)}</div></section>)}</div> : <div className="rounded-2xl border border-dashed border-slate-300 bg-white px-6 py-16 text-center"><PackageSearch className="mx-auto h-12 w-12 text-slate-300" /><h3 className="mt-4 text-lg font-semibold text-slate-900">Chưa có sản phẩm đang bán</h3><p className="mt-2 text-slate-500">Các sản phẩm sẽ được hiển thị tại đây sau khi được thêm vào cửa hàng.</p></div>}
        </section>
      </main>
    </div>
  );
}
