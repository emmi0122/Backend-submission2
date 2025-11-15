package se.yrgo.cardemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.cardemo.domain.Customer;
import se.yrgo.cardemo.domain.Vehicle;
import se.yrgo.cardemo.dto.CustomerDTO;
import se.yrgo.cardemo.dto.VehicleDTO;
import se.yrgo.cardemo.repository.CustomerRepository;
import se.yrgo.cardemo.repository.VehicleRepository;

@RestController
public class CarWorkshopController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // Create a customer
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    // Create a vehicle
    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }

    // Link a vehicle to a customer
    @PutMapping("/vehicles/{vehicleId}/customer/{customerId}")
    public ResponseEntity<String> linkVehicleToCustomer(
            @PathVariable Long vehicleId,
            @PathVariable Long customerId) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (vehicleOpt.isPresent() && customerOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            Customer customer = customerOpt.get();

            vehicle.setCustomer(customer);
            vehicleRepository.save(vehicle);

            return ResponseEntity.ok("Vehicle linked to customer successfully");
        }

        return ResponseEntity.badRequest().body("Vehicle or Customer not found");
    }

    //Return a list of all customers with vehicles
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomersWithVehicles() {
        List<Customer> customers = customerRepository.findAllCustomersWithVehicles();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDTO cDto = new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhoneNumber()
            );

            for (Vehicle vehicle : customer.getVehicles()) {
                VehicleDTO vDto = new VehicleDTO(
                    vehicle.getId(),
                    vehicle.getRegistrationNumber(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getProductionYear()
                );

                cDto.getVehicles().add(vDto);
            }

            customerDTOs.add(cDto);
        }

        return ResponseEntity.ok(customerDTOs);
    }

    //Return a list of all vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return ResponseEntity.ok(vehicles);
    }

    //Return all vehicles with their brand
    @GetMapping("/vehicles/brands")
    public ResponseEntity<List<VehicleDTO>> getAllVehiclesBrands() {
        List<Vehicle> vehicles = vehicleRepository.findAllVehiclesWithBrand();
        List<VehicleDTO> vehicleDTOs = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            VehicleDTO dto = new VehicleDTO(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getProductionYear()
            );

            vehicleDTOs.add(dto);
        }

        return ResponseEntity.ok(vehicleDTOs);
    }

    //Get customers name and return their id
    @GetMapping("/customers/id")
    public ResponseEntity<?> getCustomerIdByName(@RequestParam String name) {
        Optional<Long> customerId = customerRepository.findIdByName(name);

        if (customerId.isPresent()) {
            return ResponseEntity.ok(customerId.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with name: " + name);
    }
}