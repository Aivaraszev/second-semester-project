package application.view.manager.reservations;

import application.model.reservations.Approved;
import application.view.ViewHandler;
import application.viewmodel.manager.reservations.ApprovedReservationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;

public class ApprovedReservationViewController {
    @FXML
    public Label error;
    private ViewHandler viewHandler;
    private ApprovedReservationViewModel viewModel;
    private Region root;
    @FXML
    private TableView<Approved> reservationTable;
    @FXML
    private TableColumn<Approved, String> reservationIdColumn;
    @FXML
    private TableColumn<Approved, String> renteeColumn;
    @FXML
    private TableColumn<Approved, String> equipmentColumn;
    @FXML
    private TableColumn<Approved, String> approvedOnColumn;
    @FXML
    private TableColumn<Approved, String> rentedUntilColumn;
    @FXML
    public TableColumn<Approved, String> daysOverdueColumn;
    @FXML
    public TableColumn<Approved, String> returnButtonColumn;

    public void init(ViewHandler viewHandler, ApprovedReservationViewModel approvedReservationViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = approvedReservationViewModel;
        this.root = root;

        reservationIdColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(Integer.toString(p.getValue().getId()));
            } else {
                return new SimpleStringProperty("<no id>");
            }
        });

        renteeColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getRentee().toString());
            } else {
                return new SimpleStringProperty("<no rentee>");
            }
        });

        equipmentColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getEquipment().toString());
            } else {
                return new SimpleStringProperty("<no equipment>");
            }
        });

        approvedOnColumn.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getApprovedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no approve date>");
            }
        });

        rentedUntilColumn.setCellValueFactory(p -> {
            if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                return new SimpleStringProperty(p.getValue().getRentedFor().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                return new SimpleStringProperty("<no end date>");
            }
        });

        daysOverdueColumn.setCellValueFactory(p -> {
            if (p.getValue() != null && p.getValue().getRentedFor() != null) {
                return new SimpleStringProperty(p.getValue().getDaysOverdueString());
            } else {
                return new SimpleStringProperty("<no overdue amount>");
            }
        });

        returnButtonColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Approved, String> call(final TableColumn<Approved, String> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Return");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Approved reservation = getTableView().getItems().get(getIndex());
                            viewModel.removeReservation(reservation);
                            reservationTable.refresh();
                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
        viewModel.bindReservationList(reservationTable.itemsProperty());
        viewModel.bindErrorLabel(error.textProperty());
    }

    public void reset() {
        error.setText("");
    }

    public Region getRoot() {
        return root;
    }

    public void backButtonPressed() {
        viewHandler.openView(ViewHandler.MANAGER_MAIN_MENU_VIEW);
    }

}
