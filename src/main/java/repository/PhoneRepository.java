package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
