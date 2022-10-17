package com.shashi.takeaway.takeaway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Create {
  private String userId;
  private Long value;
  private LocalDateTime expirationDate;
}
