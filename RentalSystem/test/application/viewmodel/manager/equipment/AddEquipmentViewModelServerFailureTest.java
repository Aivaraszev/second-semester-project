package application.viewmodel.manager.equipment;

import application.client.FailingRentalSystemClient;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.viewmodel.manager.equipment.AddEquipmentViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEquipmentViewModelServerFailureTest {
    private AddEquipmentViewModel viewModel;
    private StringProperty equipmentModel;
    private StringProperty category;
    private StringProperty error;

    @BeforeEach
    void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new AddEquipmentViewModel((ManagerModel) model);
        equipmentModel = new SimpleStringProperty();
        category = new SimpleStringProperty();
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
        viewModel.bindEquipmentModel(equipmentModel);
        viewModel.bindCategory(category);
    }

    @Test
    public void server_failure_during_adding_equipment() {
        viewModel.addEquipment();
        assertEquals("Server communication error", error.get());

    }
}
