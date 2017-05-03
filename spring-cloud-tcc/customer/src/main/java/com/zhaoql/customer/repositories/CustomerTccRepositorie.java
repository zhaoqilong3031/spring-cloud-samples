package com.zhaoql.customer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zhaoql.customer.domain.entity.CustomerTcc;
import com.zhaoql.support.jpa.BaseRepository;

public interface CustomerTccRepositorie extends BaseRepository<CustomerTcc, Integer> {

	@Query(value = "SELECT * FROM customer_tcc WHERE expire <= NOW() AND entity_type=? and status = 0", nativeQuery = true)
	List<CustomerTcc> expireReservation(int entityType);
}
