package com.shashi.takeaway.takeaway.services;

import com.shashi.takeaway.takeaway.domain.Voucher;
import com.shashi.takeaway.takeaway.factory.VoucherFactory;
import com.shashi.takeaway.takeaway.model.Create;
import com.shashi.takeaway.takeaway.model.Redeem;
import com.shashi.takeaway.takeaway.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoucherService {

  private final VoucherRepository voucherRepository;
  private final VoucherFactory factory;

  public Voucher initVoucher(Create create) {
    var voucher = factory.createNewVoucher(create);
    return voucherRepository.save(voucher);
  }

  public long getAfterRedeemValue(Redeem redeem) {
    return voucherRepository.findById(redeem.getVoucherId())
            .filter(voucher -> voucher.getExpirationDate().isBefore(LocalDateTime.now()))
            .filter(voucher -> voucher.getRemainingBalances() > 0)
            .map(voucher -> validateOrUpdate(redeem, voucher))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Either voucher is expired or invalid."));
  }

  private long validateOrUpdate(Redeem redeem, Voucher voucher) {
    long orderValue = (redeem.getOrderValue() - voucher.getRemainingBalances());
    if (orderValue > 0) {
      voucher.setRemainingBalances(0L);
    } else {
      voucher.setRemainingBalances(voucher.getRemainingBalances() - redeem.getOrderValue());
    }
    voucherRepository.save(voucher);
    return orderValue;
  }

  public Optional<Voucher> retrieveByUserIdAndVoucherId(final String userId, long voucherId) {
    return voucherRepository.findByUserIdAndId(userId, voucherId);
  }

  public List<Voucher> getALLVoucher(){
    return voucherRepository.findAll();
  }

}
