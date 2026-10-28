package com.techstore.techstore_backend.modules.rule.service;

import com.techstore.techstore_backend.modules.order.entity.Order;
import com.techstore.techstore_backend.modules.order.entity.OrderItem;
import com.techstore.techstore_backend.modules.product.entity.Product;
import com.techstore.techstore_backend.modules.rule.entity.Rule;
import com.techstore.techstore_backend.modules.rule.enums.RuleType;
import com.techstore.techstore_backend.shared.exception.AppException;
import com.techstore.techstore_backend.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class BuyXGetYStrategy implements RuleStrategy {
    @Override
    public RuleType getRuleType() {
        return RuleType.BUY_X_GET_Y;
    }

    @Override
    public BigDecimal calculateDiscount(Order order, Rule rule) {
        Map<String, Object> payload = rule.getPayload();

        Map<String, Object> xProduct = (Map<String, Object>) payload.get("x_product");
        Map<String, Object> yProduct = (Map<String, Object>) payload.get("y_product");

        if (xProduct == null || yProduct == null) {
            throw new AppException(ErrorCode.RULE_MISS_PAYLOAD);
        }

        Integer xId = Integer.valueOf(xProduct.get("product_id").toString());
        Integer xQuantity = Integer.valueOf(xProduct.get("product_quantity").toString());
        Integer yId = Integer.valueOf(yProduct.get("product_id").toString());
        Integer yQuantity = Integer.valueOf(yProduct.get("product_quantity").toString());

        List<OrderItem> items = order.getItems();
        OrderItem productBuy = items.stream()
                .filter(item -> item.getProduct().getId().equals(xId))
                .findFirst()
                .orElse(null);

        if (productBuy == null || productBuy.getQuantity() < xQuantity) {
            return BigDecimal.ZERO;
        }
        int sets = productBuy.getQuantity() / xQuantity;
        int totalFree = sets * yQuantity;

        OrderItem productGift = items.stream().
                filter(item -> item.getProduct().getId().equals(yId))
                .findFirst()
                .orElse(null);
        if (productGift == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal discount = productGift.getPrice().multiply(new BigDecimal(totalFree));
        return discount;
    }
}
