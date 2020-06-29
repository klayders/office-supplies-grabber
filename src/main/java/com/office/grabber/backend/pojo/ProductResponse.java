package com.office.grabber.backend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {

  @JsonProperty("NAME")
  private String name;
  @JsonProperty("UP_CODE_1C")
  private String code;
  @JsonProperty("UP_BARCODE")
  private String barcode;
  @JsonProperty("PRICE")
  private long price;
  @JsonProperty("UP_MULTIPLICITY")
  private PriceSnapshot priceSnapshot;

}
