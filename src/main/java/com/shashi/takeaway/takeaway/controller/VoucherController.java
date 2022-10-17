package com.shashi.takeaway.takeaway.controller;

import com.shashi.takeaway.takeaway.domain.Voucher;
import com.shashi.takeaway.takeaway.model.Create;
import com.shashi.takeaway.takeaway.model.Redeem;
import com.shashi.takeaway.takeaway.services.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/takeaway/v1/voucher")
public class VoucherController {

  private final VoucherService voucherService;

  @PostMapping(value = "create")
  @Transactional(transactionManager = "jpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
  public ResponseEntity<Voucher> request(@RequestBody Create create) {
    var voucher = voucherService.initVoucher(create);
    return ResponseEntity.ok(voucher);
  }

  @PostMapping(value = "redeem")
  @Transactional(transactionManager = "jpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
  public ResponseEntity<Long> redeem(@RequestBody Redeem redeem) {
    var voucher = voucherService.getAfterRedeemValue(redeem);
    return ResponseEntity.ok(voucher);

  }

  @GetMapping("{userId}/{voucherId}")
  @Transactional(transactionManager = "jpaTransactionManager",readOnly = true)
  public Voucher getVoucherByUserId(
          @PathVariable(value = "userId") final String userId,
          @PathVariable(value = "voucherId") final long voucherId) {
    return voucherService.retrieveByUserIdAndVoucherId(userId, voucherId)
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User Id and voucherId not found."));
  }

  @GetMapping
  @Transactional(transactionManager = "jpaTransactionManager",readOnly = true)
  public List<Voucher> getVoucher() {
    return voucherService.getALLVoucher();
  }
}
