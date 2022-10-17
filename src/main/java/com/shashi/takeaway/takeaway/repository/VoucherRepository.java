package com.shashi.takeaway.takeaway.repository;

import com.shashi.takeaway.takeaway.domain.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherRepository  extends JpaRepository<Voucher,Long> {
 Optional<Voucher> findByUserIdAndId(String userId,long voucherId);
}
