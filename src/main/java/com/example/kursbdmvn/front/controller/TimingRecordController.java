package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.entity.Registration;
import com.example.kursbdmvn.entity.TimingPoint;
import com.example.kursbdmvn.entity.TimingRecord;
import com.example.kursbdmvn.service.RegistrationService;
import com.example.kursbdmvn.service.TimingPointService;
import com.example.kursbdmvn.service.TimingRecordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

@Component
public class TimingRecordController {
    @Autowired
    TimingRecordService timingRecordService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    TimingPointService timingPointService;

    @FXML
    private Button closeBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField registrationIdField;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField timeRecordedField;

    @FXML
    private TextField timingPointIdField;

    TimingRecord timingRecord;

    @FXML
    void deleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete timing record confirmation");
        alert.setHeaderText("Are you sure that you want to delete timing record?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            timingRecordService.remove(timingRecord);
            closeButtonAction();
        }

    }

    @FXML
    void saveClick(ActionEvent event) {

        try {
            timingRecord.setTimeRecorded(Duration.of(Long.parseLong(timeRecordedField.getText()), ChronoUnit.SECONDS));
            String registrationStr=registrationIdField.getText();
            Registration registration=registrationService.getById(NumberUtils.parseNumber(registrationStr, Long.class));
            if(registration!=null){
                timingRecord.setRegistration(registration);
            }


            String timingPointStr=timingPointIdField.getText();
            TimingPoint timingPoint=timingPointService.getById(NumberUtils.parseNumber(registrationStr, Long.class));
            if(registration!=null){
                timingRecord.setRegistration(registration);
            }

        } catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Invalid input");
            alert.show();
            return;
        }

        timingRecordService.save(timingRecord);
    }

    @FXML
    void initialize() {
        timeRecordedField.setText(String.valueOf(timingRecord.getTimeRecorded().getSeconds()));
        timingPointIdField.setText(String.valueOf(timingRecord.getTimingPoint().getId()));
        timingPointIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {


                TimingPoint timingPoint=timingPointService.getById(Long.valueOf(newValue));
                if(timingPoint!=null) {
                    timingPointIdField.setBorder(Border.stroke(Paint.valueOf("black")));
                }
                else {
                    timingPointIdField.setBorder(Border.stroke(Paint.valueOf("red")));
                }
            }
            catch (NumberFormatException ex){
                timingPointIdField.setBorder(Border.stroke(Paint.valueOf("red")));

            }
        });

        registrationIdField.setText(String.valueOf(timingRecord.getRegistration().getId()));
        registrationIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {


                Registration registration=registrationService.getById(Long.valueOf(newValue));
                if(registration!=null) {
                    registrationIdField.setBorder(Border.stroke(Paint.valueOf("black")));
                }
                else {
                    registrationIdField.setBorder(Border.stroke(Paint.valueOf("red")));
                }
            }
            catch (NumberFormatException ex){
                registrationIdField.setBorder(Border.stroke(Paint.valueOf("red")));

            }
        });


    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
