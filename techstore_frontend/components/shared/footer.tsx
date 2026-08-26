import { Label } from "../ui/label"
import { Button } from "../ui/button"
export default function Footer() {
    return (
        <footer className="bg-blue-950">
            <div className="flex justify-around mx-auto p-4 border-2">
                <div>
                    <Label className="text-2xl font-bold text-gray-400">
                        Tech <span className="text-2xl font bold text-blue-400">Store</span>
                    </Label>
                </div>
                <div>
                    <Label className="text-md sm:text-xl font-bold text-white">Loại sản phẩm nổi bật</Label>
                    <div className="flex flex-col py-2 text-gray-400">
                        <span>Laptops</span>
                        <span>Mouses</span>
                        <span>Headphones</span>
                        <span>Minotors</span>
                    </div>
                </div>
                <div>
                    <Label className="text-md sm:text-xl font-bold text-white">Hỗ trợ khách hàng</Label>
                    <div className="flex flex-col py-2 text-gray-400">
                        <span>FAQ</span>
                        <span>Shipping info</span>
                        <span>Contact</span>
                        <span>Return</span>
                    </div>
                </div>
                <div>
                    <Label className="text-md sm:text-xl font-bold text-white">Cửa hàng</Label>
                    <div className="flex flex-col py-2 text-gray-400 font-light">
                        <span>Về chúng tôi</span>
                        <span>Chính sách hoàn trả</span>
                    </div>
                </div>
            </div>
        </footer>
    );
}