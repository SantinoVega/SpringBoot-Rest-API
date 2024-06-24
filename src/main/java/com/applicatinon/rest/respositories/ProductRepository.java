package com.applicatinon.rest.respositories;

import com.applicatinon.rest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from productos where precio between :minPrice and :maxPrice orderBy asc",nativeQuery = true)
    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
