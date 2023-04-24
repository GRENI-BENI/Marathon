package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.City;
import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.entity.Distance;
import com.example.kursbdmvn.service.CompetitionService;
import com.example.kursbdmvn.service.DistanceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
@Component
public class DistanceController {
    @Autowired
    CompetitionService competitionService;
    @Autowired
    DistanceService distanceService;

    public Distance distance;


    @FXML
    private Button closeBtn;

    @FXML
    private ComboBox<Competition> competitionsCombo;

    @FXML
    private TextField costField;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField titleField;

    @FXML
    private TextField lengthField;

    @FXML
    private TextField maxAgeField;

    @FXML
    private TextField minAgeField;

    @FXML
    private TextField participantsLimitField;

    @FXML
    private Button saveBtn;


    @FXML
    void deleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete distance confirmation");
        alert.setHeaderText("Are you sure that you want to delete distance?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            distanceService.remove(distance);
            closeButtonAction();
        }

    }

    @FXML
    void saveClick(ActionEvent  event) {
        try {
            distance.setLength(Float.parseFloat(lengthField.getText()));
            distance.setCost(new BigDecimal(Double.valueOf(costField.getText())));
            if(minAgeField.getText().equals("")||minAgeField.getText().equals("null"))
                distance.setMinAge(null);
            if(maxAgeField.getText().equals("")||maxAgeField.getText().equals("null"))
                distance.setMaxAge(null);
           // distance.setMinAge(Integer.parseInt(lengthField.getText()));
           // distance.setMaxAge(Integer.parseInt(lengthField.getText()));
            distance.setParticipantsLimit(Integer.parseInt(participantsLimitField.getText()));
        } catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText(exception.getMessage());
            alert.show();
            return;
        }
        distance.setTitle(titleField.getText());
        distance.setCompetition(competitionsCombo.getValue());
        distanceService.save(distance);
    }

    @FXML
    void initialize() {
        titleField.setText(distance.getTitle());
        lengthField.setText(String.valueOf(distance.getLength()));
        costField.setText(String.valueOf(distance.getCost()));
        maxAgeField.setText(String.valueOf(distance.getMaxAge()));
        minAgeField.setText(String.valueOf(distance.getMinAge()));
        participantsLimitField.setText(String.valueOf(distance.getParticipantsLimit()));
        ObservableList<Competition> competitions = FXCollections.observableArrayList(competitionService.getAll());
        competitionsCombo.setItems(competitions);
        competitionsCombo.getSelectionModel().select(distance.getCompetition());
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
