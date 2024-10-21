package com.ecommerce.Ecommerce.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T>{
    private String status;
    private T data;
}
