package com.tim23.webproject.repository;

import com.tim23.webproject.entity.Polica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicaRepository extends JpaRepository<Polica, Long> {
}
