package com.shashi.takeaway.takeaway.factory;

import com.shashi.takeaway.takeaway.domain.Voucher;
import com.shashi.takeaway.takeaway.model.Create;
import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

  public Voucher createNewVoucher(Create create){
    var voucher= new Voucher();
    voucher.setUserId(create.getUserId());
    voucher.setExpirationDate(create.getExpirationDate());
    voucher.setRemainingBalances(create.getValue());
    return voucher;
  }

}
