package usth.edu.accommodationbooking.service.ReviewService;

import usth.edu.accommodationbooking.model.Review;
import usth.edu.accommodationbooking.response.ReviewResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewService {
    void addReviewToRoom(Long roomId, BigDecimal rating, String comment, String response);

    List<ReviewResponse> getReviewByRoomId(Long roomId);
}
