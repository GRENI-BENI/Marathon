package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.TimingPoint;
import com.example.kursbdmvn.entity.TimingRecord;
import com.example.kursbdmvn.service.CompetitionService;
import com.example.kursbdmvn.service.RegistrationService;
import com.example.kursbdmvn.service.TimingPointService;
import com.example.kursbdmvn.service.TimingRecordService;
import jakarta.transaction.Transactional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
@Component
public class TimingRecordsController {
    @Autowired
    TimingRecordService service;
    @Autowired
    TimingPointService timingPointService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    CompetitionService competitionService;


    @FXML
    private Button addBtn;

    @Autowired
    TimingRecordController timingRecordController;
    @FXML
    private ListView<TimingRecord> timingRecordsListView;

    List<TimingRecord> elements;
    @Autowired
    private ApplicationContext ac;

    @Value("classpath:/TimingRecordWindow.fxml")
    Resource fxml;
    @Value("classpath:/icon.png")
    Resource icon;

    @FXML
    void initialize() {
        //elements = service.getAll();
        updateList();

        timingRecordsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TimingRecord> observable, TimingRecord oldValue, TimingRecord newValue) -> {
            if (newValue != null) {
                try {
                    timingRecordController.timingRecord = newValue;
                    Stage stage = new Stage();
                    URL url = fxml.getURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    fxmlLoader.setControllerFactory(ac::getBean);
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 900, 150);
                    //MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.DEFAULT);
                    try {
                        stage.getIcons().add(new Image(icon.getURL().toURI().toString()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Timing record");
                    stage.setOnHidden(handler -> {
                        updateList();
                    });
                    stage.show();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @FXML
    void addTimingRecordClick(ActionEvent event) {
        TimingRecord timingRecord = new TimingRecord();
        timingRecord.setTimingPoint(timingPointService.getAll().get(0));
        timingRecord.setTimeRecorded(Duration.ZERO);
        timingRecord.setRegistration(registrationService.getAll().get(0));
        service.save(timingRecord);
        updateList();
    }

    private void updateList() {
        elements = service.getAll();
        ObservableList<TimingRecord> data = FXCollections.observableList(elements);
        timingRecordsListView.setItems(data);
    }
}
