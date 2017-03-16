package com.usebilbo.vertx.module.provider;

import static com.usebilbo.vertx.util.Utils.coalesce;

import java.util.Comparator;

import com.usebilbo.vertx.annotation.Order;

public final class ProviderHelper {
    private ProviderHelper() {}
    
    @Order
    private static interface OrderHolder {}

    public static final Comparator<Class<?>> ORDER_COMPARATOR = new Comparator<Class<?>>() {
        private final Order defaultOrder = OrderHolder.class.getAnnotation(Order.class);
        
        @Override
        public int compare(Class<?> c1, Class<?> c2) {
            return order(c1) - order(c2);
        }
        
        private int order(Class<?> cls) {
            return coalesce(cls.getAnnotation(Order.class), defaultOrder).value().ordinal();
        }
    };
}
