package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.service.CityService;
import com.example.kursbdmvn.service.CompetitionService;
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
import java.time.LocalTime;
import java.util.List;

@Component
public class CompetitionsController {

    @Autowired
    CompetitionService service;

    @Autowired
    CityService cityService;

    @Autowired
    CompetitionController competitionController;
    @FXML
    private ListView<Competition> competitionsListView;

    List<Competition> elements;
    @Autowired
    private ApplicationContext ac;

    @Value("classpath:/CompetitionWindow.fxml")
    Resource fxml;
    @Value("classpath:/icon.png")
    Resource icon;

    @FXML
    void initialize() {
        //elements = service.getAll();
        updateList();

        competitionsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Competition> observable, Competition oldValue, Competition newValue) -> {
            if (newValue != null) {
                try {
                    competitionController.competition = newValue;
                    Stage stage = new Stage();
                    URL url = fxml.getURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    fxmlLoader.setControllerFactory(ac::getBean);
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 900, 574);

                    //MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.DEFAULT);
                    try {
                        stage.getIcons().add(new Image(icon.getURL().toURI().toString()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Competition");
                    stage.setOnHidden(handler->{ updateList();});
                    stage.show();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private Button addCompetitionBtn;

    @FXML
    void addCompetitionClick(ActionEvent event) {
        Competition competition=new Competition();
        competition.setTitle("Title");
        competition.setDescription("Description");
        competition.setStartDate(LocalDate.now());
        competition.setRegistrationStartTime(LocalTime.now());
        competition.setRaceStartTime(LocalTime.now());
        competition.setCity(cityService.getFirst());
        service.save(competition);
        updateList();
    }

    private void updateList() {
        elements= service.getAll();
        ObservableList<Competition> data = FXCollections.observableList(elements);
        competitionsListView.setItems(data);
    }
}
