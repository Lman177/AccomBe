package usth.edu.accommodationbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usth.edu.accommodationbooking.model.Review;
import usth.edu.accommodationbooking.response.ReviewResponse;
import usth.edu.accommodationbooking.service.ReviewService.ReviewService;

import java.math.BigDecimal;
import java.util.List;


@RequestMapping("/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/room/{roomId}")
    public ResponseEntity<String> addReviewToRoom(@PathVariable Long roomId,
                                                  @RequestParam BigDecimal rating,
                                                  @RequestParam String comment,
                                                    @RequestParam(required = false) String response){
        reviewService.addReviewToRoom(roomId, rating, comment, response);
        return ResponseEntity.ok("Review added successfully!");
    }

    @GetMapping("/get/{roomId}")
    public ResponseEntity<List<ReviewResponse>> getReviewByRoomId(@PathVariable Long roomId){
        List<ReviewResponse> reviews = reviewService.getReviewByRoomId(roomId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


}
