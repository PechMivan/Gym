package com.gym.gym.specifications;

import com.gym.gym.entities.Training;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecifications {
    public static Specification<Training> getFilteredTickets(TicketFilterParam params) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (params.getMovieId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("movie").<UUID> get("id"), params.getMarketerId()));
            }

            if (params.getCustomerId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("customer").<UUID> get("id"), params.getDepotId()));
            }

            if (params.getStart() != null && params.getEnd() != null) {
                predicates.add(criteriaBuilder.between(root.get("bookingDate"), params.getStart(), params.getEnd()));
            }

            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("bookingDate")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
