package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingDto> findAll() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toDto)
                .toList();
    }

    public BookingDto findById(Long id) {
        return BookingMapper.toDto(bookingRepository.findById(id));
    }

    public BookingDto create(BookingDto bookingDto) {
        return BookingMapper.toDto(bookingRepository.create(BookingMapper.fromDto(bookingDto)));
    }

    public BookingDto update(BookingDto newBookingDto) {
        return BookingMapper.toDto(bookingRepository.update(BookingMapper.fromDto(newBookingDto)));
    }

    public void delete(Long id) {
        bookingRepository.delete(id);
    }
}
