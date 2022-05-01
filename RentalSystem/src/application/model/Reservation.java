package application.model;

import java.time.LocalDateTime;

public class Reservation {
    private boolean approved;
    private User rentee;
    private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate;
    private Equipment equipment;

    @Override
    public String toString() {
        return "Reservation{" +
                "approved=" + approved +
                ", rentee=" + rentee +
                ", reservationDate=" + reservationStartDate +
                ", equipment=" + equipment +
                '}';
    }

    public Reservation(User rentee, Equipment equipment,LocalDateTime reservationEndDate) {
        this.rentee = rentee;
        this.equipment = equipment;
        reservationStartDate = LocalDateTime.now();
        this.reservationEndDate = reservationEndDate;
        approved = false;
    }

    public boolean isApproved() {
        return approved;
    }

    public void approve() {
        approved = true;
    }

    public User getRentee() {
        return rentee;
    }

    public LocalDateTime getReservationDate() {
        return reservationStartDate;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
