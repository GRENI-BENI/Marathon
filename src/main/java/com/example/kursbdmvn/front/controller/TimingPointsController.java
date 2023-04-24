package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Participant;
import com.example.kursbdmvn.entity.TimingPoint;
import com.example.kursbdmvn.service.CompetitionService;
import com.example.kursbdmvn.service.GenderService;
import com.example.kursbdmvn.service.ParticipantService;
import com.example.kursbdmvn.service.TimingPointService;
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
import java.time.LocalDate;
import java.util.List;
@Component
public class TimingPointsController {
    @Autowired
    TimingPointService service;

    @Autowired
    CompetitionService competitionService;


    @FXML
    private Button addBtn;

    @Autowired
    TimingPointController timingPointController;
    @FXML
    private ListView<TimingPoint> timingPointsListView;

    List<TimingPoint> elements;
    @Autowired
    private ApplicationContext ac;

    @Value("classpath:/TimingPointWindow.fxml")
    Resource fxml;
    @Value("classpath:/icon.png")
    Resource icon;

    @FXML
    void initialize() {
        //elements = service.getAll();
        updateList();

        timingPointsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TimingPoint> observable, TimingPoint oldValue, TimingPoint newValue) -> {
            if (newValue != null) {
                try {
                    timingPointController.timingPoint = newValue;
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
                    stage.setTitle("Timing point");
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
    void addTimingPointClick(ActionEvent event) {
        TimingPoint timingPoint = new TimingPoint();
       timingPoint.setCompetition(competitionService.getAll().get(0));
       timingPoint.setNumber(1);
       timingPoint.setDistance(5);

        service.save(timingPoint);
        updateList();
    }

    private void updateList() {
        elements = service.getAll();
        ObservableList<TimingPoint> data = FXCollections.observableList(elements);
        timingPointsListView.setItems(data);
    }
}
