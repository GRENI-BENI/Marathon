package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Distance;
import com.example.kursbdmvn.entity.Participant;
import com.example.kursbdmvn.service.CompetitionService;
import com.example.kursbdmvn.service.DistanceService;
import com.example.kursbdmvn.service.GenderService;
import com.example.kursbdmvn.service.ParticipantService;
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
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Component
public class ParticipantsController {
    @Autowired
    ParticipantService service;

    @Autowired
    GenderService genderService;
   // @Autowired
   // ParticipantService participantService;
    @FXML
    private Button addParticipantBtn;

    @Autowired
    ParticipantController participantController;
    @FXML
    private ListView<Participant> participantsListView;

    List<Participant> elements;
    @Autowired
    private ApplicationContext ac;

    @Value("classpath:/ParticipantWindow.fxml")
    Resource fxml;
    @Value("classpath:/icon.png")
    Resource icon;

    @FXML
    void initialize() {
        //elements = service.getAll();
        updateList();

        participantsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Participant> observable, Participant oldValue, Participant newValue) -> {
            if (newValue != null) {
                try {
                    participantController.participant = newValue;
                    Stage stage = new Stage();
                    URL url = fxml.getURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    fxmlLoader.setControllerFactory(ac::getBean);
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 900, 180);
                    //MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.DEFAULT);
                    try {
                        stage.getIcons().add(new Image(icon.getURL().toURI().toString()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Participant");
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
    void addParticipantClick(ActionEvent event) {
        Participant participant = new Participant();
        participant.setFirstName("First name");
        participant.setLastName("Last name");
        participant.setDateOfBirth(LocalDate.of(2000,1,1));
        participant.setGender(genderService.getAll().get(0));

        service.save(participant);
        updateList();
    }

    private void updateList() {
        elements = service.getAll();
        ObservableList<Participant> data = FXCollections.observableList(elements);
        participantsListView.setItems(data);
    }


}
