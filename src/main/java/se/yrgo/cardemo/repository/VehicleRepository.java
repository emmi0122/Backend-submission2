package se.yrgo.cardemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.yrgo.cardemo.domain.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    //Get all vehicles for a specific customer (through the customers ID)
    @Query("SELECT v FROM Vehicle v WHERE v.customer.id = :customerId")
    List<Vehicle> findVehicleByCustomerId(@Param("customerId") Long customerId);

    //Get all vehicles with their brand
    @Query("SELECT v FROM Vehicle v")
    List<Vehicle> findAllVehiclesWithBrand();
}
