package usth.edu.accommodationbooking.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usth.edu.accommodationbooking.model.Location;
import usth.edu.accommodationbooking.service.Location.LocationService;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    @PermitAll
    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAllLocations(){
        return new ResponseEntity<>(locationService.getLocations(), HttpStatus.OK);
    }
}
