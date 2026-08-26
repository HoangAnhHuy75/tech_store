import Link from 'next/link';
import {
  ArrowRight,
  Laptop,
  Smartphone,
  Headphones,
  Watch,
  ShieldCheck,
  Truck,
  RotateCcw,
  Headset,
  Star,
  ShoppingCart,
  Sparkles
} from 'lucide-react';
import Header from '@/components/shared/header';
import Footer from '@/components/shared/footer';
import { Button } from '@/components/ui/button';

// Mock Data: Danh mục sản phẩm
const categories = [
  { name: 'Laptop', icon: Laptop, count: '120+ sản phẩm', href: '/category/laptop' },
  { name: 'Điện thoại', icon: Smartphone, count: '85+ sản phẩm', href: '/category/phone' },
  { name: 'Tai nghe', icon: Headphones, count: '60+ sản phẩm', href: '/category/audio' },
  { name: 'Smartwatch', icon: Watch, count: '40+ sản phẩm', href: '/category/watch' },
];

// Mock Data: Sản phẩm nổi bật
const featuredProducts = [
  {
    id: '1',
    name: 'MacBook Pro 16 M3 Max',
    category: 'Laptop',
    price: '79.990.000₫',
    originalPrice: '84.990.000₫',
    rating: 5,
    icon: Laptop,
    badge: 'Giảm 5%'
  },
  {
    id: '2',
    name: 'iPhone 15 Pro Max 256GB',
    category: 'Điện thoại',
    price: '33.990.000₫',
    originalPrice: '34.990.000₫',
    rating: 4.9,
    icon: Smartphone,
    badge: 'Bán chạy'
  },
  {
    id: '3',
    name: 'Sony WH-1000XM5 Noise-Canceling',
    category: 'Tai nghe',
    price: '8.490.000₫',
    originalPrice: '9.990.000₫',
    rating: 4.8,
    icon: Headphones,
    badge: 'Hot Deal'
  },
  {
    id: '4',
    name: 'Apple Watch Series 9 GPS',
    category: 'Smartwatch',
    price: '10.290.000₫',
    originalPrice: '11.290.000₫',
    rating: 4.7,
    icon: Watch,
    badge: 'Mới'
  },
];

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen gap-12">
      <Header />

      {/* 1. Hero Banner (Thiết kế CSS Gradient thay cho Image) */}
      <section className="relative overflow-hidden bg-gradient-to-br from-slate-900 via-slate-800 to-blue-950 text-white rounded-3xl mx-4 mt-4 lg:mx-8">
        <div className="container relative z-10 grid grid-cols-1 lg:grid-cols-2 gap-8 items-center px-6 py-16 lg:py-24">
          <div className="space-y-6">
            <span className="inline-flex items-center gap-1.5 px-3 py-1 bg-blue-600/30 border border-blue-500/40 text-blue-400 text-xs font-semibold rounded-full uppercase tracking-wider">
              <Sparkles className="h-3.5 w-3.5" /> Siêu Khuyến Mãi 2026
            </span>
            <h1 className="text-4xl md:text-6xl font-extrabold tracking-tight leading-tight">
              Công Nghệ Mới. <br />
              <span className="text-blue-500">Trải Nghiệm Đỉnh Cao.</span>
            </h1>
            <p className="text-slate-300 text-base md:text-lg max-w-xl">
              Khám phá hệ sinh thái thiết bị công nghệ chính hãng hàng đầu với ưu đãi giảm giá đến 30% và chính sách bảo hành 1 đổi 1.
            </p>
            <div className="flex flex-wrap gap-4 pt-2">
              <Button size="lg" className="bg-blue-600 hover:bg-blue-700 text-white">
                Mua ngay <ArrowRight className="ml-2 h-4 w-4" />
              </Button>
              <Button size="lg" variant="outline" className="border-slate-700 text-slate-200 hover:bg-slate-800">
                Xem khuyến mãi
              </Button>
            </div>
          </div>

          {/* Placeholder Visual dùng CSS Pattern & Card */}
          <div className="relative h-64 md:h-96 w-full rounded-2xl bg-slate-800/50 border border-slate-700/50 flex flex-col items-center justify-center p-8 text-center backdrop-blur-sm">
            <div className="p-6 rounded-full bg-blue-600/10 text-blue-400 mb-4 animate-pulse">
              <Laptop className="h-16 w-16" />
            </div>
            <h3 className="text-xl font-bold text-white">TechStore Premium Collection</h3>
            <p className="text-slate-400 text-sm mt-2 max-w-xs">Cam kết hàng chính hãng 100% - Bảo hành tận nơi</p>
          </div>
        </div>
      </section>

      {/* 2. Trust Badges / Services */}
      <section className="container mx-auto px-4 lg:px-8">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-6 p-6 rounded-2xl bg-slate-50 border border-slate-100">
          <div className="flex items-center space-x-4">
            <div className="p-3 bg-blue-100 text-blue-600 rounded-xl">
              <Truck className="h-6 w-6" />
            </div>
            <div>
              <h4 className="font-semibold text-sm">Giao hàng miễn phí</h4>
              <p className="text-xs text-slate-500">Đơn hàng từ 500k</p>
            </div>
          </div>
          <div className="flex items-center space-x-4">
            <div className="p-3 bg-green-100 text-green-600 rounded-xl">
              <ShieldCheck className="h-6 w-6" />
            </div>
            <div>
              <h4 className="font-semibold text-sm">Hàng chính hãng 100%</h4>
              <p className="text-xs text-slate-500">Bảo hành 12-24 tháng</p>
            </div>
          </div>
          <div className="flex items-center space-x-4">
            <div className="p-3 bg-amber-100 text-amber-600 rounded-xl">
              <RotateCcw className="h-6 w-6" />
            </div>
            <div>
              <h4 className="font-semibold text-sm">Đổi trả trong 30 ngày</h4>
              <p className="text-xs text-slate-500">Lỗi từ nhà sản xuất</p>
            </div>
          </div>
          <div className="flex items-center space-x-4">
            <div className="p-3 bg-purple-100 text-purple-600 rounded-xl">
              <Headset className="h-6 w-6" />
            </div>
            <div>
              <h4 className="font-semibold text-sm">Hỗ trợ 24/7</h4>
              <p className="text-xs text-slate-500">Tư vấn kỹ thuật tận tâm</p>
            </div>
          </div>
        </div>
      </section>

      {/* 3. Categories Grid */}
      <section className="container mx-auto px-4 lg:px-8 space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold tracking-tight">Danh mục sản phẩm</h2>
            <p className="text-slate-500 text-sm">Tìm kiếm thiết bị theo nhu cầu của bạn</p>
          </div>
        </div>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          {categories.map((cat, index) => {
            const Icon = cat.icon;
            return (
              <Link
                key={index}
                href={cat.href}
                className="group flex flex-col items-center justify-center p-6 bg-white border border-slate-200 rounded-2xl hover:border-blue-500 hover:shadow-lg transition-all"
              >
                <div className="p-4 bg-slate-100 rounded-full group-hover:bg-blue-50 group-hover:text-blue-600 transition-colors">
                  <Icon className="h-8 w-8 text-slate-700 group-hover:text-blue-600" />
                </div>
                <h3 className="mt-4 font-semibold text-slate-900">{cat.name}</h3>
                <p className="text-xs text-slate-500 mt-1">{cat.count}</p>
              </Link>
            );
          })}
        </div>
      </section>

      {/* 4. Featured Products Grid */}
      <section className="container mx-auto px-4 lg:px-8 space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold tracking-tight">Sản phẩm nổi bật</h2>
            <p className="text-slate-500 text-sm">Những thiết bị công nghệ hot nhất hiện nay</p>
          </div>
          <Button variant="ghost" className="text-blue-600 hover:text-blue-700">
            Xem tất cả <ArrowRight className="ml-2 h-4 w-4" />
          </Button>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {featuredProducts.map((product) => {
            const ProductIcon = product.icon;
            return (
              <div
                key={product.id}
                className="group relative bg-white border border-slate-200 rounded-2xl overflow-hidden hover:shadow-xl transition-all flex flex-col"
              >
                {/* Product Badge */}
                <span className="absolute top-3 left-3 z-10 px-2.5 py-1 bg-red-500 text-white text-xs font-bold rounded-md">
                  {product.badge}
                </span>

                {/* Product Icon Container (thay cho Image) */}
                <div className="h-48 w-full bg-slate-50 flex items-center justify-center group-hover:bg-blue-50/50 transition-colors">
                  <ProductIcon className="h-20 w-20 text-slate-300 group-hover:text-blue-500 group-hover:scale-110 transition-all duration-300" />
                </div>

                {/* Product Details */}
                <div className="p-4 flex flex-col flex-1 justify-between space-y-3">
                  <div className="space-y-1">
                    <span className="text-xs text-slate-400 font-medium">{product.category}</span>
                    <h3 className="font-semibold text-slate-900 line-clamp-1 group-hover:text-blue-600 transition-colors">
                      {product.name}
                    </h3>

                    {/* Rating */}
                    <div className="flex items-center space-x-1">
                      <Star className="h-3.5 w-3.5 fill-amber-400 text-amber-400" />
                      <span className="text-xs font-medium text-slate-700">{product.rating}</span>
                    </div>
                  </div>

                  {/* Pricing & CTA */}
                  <div className="flex items-center justify-between pt-2 border-t border-slate-100">
                    <div>
                      <div className="text-lg font-bold text-blue-600">{product.price}</div>
                      <div className="text-xs text-slate-400 line-through">{product.originalPrice}</div>
                    </div>
                    <Button size="icon" className="bg-slate-900 hover:bg-blue-600 transition-colors rounded-xl">
                      <ShoppingCart className="h-4 w-4" />
                    </Button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </section>
      <Footer />

    </div>
  );
}