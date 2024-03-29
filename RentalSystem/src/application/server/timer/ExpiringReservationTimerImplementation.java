package application.server.timer;

import application.model.reservations.Reservation;
import application.model.reservations.Unapproved;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class ExpiringReservationTimerImplementation implements ExpiringReservationTimer, PropertyChangeListener {
    private Timer timer;
    private final HashMap<Integer,ExpiringReservation> reservationHashMap;
    private final PropertyChangeSupport pcs;
    private int expirationTimeout;

    public ExpiringReservationTimerImplementation(int expirationTimeout) {
        this.expirationTimeout = expirationTimeout;
        timer = new Timer();
        reservationHashMap = new HashMap<>();
        pcs = new PropertyChangeSupport(this);
        PCSExpiringReservation.getInstance().addListener(PCSExpiringReservation.RESERVATION_EXPIRED,this);
    }

    public boolean isEmpty() {
        return reservationHashMap.isEmpty();
    }

    public void setExpirationTimeout(int timeout) {
        this.expirationTimeout = timeout;
    }

    public int getExpirationTimeout() {
        return expirationTimeout;
    }

    @Override
    public void addReservationToExpire(Reservation reservation) {
        if(!reservation.status().equals(Unapproved.status))
            throw new IllegalArgumentException("Only unapproved reservation can expire");

        if(isExpiring(reservation))
            cancelExpiration(reservation);

        ExpiringReservation expiringReservation = new ExpiringReservation((Unapproved) reservation);
        reservationHashMap.put(reservation.getId(),expiringReservation);
        timer.schedule(expiringReservation,convertToDate(reservation.getReservationDate().plusSeconds(expirationTimeout)));
    }

    private Date convertToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void cancelExpiration(int id) {
        if(!reservationHashMap.containsKey(id))
            throw new IllegalArgumentException("Id does not belong to any currently expiring reservation");

        reservationHashMap.get(id).cancel();
        reservationHashMap.remove(id);
        timer.purge();
    }

    @Override
    public void cancelExpiration(Reservation reservation) {
        cancelExpiration(reservation.getId());
    }

    @Override
    public boolean isExpiring(Reservation reservation) {
        if(!reservationHashMap.containsKey(reservation.getId()))
            return false;
        return reservationHashMap.get(reservation.getId()).getUnapprovedReservation().equals(reservation);
    }

    @Override
    public void cancelAll() {
        timer.cancel();
        this.reservationHashMap.clear();
        timer = new Timer();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case RESERVATION_EXPIRED -> {
                reservationHashMap.remove(evt.getNewValue());
                pcs.firePropertyChange(evt);
            }
        }
    }

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName,listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName,listener);
    }
}
