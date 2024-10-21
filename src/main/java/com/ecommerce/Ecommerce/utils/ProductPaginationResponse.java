package com.ecommerce.Ecommerce.utils;

import com.ecommerce.Ecommerce.entity.Product;

import java.util.List;

public record ProductPaginationResponse(List<Product> productList, Integer pageeNumber, Integer pageSize,
                                        long totalElements, int totalPages, boolean isLast) {
}
