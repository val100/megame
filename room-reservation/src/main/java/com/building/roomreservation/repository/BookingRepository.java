package com.building.roomreservation.repository;

import com.building.roomreservation.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.room.id = :roomId and b.status in (:statuses) and not (b.requestedEnd <= :start or b.requestedStart >= :end)")
    List<Booking> findOverlapping(@Param("roomId") Long roomId,
                                  @Param("start") OffsetDateTime start,
                                  @Param("end") OffsetDateTime end,
                                  @Param("statuses") List<BookingStatus> statuses);

    @Query("select max(b.bookingNumber) from Booking b")
    Optional<Long> findMaxBookingNumber();

    @Query("select max(b.requestedEnd) from Booking b where b.apartment.id = :apartmentId and b.room.id = :roomId and b.status in (com.building.roomreservation.model.BookingStatus.APPROVED, com.building.roomreservation.model.BookingStatus.COMPLETED)")
    OffsetDateTime findLastUsage(@Param("apartmentId") Long apartmentId, @Param("roomId") Long roomId);
}
