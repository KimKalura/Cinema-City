package com.spring.cinemaapp.repository;

import com.spring.cinemaapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
