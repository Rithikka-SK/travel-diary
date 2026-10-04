package com.rithikka.traveldiary.controller;

import com.rithikka.traveldiary.entity.Trip;
import com.rithikka.traveldiary.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
//This defines the base URL
@RequestMapping("/api/trips")

public class TripController{

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // ResponseEntity is the class that represents the entire HTTP response sent from your backend server to your frontend client.
    // It hold the body(data), status code , headers

    @PostMapping  // adding trips
    public ResponseEntity<Trip> addTrip(@RequestBody Trip trip){
        Trip savedTrip = tripService.addTrip(trip);
        return new ResponseEntity<>(savedTrip, HttpStatus.CREATED);
    }

    @GetMapping    // View all trips
    //ResponseEntity<...> - this method will return object of responseEntity class

    public ResponseEntity<List<Trip>> getAllTrips() {
        return new ResponseEntity<>(tripService.getAllTrips(), HttpStatus.OK);
    }


    @GetMapping("/{id}")      // View one trip

    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripService.getTripById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Edit a trip
    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        try {
            Trip updatedTrip = tripService.updateTrip(id, tripDetails);
            return ResponseEntity.ok(updatedTrip);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //️ Delete a trip
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}