package application.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation implements Serializable, IReservation {

    private int id;
    private User rentee;
    private LocalDateTime reservationDate;
    private LocalDateTime rentedFor;
    private Equipment equipment;
    public static final String type = "Unapproved";

    @Override
    public String status() {
        return type;
    }

    public Reservation(int id, User rentee, LocalDateTime reservationDate, LocalDateTime rentedFor, Equipment equipment) {
        this.id = id;
        this.rentee = rentee;
        this.reservationDate = reservationDate;
        this.rentedFor = rentedFor;
        this.equipment = equipment;
    }

    public Reservation(int id, User rentee, Equipment equipment, LocalDateTime rentedFor) {
        this.id = id;
        this.rentee = rentee;
        this.equipment = equipment;
        reservationDate = LocalDateTime.now();
        this.rentedFor = rentedFor;
    }


    public User getRentee() {
        return rentee;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public LocalDateTime getRentedFor() {
        return rentedFor;
    }

    public Long getDaysOverdue(){
        return DAYS.between(rentedFor, LocalDateTime.now());
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getId() {
        return id;
    }



}
