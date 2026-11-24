package com.koyo.webflux;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    record Product(Long id, String name) {
    }

    private static final List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1L, "Manzana"),
                    new Product(2L, "Pera"),
                    new Product(3L, "Naranja")));

}
