package usth.edu.accommodationbooking.exception;

public class RoomTypeAlreadyExistException  extends RuntimeException {
    public RoomTypeAlreadyExistException(String message) {
        super(message);
    }
}
