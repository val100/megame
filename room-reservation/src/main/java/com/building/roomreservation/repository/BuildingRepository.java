package com.building.roomreservation.repository;

import com.building.roomreservation.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> { }
