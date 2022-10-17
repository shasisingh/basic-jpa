package com.shashi.takeaway.takeaway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "VOUCHER")
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {

  @GeneratedValue
  @Id
  private Long id;
  private LocalDateTime expirationDate;
  private String userId;
  private Long remainingBalances;



}
