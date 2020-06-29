package com.office.grabber.backend.model;

import com.office.grabber.backend.pojo.ProductPojo;
import com.office.grabber.frontend.configuration.annotation.ColumnDisplayGrid;
import com.office.grabber.frontend.configuration.annotation.ColumnEditForm;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product",
       indexes = {
           @Index(columnList="currentInflatedPrice"),
           @Index(columnList="concurrentInflatedPrice"),
       }
)
public class Product {

  @Id
  @ColumnEditForm(displayOrder = 0)
  @ColumnDisplayGrid(displayOrder = 0)
  private String id;

  @ColumnEditForm(displayOrder = 1)
  @ColumnDisplayGrid(displayOrder = 1)
  private String name;

  @ColumnEditForm(displayOrder = 2)
  @ColumnDisplayGrid(displayOrder = 2)
  private String code;

  @ColumnEditForm(displayOrder = 3)
  @ColumnDisplayGrid(displayOrder = 3)
  private String barcode;

  @ColumnEditForm(displayOrder = 4)
  @ColumnDisplayGrid(displayOrder = 4)
  private double currentPrice;


  @ColumnEditForm(displayOrder = 5)
  @ColumnDisplayGrid(displayOrder = 5)
  private double concurrentPrice;

  private boolean currentInflatedPrice;
  private boolean concurrentInflatedPrice;


  public static Product fromPojo(ProductPojo productPojo, boolean concurrent) {
    final var productBuilder = Product.builder()
        .id(productPojo.getId())
        .name(productPojo.getName())
        .code(productPojo.getCode())
        .barcode(productPojo.getBarcode());

    if (concurrent) {
      productBuilder.concurrentPrice(productPojo.getPrice());
    } else {
      productBuilder.currentPrice(productPojo.getPrice());
    }

    return productBuilder.build();

  }

  public static Product fromTwoProduct(Product first, Product second) {
    final var currentPrice = first.getCurrentPrice() > 0 ? first.getCurrentPrice() : second.getCurrentPrice();
    final var concurrentPrice = first.getConcurrentPrice() > 0 ? first.getConcurrentPrice() : second.getConcurrentPrice();


    return Product.builder()
        .id(first.getId())
        .name(first.getName())
        .code(first.getCode())
        .barcode(first.getBarcode())
        .currentPrice(currentPrice)
        .concurrentPrice(concurrentPrice)
        .currentInflatedPrice(currentPrice > concurrentPrice)
        .concurrentInflatedPrice(concurrentPrice > currentPrice)
        .build();
  }

}
