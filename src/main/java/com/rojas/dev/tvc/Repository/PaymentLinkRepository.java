package com.rojas.dev.tvc.Repository;

import com.rojas.dev.tvc.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentLinkRepository extends JpaRepository<PaymentLink, Integer> {
    Optional<PaymentLink> findByPaymentLinkId(String paymentLinkId);
}
