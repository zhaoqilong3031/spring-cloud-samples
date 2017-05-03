package com.zhaoql.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zhaoql.product.domain.entity.ProuctTcc;
import com.zhaoql.support.jpa.BaseRepository;

public interface ProductTccRepositorie extends BaseRepository<ProuctTcc, Integer> {

	@Query(value = "SELECT * FROM product_tcc WHERE expire <= NOW() AND entity_type=? AND status = 0", nativeQuery = true)
	List<ProuctTcc> expireReservation(int entityType);
}
