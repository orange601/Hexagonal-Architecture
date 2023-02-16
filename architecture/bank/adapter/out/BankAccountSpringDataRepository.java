package com.hexagonal.architecture.bank.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountSpringDataRepository extends JpaRepository<BankAccountEntity, Long> {

}
