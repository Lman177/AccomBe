package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import usth.edu.accommodationbooking.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
   Location findByLocationName(String locationName);

}
