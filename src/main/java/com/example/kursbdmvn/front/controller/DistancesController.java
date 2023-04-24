package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Discount;
import com.example.kursbdmvn.entity.Distance;
import com.example.kursbdmvn.service.CompetitionService;
import com.example.kursbdmvn.service.DiscountService;
import com.example.kursbdmvn.service.DistanceService;
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
import java.util.List;
@Component
public class DistancesController {
    @Autowired
    DistanceService service;

@Autowired
    CompetitionService competitionService;
    @FXML
    private Button addDistanceBtn;

    @Autowired
    DistanceController distanceController;
    @FXML
    private ListView<Distance> distancesListView;

    List<Distance> elements;
    @Autowired
    private ApplicationContext ac;

    @Value("classpath:/DistanceWindow.fxml")
    Resource fxml;
    @Value("classpath:/icon.png")
    Resource icon;

    @FXML
    void initialize() {
        //elements = service.getAll();
        updateList();

        distancesListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Distance> observable, Distance oldValue, Distance newValue) -> {
            if (newValue != null) {
                try {
                    distanceController.distance = newValue;
                    Stage stage = new Stage();
                    URL url = fxml.getURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    fxmlLoader.setControllerFactory(ac::getBean);
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 900, 265);
                    //MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.DEFAULT);
                    try {
                        stage.getIcons().add(new Image(icon.getURL().toURI().toString()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Distance");
                    stage.setOnHidden(handler->{ updateList();});
                    stage.show();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    @FXML
    void addDistanceClick(ActionEvent event) {
        Distance distance=new Distance();

        distance.setCompetition(competitionService.getAll().get(0));
        distance.setTitle("Title");
        distance.setLength(10);
        distance.setMaxAge(100);
        distance.setMinAge(10);
        distance.setParticipantsLimit(200);
        distance.setCost(BigDecimal.valueOf(100.0));
        service.save(distance);
        updateList();
    }

    private void updateList() {
        elements= service.getAll();
        ObservableList<Distance> data = FXCollections.observableList(elements);
        distancesListView.setItems(data);
    }
}
