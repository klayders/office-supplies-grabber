package com.office.grabber.backend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductPojo {

  @JsonProperty("ID")
  private String id;

  @JsonProperty("NAME")
  private String name;

  @JsonProperty("UP_CODE_1C")
  private String code;

  @JsonProperty("UP_BARCODE")
  private String barcode;

  @JsonProperty("PRICE")
  private double price;
}
