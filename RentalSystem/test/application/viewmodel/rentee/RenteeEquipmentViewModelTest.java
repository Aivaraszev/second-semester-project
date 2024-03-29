package application.viewmodel.rentee;

import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.models.RenteeModel;
import application.model.equipment.Equipment;
import application.model.reservations.Unapproved;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.client.FakeRentalSystemClient;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RenteeEquipmentViewModelTest {
    private RenteeEquipmentViewModel viewModel;
    private Model model;

    @BeforeEach
    void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new RenteeEquipmentViewModel((RenteeModel) model);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));

    }

    @Test
    void reserving_equipment_adds_to_reservations_list() {
        viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(new Equipment(0, "a", "b", false)));
        viewModel.bindReservationEndDate(new SimpleObjectProperty<>(LocalDateTime.now().plusDays(7)));
        viewModel.reserveEquipment();
        ArrayList<Unapproved> reservations = model.getUnapprovedReservations();
        assertEquals(1, reservations.size());
    }

    @Test
    void added_reservation_has_equipment_id() {
        viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(new Equipment(7, "a", "b", true)));
        viewModel.bindReservationEndDate(new SimpleObjectProperty<>(LocalDateTime.now().plusDays(7)));
        viewModel.reserveEquipment();
        Equipment equipment = model.getUnapprovedReservations().get(0).getEquipment();
        assertEquals(7, equipment.getEquipmentId());
    }

}