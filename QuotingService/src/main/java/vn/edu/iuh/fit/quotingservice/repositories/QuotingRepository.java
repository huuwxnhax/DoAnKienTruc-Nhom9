package vn.edu.iuh.fit.quotingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.quotingservice.entity.Quote;

@Repository
public interface QuotingRepository extends JpaRepository<Quote, Long> {

}
