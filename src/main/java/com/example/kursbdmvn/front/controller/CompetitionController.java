package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.City;
import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.service.CityService;
import com.example.kursbdmvn.service.CompetitionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Optional;

@Component
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @Autowired
    CityService cityService;

    Competition competition;

    @FXML
    private ComboBox<City> citiesCombo;

    @FXML
    private Button deleteCompetitionBtn;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField raceStartTimeField;

    @FXML
    private TextField registrationStartTimeField;

    @FXML
    private Button saveCompetitionBtn;

    @FXML
    private Button closeCompetitionBtn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField titleField;

    @FXML
    void deleteCompetitionClick(ActionEvent  event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete competition confirmation");
        alert.setHeaderText("Are you sure that you want to delete competition?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            competitionService.remove(competition);
            closeButtonAction();
        }

    }

    @FXML
    void saveCompetitionClick(ActionEvent event) {
        competition.setCity(citiesCombo.getSelectionModel().getSelectedItem());
        competition.setTitle(titleField.getText());
        competition.setDescription(descriptionField.getText());

        int startHour = Integer.parseInt(raceStartTimeField.getText().split(":")[0]);
        int startMinute = Integer.parseInt(raceStartTimeField.getText().split(":")[1]);
        int registrationHour = Integer.parseInt(registrationStartTimeField.getText().split(":")[0]);
        int registrationMinute = Integer.parseInt(registrationStartTimeField.getText().split(":")[1]);

        competition.setRaceStartTime(LocalTime.of(startHour, startMinute));
        competition.setRegistrationStartTime(LocalTime.of(registrationHour, registrationMinute));

        competition.setStartDate(startDatePicker.getValue());

        competitionService.save(competition);
    }

    @FXML
    void initialize() {
        titleField.setText(competition.getTitle());
        descriptionField.setText(competition.getDescription());
        startDatePicker.setValue(competition.getStartDate());
        raceStartTimeField.setText(competition.getRaceStartTime().toString());
        registrationStartTimeField.setText(competition.getRegistrationStartTime().toString());
        ObservableList<City> cities = FXCollections.observableArrayList(cityService.getAll());
        citiesCombo.setItems(cities);
        citiesCombo.getSelectionModel().select(competition.getCity());
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeCompetitionBtn.getScene().getWindow();
        stage.close();
    }
}
