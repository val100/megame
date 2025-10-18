package com.building.roomreservation.service;

import com.building.roomreservation.model.*;
import com.building.roomreservation.repository.BookingRepository;
import com.building.roomreservation.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private BookingService service;

    @BeforeEach
    void setup() {
        bookingRepository = mock(BookingRepository.class);
        roomRepository = mock(RoomRepository.class);
        service = new BookingService(bookingRepository, roomRepository);
    }

    @Test
    void createRequest_rejects_whenApprovedConflictExists() {
        Room room = new Room(); room.setId(1L);
        Apartment apt = new Apartment(); apt.setId(1L);
        User user = new User(); user.setId(10L);
        Booking incoming = new Booking();
        incoming.setRoom(room);
        incoming.setApartment(apt);
        incoming.setRequestedStart(OffsetDateTime.parse("2025-01-01T10:00:00Z"));
        incoming.setRequestedEnd(OffsetDateTime.parse("2025-01-01T12:00:00Z"));
        incoming.setCreatedBy(user);

        Booking existing = new Booking();
        existing.setId(2L);
        existing.setRoom(room);
        existing.setApartment(apt);
        existing.setRequestedStart(OffsetDateTime.parse("2025-01-01T09:00:00Z"));
        existing.setRequestedEnd(OffsetDateTime.parse("2025-01-01T11:00:00Z"));
        existing.setStatus(BookingStatus.APPROVED);

        when(bookingRepository.findOverlapping(eq(1L), any(), any(), any())).thenReturn(List.of(existing));
        when(bookingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Booking result = service.createRequest(incoming);
        assertEquals(BookingStatus.REJECTED, result.getStatus());
    }

    @Test
    void allocatePriority_prefersApartmentWithOldestUsageOrNeverUsed() {
        Long roomId = 1L;
        Room room = new Room(); room.setId(roomId);
        Apartment a1 = new Apartment(); a1.setId(1L);
        Apartment a2 = new Apartment(); a2.setId(2L);

        Booking b1 = new Booking(); b1.setId(11L); b1.setApartment(a1); b1.setRoom(room);
        b1.setRequestedStart(OffsetDateTime.parse("2025-01-01T10:00:00Z"));
        b1.setRequestedEnd(OffsetDateTime.parse("2025-01-01T11:00:00Z"));
        b1.setCreatedAt(OffsetDateTime.parse("2024-12-01T10:00:00Z"));
        b1.setStatus(BookingStatus.PENDING);

        Booking b2 = new Booking(); b2.setId(12L); b2.setApartment(a2); b2.setRoom(room);
        b2.setRequestedStart(OffsetDateTime.parse("2025-01-01T10:00:00Z"));
        b2.setRequestedEnd(OffsetDateTime.parse("2025-01-01T11:00:00Z"));
        b2.setCreatedAt(OffsetDateTime.parse("2024-12-01T09:00:00Z")); // earlier request, should lose if a1 never used
        b2.setStatus(BookingStatus.PENDING);

        when(bookingRepository.findOverlapping(eq(roomId), any(), any(), any())).thenReturn(List.of(b1, b2));
        when(bookingRepository.findLastUsage(eq(1L), eq(roomId))).thenReturn(null); // never used -> highest priority
        when(bookingRepository.findLastUsage(eq(2L), eq(roomId))).thenReturn(OffsetDateTime.parse("2024-01-01T00:00:00Z"));
        when(bookingRepository.findMaxBookingNumber()).thenReturn(Optional.of(1500L));
        when(bookingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Optional<Booking> winnerOpt = service.allocatePriorityForSlot(roomId,
                OffsetDateTime.parse("2025-01-01T10:00:00Z"), OffsetDateTime.parse("2025-01-01T11:00:00Z"));

        assertTrue(winnerOpt.isPresent());
        Booking winner = winnerOpt.get();
        assertEquals(a1.getId(), winner.getApartment().getId());
        assertEquals(BookingStatus.APPROVED, winner.getStatus());
        assertEquals(1501L, winner.getBookingNumber());
    }
}
