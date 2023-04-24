package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.*;
import com.example.kursbdmvn.service.CompetitionService;
import com.example.kursbdmvn.service.TimingPointService;
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
public class TimingPointController {

    @Autowired
    TimingPointService timingPointService;

    @Autowired
    CompetitionService competitionService;

   public TimingPoint timingPoint;
    @FXML
    private Button closeBtn;

    @FXML
    private TextField competitionIdField;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField distanceField;

    @FXML
    private TextField numberField;

    @FXML
    private Button saveBtn;

    @FXML
    void deleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete timing point confirmation");
        alert.setHeaderText("Are you sure that you want to delete timing point?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            timingPointService.remove(timingPoint);
            closeButtonAction();
        }

    }

    @FXML
    void saveClick(ActionEvent event) {

        try {
            timingPoint.setDistance(Float.parseFloat(distanceField.getText()));
            timingPoint.setNumber(Integer.parseInt(numberField.getText()));
            String str=competitionIdField.getText();
            Competition competition=competitionService.getById(NumberUtils.parseNumber(str, Long.class));
            if(competition!=null){
                timingPoint.setCompetition(competition);
            }

        } catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Invalid input");
            alert.show();
            return;
        }

        timingPointService.save(timingPoint);
    }

    @FXML
    void initialize() {

        competitionIdField.setText(String.valueOf(timingPoint.getCompetition().getId()));
        competitionIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {


                Competition competition=competitionService.getById(Long.valueOf(newValue));
                if(competition!=null) {
                    competitionIdField.setBorder(Border.stroke(Paint.valueOf("black")));
                }
                else {
                    competitionIdField.setBorder(Border.stroke(Paint.valueOf("red")));
                }
            }
            catch (NumberFormatException ex){
                competitionIdField.setBorder(Border.stroke(Paint.valueOf("red")));

            }
        });
        numberField.setText(String.valueOf(timingPoint.getNumber()));
        distanceField.setText(String.valueOf(timingPoint.getDistance()));

    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
