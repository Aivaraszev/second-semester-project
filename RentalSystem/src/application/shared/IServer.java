package application.shared;

import application.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IServer extends Remote {
    Equipment addEquipment(String model, String category, boolean available) throws RemoteException;
    ArrayList<Equipment> getAllEquipment() throws RemoteException;
    ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException;
    void setAvailability(Equipment equipment, boolean available) throws RemoteException;
    void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException;
    User getUser(String email) throws RemoteException;
    boolean isValidUser(String email, String password) throws RemoteException;
    boolean isUserAManager(String email) throws RemoteException;

    ArrayList<IReservation> retrieveReservations() throws RemoteException;

    void approveReservation(int id, String manager_id) throws RemoteException;
    void rejectReservation(int id, String manager_id) throws RemoteException;
    void expireReservation(int id) throws RemoteException;
    void returnReservation(int id) throws RemoteException;
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;
}
