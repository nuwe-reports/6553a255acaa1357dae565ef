package com.example.demo.controllers;

import com.example.demo.repositories.*;
import com.example.demo.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        appointmentRepository.findAll().forEach(appointments::add);

        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {
            return new ResponseEntity<>(appointment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/appointment")
    public ResponseEntity<List<Appointment>> createAppointment(@RequestBody Appointment appointment) {
        /** TODO 
         * Implement this function, which acts as the POST /api/appointment endpoint.
         * Make sure to check out the whole project. Specially the Appointment.java class
         */
        // Verify if the appointment overlaps with any existing appointments.
        if (isOverlapping(appointment)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        // Validate that the appointment dates make sense, that is, the end date is later than the start date.
        if (!appointment.getStartsAt().isBefore(appointment.getFinishesAt())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // Create the Appointment Object with the new appointment data and save it.
        Appointment ap = new Appointment(
                appointment.getPatient(),
                appointment.getDoctor(),
                appointment.getRoom(),
                appointment.getStartsAt(),
                appointment.getFinishesAt()
        );
        appointmentRepository.save(ap);
         
        List<Appointment> updatedAppointments = appointmentRepository.findAll();
        return new ResponseEntity<>(updatedAppointments, HttpStatus.OK);
    }

    private boolean isOverlapping(Appointment appointment) {
        // Obtain all current appointments
        List<Appointment> existingAppointments = appointmentRepository.findAll();

        // Verify if the appointment overlaps with any existing appointments.
        for (Appointment existingAppointment : existingAppointments) {
            if (existingAppointment.overlaps(appointment)) {
                return true;
            }
        }

        return false;
    }


    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id) {

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (!appointment.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appointmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments() {
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
