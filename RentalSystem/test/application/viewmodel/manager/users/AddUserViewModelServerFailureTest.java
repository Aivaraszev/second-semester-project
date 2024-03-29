package application.viewmodel.manager.users;

import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.viewmodel.manager.users.AddUserViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.client.FailingRentalSystemClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AddUserViewModelServerFailureTest {
    private AddUserViewModel viewModel;
    private StringProperty error;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new AddUserViewModel((ManagerModel) model);
        error = new SimpleStringProperty();
        viewModel.bindError(error);
    }

    @Test
    public void server_failure_during_adding_new_user() {
        boolean result = viewModel.addUser("Manager");
        assertEquals("Failed to add user to the database", error.get());
        assertFalse(result);
    }
}
