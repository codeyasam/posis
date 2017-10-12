package com.codeyasam.posis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.codeyasam.posis.domain.PointOfSale;

public interface PointOfSaleRepository extends PagingAndSortingRepository<PointOfSale, Long> {

}
