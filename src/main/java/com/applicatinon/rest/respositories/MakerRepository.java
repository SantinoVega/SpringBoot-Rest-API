package com.applicatinon.rest.respositories;

import com.applicatinon.rest.entities.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker,Long> {
}
