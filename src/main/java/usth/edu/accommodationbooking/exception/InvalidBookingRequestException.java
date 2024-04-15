package usth.edu.accommodationbooking.exception;

public class InvalidBookingRequestException extends RuntimeException{
    public InvalidBookingRequestException(String message) {
        super(message);
    }
}
