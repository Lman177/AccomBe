package usth.edu.accommodationbooking.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usth.edu.accommodationbooking.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitResponse {
    private Long  ownerId;
    private Integer year;
    private Integer month;
    private Long totalRevenue;

}
