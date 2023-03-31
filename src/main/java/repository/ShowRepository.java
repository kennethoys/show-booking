package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
}
