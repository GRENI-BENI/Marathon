package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.*;
import com.example.kursbdmvn.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.util.Optional;

@Component
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    DistanceService distanceService;

    @Autowired
    DiscountService discountService;


    public Registration registration;



    @FXML
    private TextField bidNumberField;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField competitionIdField;

    @FXML
    private Button deleteBtn;

    @FXML
    private ComboBox<Discount> discountCombo;

    @FXML
    private TextField disqualificationReasonField;

    @FXML
    private ComboBox<Distance> distanceCombo;

    @FXML
    private ComboBox<String> isDisqualifiedCombo;

    @FXML
    private ComboBox<String> isRegisteredCombo;

    @FXML
    private TextField participantIdField;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField timingChipNumberField;




    @FXML
    void deleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete registration confirmation");
        alert.setHeaderText("Are you sure that you want to delete registration?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            registrationService.remove(registration);
            closeButtonAction();
        }

    }

    @FXML
    void saveClick(ActionEvent event) {

        try {
            registration.setTimingChipNumber(Integer.parseInt(timingChipNumberField.getText()));
            registration.setBidNumber(Integer.parseInt(bidNumberField.getText()));
            String str=competitionIdField.getText();
            Competition competition=competitionService.getById(NumberUtils.parseNumber(str, Long.class));
            if(competition!=null){
                registration.setCompetition(competition);
            }
            Participant participant=participantService.getById(NumberUtils.parseNumber(participantIdField.getText(), Long.class));
            if(participant!=null){
                registration.setParticipant(participant);
            }

        } catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Invalid percent input");
            alert.show();
            return;
        }

        registration.setDisqualificationReason(disqualificationReasonField.getText());
        registration.setDisqualified(isDisqualifiedCombo.getSelectionModel().isSelected(0));
        registration.setRegistered(isRegisteredCombo.getSelectionModel().isSelected(0));
        registration.setDiscount(discountCombo.getValue());
        registration.setDistance(distanceCombo.getValue());
        registrationService.save(registration);
    }

    @FXML
    void initialize() {

        ObservableList<String> isDisqualified = FXCollections.observableArrayList("Yes","No");
        isDisqualifiedCombo.setItems(isDisqualified);
        if(registration.isDisqualified())
            isDisqualifiedCombo.getSelectionModel().select(0);
        else
            isDisqualifiedCombo.getSelectionModel().select(1);

        ObservableList<String> isRegistered = FXCollections.observableArrayList("Yes","No");
        isRegisteredCombo.setItems(isRegistered);
        if(registration.isRegistered())
            isRegisteredCombo.getSelectionModel().select(0);
        else
            isRegisteredCombo.getSelectionModel().select(1);


        competitionIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {


            Competition competition=competitionService.getById(Long.valueOf(newValue));
            if(competition!=null) {
                competitionIdField.setBorder(Border.stroke(Paint.valueOf("black")));

                ObservableList<Discount> discounts = FXCollections.observableArrayList(discountService.getByCompetition(registration.getCompetition()));
                discountCombo.setItems(discounts);
                discountCombo.getSelectionModel().select(registration.getDiscount());

                ObservableList<Distance> distances = FXCollections.observableArrayList(distanceService.getDistancesByCompetition(registration.getCompetition()));
                distanceCombo.setItems(distances);
                distanceCombo.getSelectionModel().select(registration.getDistance());
            }
            else {
                competitionIdField.setBorder(Border.stroke(Paint.valueOf("red")));
            }
            }
            catch (NumberFormatException ex){
                competitionIdField.setBorder(Border.stroke(Paint.valueOf("red")));

            }
        });




        bidNumberField.setText(String.valueOf(registration.getBidNumber()));
        disqualificationReasonField.setText(registration.getDisqualificationReason());
        competitionIdField.setText(String.valueOf(registration.getCompetition().getId()));
        timingChipNumberField.setText(String.valueOf(registration.getTimingChipNumber()));
        participantIdField.setText(String.valueOf(registration.getParticipant().getId()));

    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
