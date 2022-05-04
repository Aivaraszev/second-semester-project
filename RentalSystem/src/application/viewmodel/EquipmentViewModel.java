package application.viewmodel;

import application.model.Equipment;
import application.model.Model;
import application.model.ModelManager;
import application.model.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class EquipmentViewModel
        implements  PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Equipment>> listObjectProperty;
    private final ObjectProperty<Equipment> selectedEquipmentProperty;
    private final ObjectProperty<LocalDateTime> reservationEndDate;

    public EquipmentViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        selectedEquipmentProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.EQUIPMENT_LIST_CHANGED, this);
        reservationEndDate = new SimpleObjectProperty<>();
    }

    public void bindEquipmentList(ObjectProperty<ObservableList<Equipment>> property) {
        property.bind(listObjectProperty);
    }

    public void bindSelectedEquipment(SimpleObjectProperty<Equipment> property) {
        selectedEquipmentProperty.bind(property);
    }

    public void bindReservationEndDate(SimpleObjectProperty<LocalDateTime> property) {
        reservationEndDate.bind(property);
    }




    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.EQUIPMENT_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList((List<Equipment>) evt.getNewValue()));
            }
        }
    }

    public void reserveEquipment(){
        model.addReservation(new User("a","b"),selectedEquipmentProperty.get(),reservationEndDate.get());
    }
}
