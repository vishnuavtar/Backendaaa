package com.example.demo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

//	List<Product> findByModifyDateBetween(Date startDate, Date endDate);

	List<Product> findByDateBetween(LocalDate startDate, LocalDate endDate);

	List<Product> findByDateBetween(Date startDate, Date endDate);

	List<Product> findByProductName(String name);

}
