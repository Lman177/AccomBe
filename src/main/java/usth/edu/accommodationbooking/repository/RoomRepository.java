package usth.edu.accommodationbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import usth.edu.accommodationbooking.model.Room;

import java.time.LocalDate;
import java.util.List;


public interface RoomRepository extends JpaRepository<Room, Long> {
//@Query(value = "SELECT r.id as id, r.room_description, r.room_status, r.owner_id, r.room_photo, r.room_address, r.room_price, r.room_location, r.room_type, " +
//        "rt.id as room_type_id, " +
//        "l.id as location_id " +
//        "FROM accom.room r " +
//        "LEFT JOIN accom.room_type rt ON rt.id = r.room_type " +
//        "LEFT JOIN accom.location l ON l.id = r.room_location " +
//        "WHERE rt.room_type LIKE :roomType " +
//        "AND l.district LIKE :district " +
//        "AND r.id NOT IN (" +
//        "SELECT br.room_id " +
//        "FROM accom.booked_room br " +
//        "WHERE br.check_in_date <= :checkOutDate " +
//        "AND br.check_out_date >= :checkInDate)", nativeQuery = true)
//List<Room> findAvaRoomByDate_Type_Location(LocalDate checkInDate, LocalDate checkOutDate, String roomType, String district);

//    @Query(value = "SELECT r.id as id, r.room_description, r.room_status, r.owner_id, r.room_photo, r.room_address, r.room_price, r.room_location, r.room_type, " +
//            "rt.id as room_type_id, " +
//            "l.id as location_id " +
//            "FROM accom.room r " +
//            "LEFT JOIN accom.room_type rt ON rt.id = r.room_type " +
//            "LEFT JOIN accom.location l ON l.id = r.room_location " +
//            "WHERE (:roomType IS NULL OR :roomType = '' OR rt.room_type LIKE :roomType) " +
//            "AND (:district IS NULL OR :district = '' OR l.district LIKE :district) " +
//            "AND r.id NOT IN (" +
//            "SELECT br.room_id " +
//            "FROM accom.booked_room br " +
//            "WHERE br.check_in_date <= :checkOutDate " +
//            "AND br.check_out_date >= :checkInDate)", nativeQuery = true)
//    List<Room> findAvaRoomByDate_Type_Location(LocalDate checkInDate, LocalDate checkOutDate, @Param("roomType") String roomType, @Param("roomLocation") String district);

//    @Query("select r from Room r where r.roomTypeName.name like :roomType and r.roomLocation.locationName like :roomLocation and r.id not in " +
//            "(select br.room.id from BookedRoom br where br.checkInDate <= :checkOutDate and br.checkOutDate >= :checkInDate)")
//    List<Room> findAvaRoomByDate_Type_Location(LocalDate checkInDate, LocalDate checkOutDate, @Param("roomType") String roomType, @Param("roomLocation") String roomLocation);

    @Query("SELECT r FROM Room r " +
            "WHERE " +
            "(:#{#roomType} IS NULL OR :#{#roomType} = '' OR lower(r.roomTypeName.name) LIKE concat('%', lower(:#{#roomType}), '%')) AND " +
            "(:#{#roomLocation} IS NULL OR :#{#roomLocation} = '' OR lower(r.roomLocation.locationName) LIKE concat('%', lower(:#{#roomLocation}), '%')) AND " +
            "r.id NOT IN " +
            "(SELECT br.room.id FROM BookedRoom br WHERE br.checkInDate <= :#{#checkOutDate} AND br.checkOutDate >= :#{#checkInDate})")
    List<Room> findAvaRoomByDate_Type_Location(@Param("checkInDate") LocalDate checkInDate,
                                               @Param("checkOutDate") LocalDate checkOutDate,
                                               @Param("roomType") String roomType,
                                               @Param("roomLocation") String roomLocation);


    List<Room> findByOwnerId(Long userId);

    @Query("select r from Room r where r.isBooked = false")
    List<Room> findAvailableRoom();
}
