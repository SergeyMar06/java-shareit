package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBooker_Id(Long userId, Sort sort);

    List<Booking> findByItem_Owner_Id(Long ownerId, Sort sort);

    List<Booking> findByBooker_IdAndEndIsBefore(
            Long userId,
            LocalDateTime time,
            Sort sort
    );

    List<Booking> findByBooker_IdAndStartIsAfter(
            Long userId,
            LocalDateTime time,
            Sort sort
    );

    List<Booking> findByBooker_IdAndStatus(
            Long userId,
            BookingStatus status,
            Sort sort
    );

    List<Booking> findByItem_Owner_IdAndStatus(
            Long ownerId,
            BookingStatus status,
            Sort sort
    );

    List<Booking> findByItem_Id(Long itemId);

    List<Booking> findByItem_IdAndBooker_IdAndStatus(Long itemId, Long bookerId, BookingStatus status);
}