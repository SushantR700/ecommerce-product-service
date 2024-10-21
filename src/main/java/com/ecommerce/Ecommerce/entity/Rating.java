package com.ecommerce.Ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Column(nullable = false)
    private double rate;
    @Column(nullable = false)
    private double count;
}
