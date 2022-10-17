package com.shashi.takeaway.takeaway.model;

import lombok.Data;

@Data
public class Redeem {
  private String userId;
  private long orderValue;
  private long voucherId;

}
