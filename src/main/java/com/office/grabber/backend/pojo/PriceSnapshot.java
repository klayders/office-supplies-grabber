package com.office.grabber.backend.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceSnapshot {

  @JsonProperty("POSITION_PRICE")
  private long price;

}
