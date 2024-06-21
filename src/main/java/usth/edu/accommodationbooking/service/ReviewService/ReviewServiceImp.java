package usth.edu.accommodationbooking.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import usth.edu.accommodationbooking.model.Review;
import usth.edu.accommodationbooking.model.Room;
import usth.edu.accommodationbooking.model.User;
import usth.edu.accommodationbooking.repository.ReviewRepository;
import usth.edu.accommodationbooking.repository.RoomRepository;
import usth.edu.accommodationbooking.repository.UserRepository;
import usth.edu.accommodationbooking.response.ReviewResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService{
    public final ReviewRepository reviewRepository;
    public final RoomRepository roomRepository;
    public final UserRepository userRepository;



    @Override
    public void addReviewToRoom(Long roomId, BigDecimal rating, String comment, String response) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Room not found"));
        Review review = new Review();
        review.setRoom(room);
        review.setComment(comment);
        review.setRating(rating);
        review.setCreatedDate(LocalDate.now());
        review.setUser(user);
        review.setRoom(room);
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponse> getReviewByRoomId(Long roomId) {
        List<Review> reviews = reviewRepository.getReviewByRoomId(roomId);
        return reviews.stream().map(review -> new ReviewResponse(
                review.getRoom().getId(),
                review.getUser().getId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedDate()
        )).collect(Collectors.toList());
    }
}
