package se.yrgo.cardemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.yrgo.cardemo.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Get all customers and their vehicles through JOIN
    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.vehicles")
    List<Customer> findAllCustomersWithVehicles();

    //Fins customer through name and return their ID
    @Query("SELECT c.id FROM Customer c WHERE c.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);
}
