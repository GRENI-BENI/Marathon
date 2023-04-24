package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Participant;
import com.example.kursbdmvn.entity.Registration;
import com.example.kursbdmvn.service.*;
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
public class RegistrationsController {
    @Autowired
    RegistrationService service;

    @Autowired
    ParticipantService participantService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    DistanceService distanceService;

    @Autowired
    DiscountService discountService;
    @FXML
    private Button addBtn;

    @Autowired
    RegistrationController registrationController;
    @FXML
    private ListView<Registration> registrationsListView;

    List<Registration> elements;
    @Autowired
    private ApplicationContext ac;

    @Value("classpath:/RegistrationWindow.fxml")
    Resource fxml;
    @Value("classpath:/icon.png")
    Resource icon;

    @FXML
    void initialize() {
        //elements = service.getAll();
        updateList();

        registrationsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Registration> observable, Registration oldValue, Registration newValue) -> {
            if (newValue != null) {
                try {
                    registrationController.registration = newValue;
                    Stage stage = new Stage();
                    URL url = fxml.getURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    fxmlLoader.setControllerFactory(ac::getBean);
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 900, 330);
                    //MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.DEFAULT);
                    try {
                        stage.getIcons().add(new Image(icon.getURL().toURI().toString()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Registration");
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
    void addRegistrationClick(ActionEvent event) {
        Registration registration = new Registration();
        registration.setBidNumber(1);
        registration.setCompetition(competitionService.getAll().get(0));
        registration.setDistance(distanceService.getDistancesByCompetition(competitionService.getAll().get(0)).get(0));
        registration.setParticipant(participantService.getAll().get(0));
        registration.setTimingChipNumber(1);
        registration.setDiscount(discountService.getNoDiscount());
        service.save(registration);
        updateList();
    }

    private void updateList() {
        elements = service.getAll();
        ObservableList<Registration> data = FXCollections.observableList(elements);
        registrationsListView.setItems(data);
    }

}
