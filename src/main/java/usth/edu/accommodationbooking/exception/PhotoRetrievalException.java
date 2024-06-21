package usth.edu.accommodationbooking.exception;

import java.sql.SQLException;

public class PhotoRetrievalException extends Throwable {
    public PhotoRetrievalException(String errorRetrievingPhoto, SQLException e) {

    }
}
