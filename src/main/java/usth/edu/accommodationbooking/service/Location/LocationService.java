package usth.edu.accommodationbooking.service.Location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usth.edu.accommodationbooking.model.Location;
import usth.edu.accommodationbooking.repository.LocationRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LocationService implements ILocationService{
    private final LocationRepository locationRepository;

    @Override
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }
}
