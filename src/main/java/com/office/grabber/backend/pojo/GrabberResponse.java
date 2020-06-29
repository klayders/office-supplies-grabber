package com.office.grabber.backend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GrabberResponse {

  @JsonProperty("PRODUCTS")
  private List<ProductPojo> products;

}
