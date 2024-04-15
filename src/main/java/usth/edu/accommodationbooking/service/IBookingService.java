package usth.edu.accommodationbooking.service;

import usth.edu.accommodationbooking.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookings();

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);
}
