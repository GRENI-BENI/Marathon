package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.entity.Gender;
import com.example.kursbdmvn.entity.Participant;
import com.example.kursbdmvn.service.GenderService;
import com.example.kursbdmvn.service.ParticipantService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class ParticipantController {
    @Autowired
    ParticipantService participantService;

    public Participant participant;

    @Autowired
    GenderService genderService;

    @FXML
    private Button closeBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField firstNameField;

    @FXML
    private ComboBox<Gender> gendersCombo;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField secondNameField;


    @FXML
    void deleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete participant confirmation");
        alert.setHeaderText("Are you sure that you want to delete participant?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            participantService.remove(participant);
            closeButtonAction();
        }

    }

    @FXML
    void saveClick(ActionEvent event) {
        participant.setDateOfBirth(dobPicker.getValue());
        participant.setFirstName(firstNameField.getText());
        participant.setLastName(secondNameField.getText());
        participant.setGender(gendersCombo.getValue());
        participantService.save(participant);
    }

    @FXML
    void initialize() {

        ObservableList<Gender> genders = FXCollections.observableArrayList(genderService.getAll());
        gendersCombo.setItems(genders);
        gendersCombo.getSelectionModel().select(participant.getGender());

        firstNameField.setText(participant.getFirstName());
        secondNameField.setText(participant.getLastName());

        dobPicker.setValue(participant.getDateOfBirth());
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
