package com.ecommerce.Ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({ "id", "title", "price", "description", "category", "image", "rating" })
public class Product {

    @Id
    private int Id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false,length = 2000)

    private String description;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String image;
    @Embedded
    private Rating rating;

}
