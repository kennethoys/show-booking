package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
