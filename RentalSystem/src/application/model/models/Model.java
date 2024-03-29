package application.model.models;

import application.model.equipment.Equipment;
import application.model.equipment.EquipmentManager;
import application.model.reservations.*;
import application.model.users.User;
import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject {
    /**
     * Adds new equipment with given parameters to the database and to the equipmentList field.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     * Calls {@link application.client.RentalSystemClient#addEquipment(String, String, boolean) addEquipment} method.
     *
     * @param model     equipment's model
     * @param category  equipment's category
     * @param available equipment's availability
     * @throws RemoteException indicates connection issues
     */
    void addEquipment(String model, String category, boolean available) throws RemoteException;

    /**
     * Gets all stored equipment from the equipmentList.
     * Delegates to {@link EquipmentManager#getAllEquipment() getAllEquipment} method.
     *
     * @return {@link ArrayList<Equipment>} of all stored equipment
     */
    ArrayList<Equipment> getAllEquipment();

    /**
     * Gets all stored reservations that were made by the current user
     *
     * @return {@link ArrayList < Reservation >} of all stored reservations
     */
    ArrayList<Reservation> getCurrentUserReservations();
    /**
     * Gets the amount of currently overdue equipment of the current user
     *
     * @return number of equipment overdue
     */
    int getCurrentUserOverDueEquipmentAmount();

    /**
     * Gets all available equipment from the equipmentList.
     * Delegates to {@link EquipmentManager#getAllAvailableEquipment() getAllAvailableEquipment} method.
     *
     * @return {@link ArrayList<Equipment>} of all available equipment
     * @throws RemoteException indicates connection issues
     */
    ArrayList<Equipment> getAllAvailableEquipment() throws RemoteException;

    /**
     * Clears equipmentList and populates it by calling {@link application.client.RentalSystemClient#getAllEquipment() getAllEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllEquipment() throws RemoteException;

    /**
     * Clears equipmentList and populates it by calling {@link application.client.RentalSystemClient#getAllUnreservedEquipment() getAllUnreservedEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllUnreservedEquipment() throws RemoteException;

    /**
     * Adds new user to the database with given parameters.
     * Delegates to {@link application.client.RentalSystemClient#addUser(String, String, String, String, String, boolean) addUser} method.
     *
     * @param firstName   user's first name
     * @param lastName    user's last name
     * @param phoneNumber user's phone number
     * @param email       user's email address
     * @param password    user's password
     * @param isManager   user's role (true - manager, false - rentee)
     * @throws RemoteException indicates connection issues
     */
    void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException;

    /**
     * Gets the user from the database by given email address.
     * Delegates to {@link application.client.RentalSystemClient#getUser(String) getUser} method.
     *
     * @param email user's email
     * @return {@link User} that has been added
     * @throws RemoteException indicates connection issues
     */
    User getUser(String email) throws RemoteException;

    /**
     * Clears userList and populates it by calling {@link application.client.RentalSystemClient#getAllUsers() getAllUsers} method.
     * Fires property change on USER_LIST_CHANGED event.
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllUsers() throws RemoteException;

    /**
     * Returns a list of all registered users.
     *
     * @return list of all users
     */
    ArrayList<User> getAllUsers();

    /**
     * Deletes the user with given email address from the list of registered users.
     * Delegates to {@link application.client.RentalSystemClient#deleteUser(String) deleteUser} method.
     *
     * @param email user's email
     * @throws RemoteException indicates connection issues
     */
    void deleteUser(String email) throws RemoteException;

    /**
     * Returns a String indicating who the user should be treated as (Manager, Rentee or Invalid)
     * Calls {@link application.client.RentalSystemClient#isValidUser(String, String) isValidUser} method to check if the user is valid.
     * Calls {@link application.client.RentalSystemClient#isUserAManager(String) isUserAManager} method to check if the user is a manager, otherwise, rentee.
     *
     * @param email    user's email address
     * @param password user's password
     * @return String(Manager, Rentee or Invalid) indicating user's role or invalid
     * @throws RemoteException indicates connection issues
     */
    String logIn(String email, String password) throws RemoteException;

    /**
     * Toggles the availability of the given equipment.
     * Calls {@link application.client.RentalSystemClient#setAvailability(int, boolean) setAvailability} method with an appropriate boolean value.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     *
     * @param equipment equipment object
     * @throws RemoteException indicates connection issues
     */
    void toggleAvailability(Equipment equipment) throws RemoteException;

    /**
     * Delegates to {@link ReservationManager#getUnapprovedReservations() getUnapprovedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of unapproved reservations
     */
    ArrayList<Unapproved> getUnapprovedReservations();

    /**
     * Delegates to {@link ReservationManager#getApprovedReservations() getApprovedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of approved reservations
     */
    ArrayList<Approved> getApprovedReservations();

    /**
     * Delegates to {@link ReservationManager#getRejectedReservations() getRejectedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of rejected reservations
     */
    ArrayList<Rejected> getRejectedReservations();

    /**
     * Delegates to {@link ReservationManager#getReturnedReservations() getReturnedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of returned reservations
     */
    ArrayList<Returned> getReturnedReservations();

    /**
     * Delegates to {@link ReservationManager#getExpiredReservations() getExpiredReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of expired reservations
     */
    ArrayList<Expired> getExpiredReservations();

    /**
     * Approved the reservation with given id by the manager with given manager_id.
     * Delegates to {@link application.client.RentalSystemClient#approveReservation(int, String) approveReservation} method.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @throws RemoteException indicates connection issues
     */
    void approveReservation(int id, String manager_id) throws RemoteException;

    /**
     * Rejects the reservation with given id by the manger with given manager_id.
     * Delegates to {@link application.client.RentalSystemClient#rejectReservation(int, String, String) rejectReservation} method.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @param reason     reason given by the manager for rejection
     * @throws RemoteException indicates connection issues
     */
    void rejectReservation(int id, String manager_id, String reason) throws RemoteException;

    /**
     * Makes the reservation with given id returned.
     * Delegates to {@link application.client.RentalSystemClient#returnReservation(int) returnReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection issues
     */
    void returnReservation(int id) throws RemoteException;

    /**
     * Reserves the equipment with given id, by rentee with given id until certain end date
     * Delegates to {@link application.client.RentalSystemClient#reserveEquipment(int, String, LocalDateTime) reserveEquipment} method.
     *
     * @param equipment_id equipment's id
     * @param rentee_id    rentee's id
     * @param rentedFor    expiration date of the reserved equipment
     * @throws RemoteException indicates connection issues
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;

    /**
     * Returns current expiration timeout of reservations.
     * Delegates to {@link application.client.RentalSystemClient#getExpirationTimeout() getExpirationTimeout} method.
     *
     * @return expiration timeout of reservations
     * @throws RemoteException indicates connection issues
     */
    int getExpirationTimeout() throws RemoteException;

    /**
     * Sets expiration timeout for reservations.
     * Delegates to {@link application.client.RentalSystemClient#setExpirationTimeout(int) setExpirationTimeout} method.
     *
     * @param expirationTimeout new expiration timeout in seconds
     */
    void setExpirationTimeout(int expirationTimeout) throws RemoteException;

    /**
     * Returns currently logged-in user.
     *
     * @return user object
     */
    User getCurrentlyLoggedInUser();

    /**
     * Sets a new logged-in user.
     *
     * @param newUser user to be set as logged-in
     */
    void setCurrentlyLoggedInUser(User newUser);

    /**
     * Refreshes reservationList by setting it with a call to
     * {@link application.client.RentalSystemClient#retrieveReservations() retrieveReservations} method.
     * Fires property change on RESERVATION_LIST_CHANGED event.
     */
    void refreshReservations() throws RemoteException;

    /**
     * Returns true if successfully reconnected the client to the server.
     * Otherwise, returns false.
     *
     * @return result of trying to reconnect the client to the server
     */
    boolean tryToReconnectClient();

    /**
     * Checks if there is connectivity with the server. Throws exception if it cannot connect.
     * Calls {@link application.client.RentalSystemClient#pingServer() pingServer} method.
     *
     * @throws RemoteException indicates connection failure
     */
    void pingServer() throws RemoteException;
}
