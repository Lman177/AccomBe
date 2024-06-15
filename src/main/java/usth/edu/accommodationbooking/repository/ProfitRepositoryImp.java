package usth.edu.accommodationbooking.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import usth.edu.accommodationbooking.response.ProfitResponse;

import java.util.List;
@Repository
public class ProfitRepositoryImp implements ProfitRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProfitResponse> getProfit(){
        String jpql = "SELECT new usth.edu.accommodationbooking.response.ProfitResponse(" +
                "r.owner.id, " +
                "EXTRACT(YEAR FROM br.checkInDate), " +
                "EXTRACT(MONTH FROM br.checkInDate), " +
                "SUM(br.Price)) " +
                "FROM BookedRoom br JOIN br.room r " +
                "GROUP BY r.owner.id, EXTRACT(YEAR FROM br.checkInDate), EXTRACT(MONTH FROM br.checkInDate)";
        TypedQuery<ProfitResponse> query = entityManager.createQuery(jpql, ProfitResponse.class);
        return query.getResultList();
    }
}
