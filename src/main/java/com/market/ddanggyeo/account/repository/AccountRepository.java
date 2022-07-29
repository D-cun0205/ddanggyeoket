package com.market.ddanggyeo.account.repository;

import com.market.ddanggyeo.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByEmail(String username);
}
