package com.building.roomreservation.repository;

import com.building.roomreservation.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> { }
