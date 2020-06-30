package com.office.grabber.backend.repository;

import com.office.grabber.backend.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAllByCurrentInflatedPriceAndAndConcurrentInflatedPrice(Pageable pageable, boolean current, boolean concurrent);

}
