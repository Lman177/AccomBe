package usth.edu.accommodationbooking.service.Booking;

import jakarta.servlet.http.HttpServletRequest;
import usth.edu.accommodationbooking.model.BookedRoom;
import usth.edu.accommodationbooking.response.BookingResponse;

import java.util.List;

public interface IBookingService {
    List<BookedRoom> getAllBookings();

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);


    List<BookingResponse> getBookingsByUserEmail(String email);

   void UpdateAvailableRoom();


    List<BookingResponse> findBookingOfOwner(HttpServletRequest request);
}
