package com.rojas.dev.tvc.Repository;

import com.rojas.dev.tvc.entity.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutRepository extends JpaRepository<Payout,Long> {
}
