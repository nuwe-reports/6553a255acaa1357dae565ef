package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;
    private Doctor d2;

	private Patient p1;
    private Patient p2;

    private Room r1;
    private Room r2;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    // Doctor Entity Tests

    @Test
    void should_create_doctor_with_constructor() {
        
        d1 = new Doctor("Juan","Carlos", 34, "j.carlos@hospital.accwe");

        assertThat(d1).isNotNull();
    }

    @Test
    void should_set_and_get_doctor_attributes() {
        
        d1 = new Doctor();

        d1.setFirstName("Juan");
        d1.setLastName("Carlos");
        d1.setAge(34);
        d1.setEmail("j.carlos@hospital.accwe");

        assertThat(d1.getFirstName()).isEqualTo("Juan");
        assertThat(d1.getLastName()).isEqualTo("Carlos");
        assertThat(d1.getAge()).isEqualTo(34);
        assertThat(d1.getEmail()).isEqualTo("j.carlos@hospital.accwe");
    }

    @Test
    void should_set_and_get_doctor_id() {
        
        d1 = new Doctor();
        d1.setId(1);

        
        assertThat(d1.getId()).isEqualTo(1);
    }

    @Test
    void should_check_doctors_inequality() {
        
        d1 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        d2 = new Doctor("Clarisa","Julia", 29, "c.julia@hospital.accwe");

        
        assertThat(d1).isNotEqualTo(d2);
    }

    @Test
    void should_not_be_same_doctor_different_instances_same_attributes() {

        d1 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        d2 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
       
        assertThat(d1).isNotSameAs(d2);
    }

    @Test
    void should_have_different_ids_after_doctor_creation() {

        d1 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        d2 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");

        entityManager.persist(d1);
        entityManager.persist(d2);
        entityManager.flush();

        assertThat(d1.getId()).isNotEqualTo(d2.getId());
    }

    // Patient Entity Tests

    @Test
    void should_create_patient_with_constructor() {
        
        p1 = new Patient("Juan","Carlos", 34, "j.carlos@hospital.accwe");

        assertThat(p1).isNotNull();
    }

    @Test
    void should_set_and_get_patient_attributes() {
        
        p1 = new Patient();

        p1.setFirstName("Juan");
        p1.setLastName("Carlos");
        p1.setAge(34);
        p1.setEmail("j.carlos@hospital.accwe");

        assertThat(p1.getFirstName()).isEqualTo("Juan");
        assertThat(p1.getLastName()).isEqualTo("Carlos");
        assertThat(p1.getAge()).isEqualTo(34);
        assertThat(p1.getEmail()).isEqualTo("j.carlos@hospital.accwe");
    }

    @Test
    void should_set_and_get_patient_id() {
        
        p1 = new Patient();
        p1.setId(1);

        assertThat(p1.getId()).isEqualTo(1);
    }

    @Test
    void should_check_patients_inequality() {
        
        p1 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        p2 = new Patient("Clarisa","Julia", 29, "c.julia@hospital.accwe");

        
        assertThat(p1).isNotEqualTo(p2);
    }

    @Test
    void should_not_be_same_patient_different_instances_same_attributes() {

        p1 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        p2 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
       
        assertThat(p1).isNotSameAs(p2);
    }

    @Test
    void should_have_different_ids_after_patient_creation() {

        p1 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        p2 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");

        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.flush();

        assertThat(p1.getId()).isNotEqualTo(p2.getId());
    }

    // Room Entity Tests

    @Test
    void should_create_room_object() {

        r1 = new Room("Dermatology");

        assertThat(r1).isNotNull();
    }

    @Test
    void should_get_room_name() {

        r1 = new Room("Dermatology");

        assertThat(r1.getRoomName()).isEqualTo("Dermatology");
    }

    @Test
    void should_check_room_inequality() {

        r1 = new Room("Dermatology");
        r2 = new Room("Operations");

        assertThat(r1).isNotEqualTo(r2);
    }

    @Test
    void should_not_be_same_room_different_instances_same_name() {

        r1 = new Room("Surgery");
        r2 = new Room("Surgery");

        assertThat(r1).isNotSameAs(r2);
    }

    // Appointment Entity Tests

    @Test
    void should_create_appointment_with_constructor() {

        p1 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        d1 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        r1 = new Room("Dermatology");
        LocalDateTime startsAt = LocalDateTime.now();
        LocalDateTime finishesAt = startsAt.plusHours(2);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);

        assertThat(a1).isNotNull();
    }

    @Test
    void should_set_and_get_appointment_attributes() {

        p1 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        d1 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        r1 = new Room("Dermatology");
        LocalDateTime startsAt = LocalDateTime.now();
        LocalDateTime finishesAt = startsAt.plusHours(2);

        a1 = new Appointment();
        a1.setPatient(p1);
        a1.setDoctor(d1);
        a1.setRoom(r1);
        a1.setStartsAt(startsAt);
        a1.setFinishesAt(finishesAt);

        assertThat(a1.getPatient()).isEqualTo(p1);
        assertThat(a1.getDoctor()).isEqualTo(d1);
        assertThat(a1.getRoom()).isEqualTo(r1);
        assertThat(a1.getStartsAt()).isEqualTo(startsAt);
        assertThat(a1.getFinishesAt()).isEqualTo(finishesAt);
    }

    @Test
    void should_set_and_get_appointment_id() {

        a1 = new Appointment();
        a1.setId(1);

        assertThat(a1.getId()).isEqualTo(1);
    }

    @Test
    void should_check_appointment_inequality() {

        p1 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        d1 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        r1 = new Room("Dermatology");
        LocalDateTime startsAt = LocalDateTime.now();
        LocalDateTime finishesAt = startsAt.plusHours(2);
        LocalDateTime startsAt2 = startsAt.plusDays(2);
        LocalDateTime finishesAt2 = startsAt2.plusHours(2);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);

        assertThat(a1).isNotEqualTo(a2);
    }

    @Test
    void should_not_be_same_appointment_different_instances_same_attributes() {

        p1 = new Patient("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        d1 = new Doctor("Juan", "Carlos", 34, "j.carlos@hospital.accwe");
        r1 = new Room("Dermatology");
        LocalDateTime startsAt = LocalDateTime.now();
        LocalDateTime finishesAt = startsAt.plusHours(2);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment(p1, d1, r1, startsAt, finishesAt);
       
        assertThat(a1).isNotSameAs(a2);
    }

}
