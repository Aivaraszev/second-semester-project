package application.model;

import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject {
    void addEquipment(Equipment equipment) throws RemoteException;

    ArrayList<Equipment> getAllEquipment() throws RemoteException;

    ArrayList<Equipment> getAllAvailableEquipment() throws RemoteException;

    void retrieveAllEquipment() throws RemoteException;

    void addReservation(User rentee, Equipment equipment, LocalDateTime reservationEndDate) throws RemoteException;
    void addUser(User user) throws RemoteException;

    String logIn(String email, String password) throws RemoteException;

    ArrayList<Reservation> getReservationList() throws RemoteException;

    ArrayList<Reservation> getApprovedReservationList() throws RemoteException;

    void approveReservation(Reservation reservation) throws RemoteException;

    void toggleAvailability(Equipment equipment) throws RemoteException;

    void editEquipment(Equipment oldEquipment, Equipment newEquipment) throws RemoteException;

    void editEquipment(Equipment equipment, int index) throws RemoteException;

    void removeReservation(Reservation reservation) throws RemoteException;
}
