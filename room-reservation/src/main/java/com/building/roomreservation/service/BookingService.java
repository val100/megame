package com.building.roomreservation.service;

import com.building.roomreservation.model.*;
import com.building.roomreservation.repository.BookingRepository;
import com.building.roomreservation.repository.RoomRepository;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional(readOnly = true)
    public List<Booking> findOverlaps(Long roomId, OffsetDateTime start, OffsetDateTime end) {
        return bookingRepository.findOverlapping(roomId, start, end,
                Arrays.asList(BookingStatus.PENDING, BookingStatus.APPROVED, BookingStatus.COMPLETED));
    }

    @Transactional
    public Booking createRequest(Booking booking) {
        if (!booking.getRequestedEnd().isAfter(booking.getRequestedStart())) {
            throw new IllegalArgumentException("End must be after start");
        }
        List<Booking> conflicts = findOverlaps(booking.getRoom().getId(), booking.getRequestedStart(), booking.getRequestedEnd());
        boolean hasApprovedConflict = conflicts.stream().anyMatch(b -> b.getStatus() == BookingStatus.APPROVED || b.getStatus() == BookingStatus.COMPLETED);
        if (hasApprovedConflict) {
            booking.setStatus(BookingStatus.REJECTED);
            return bookingRepository.save(booking);
        }
        booking.setStatus(BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking approve(Long bookingId, User adminUser) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can be approved");
        }
        // Re-check conflicts inside transaction
        List<Booking> conflicts = findOverlaps(booking.getRoom().getId(), booking.getRequestedStart(), booking.getRequestedEnd());
        boolean approvedExists = conflicts.stream()
                .anyMatch(b -> !Objects.equals(b.getId(), booking.getId()) && (b.getStatus() == BookingStatus.APPROVED || b.getStatus() == BookingStatus.COMPLETED));
        if (approvedExists) {
            throw new IllegalStateException("Slot already approved for another booking");
        }
        booking.setStatus(BookingStatus.APPROVED);
        booking.setApprovedBy(adminUser);
        booking.setApprovedAt(OffsetDateTime.now());
        booking.setBookingNumber(nextBookingNumber());
        return bookingRepository.save(booking);
    }

    private Long nextBookingNumber() {
        Optional<Long> max = bookingRepository.findMaxBookingNumber();
        return max.map(aLong -> aLong + 1).orElse(1000L);
    }

    @Transactional
    public Optional<Booking> allocatePriorityForSlot(Long roomId, OffsetDateTime start, OffsetDateTime end) {
        List<Booking> candidates = bookingRepository.findOverlapping(roomId, start, end, Collections.singletonList(BookingStatus.PENDING));
        if (candidates.isEmpty()) return Optional.empty();

        candidates.sort((a, b) -> {
            OffsetDateTime lastA = bookingRepository.findLastUsage(a.getApartment().getId(), roomId);
            OffsetDateTime lastB = bookingRepository.findLastUsage(b.getApartment().getId(), roomId);
            int cmp;
            if (lastA == null && lastB == null) cmp = 0;
            else if (lastA == null) cmp = -1; // never used gets priority
            else if (lastB == null) cmp = 1;
            else cmp = lastA.compareTo(lastB); // older last usage first
            if (cmp != 0) return cmp;
            // fallback: createdAt
            cmp = a.getCreatedAt().compareTo(b.getCreatedAt());
            if (cmp != 0) return cmp;
            // final fallback: id
            return a.getId().compareTo(b.getId());
        });

        Booking winner = candidates.get(0);
        winner.setStatus(BookingStatus.APPROVED);
        winner.setApprovedAt(OffsetDateTime.now());
        winner.setBookingNumber(nextBookingNumber());
        bookingRepository.save(winner);

        // reject others
        for (int i = 1; i < candidates.size(); i++) {
            Booking other = candidates.get(i);
            other.setStatus(BookingStatus.REJECTED);
            bookingRepository.save(other);
        }
        return Optional.of(winner);
    }
}
