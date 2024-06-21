package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import usth.edu.accommodationbooking.model.Review;
import usth.edu.accommodationbooking.response.ReviewResponse;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.room.id = :id")
    List<Review> getReviewByRoomId(Long id);

}
