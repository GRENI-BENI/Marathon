package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.City;
import com.example.kursbdmvn.service.CityService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CitiesController {

    @Autowired
    CityService cityService;

    List<City> cities;

    @FXML
    private Button addButton;

    @FXML
    private ListView<City> citiesList;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField nameField;

    @FXML
    private Button saveButton;

    @FXML
    void onAddCityClick(MouseEvent event) {
        City c=new City();
        c.setName("City name");
        cities.add(c);
        updateList();
    }

    @FXML
    void onDeleteCityClick(MouseEvent event) {
        City selected=citiesList.getSelectionModel().getSelectedItem();
        cities.remove(selected);
        nameField.clear();
        updateList();
        cityService.remove(selected);
    }

    private void updateList(){
        ObservableList<City> data = FXCollections.observableList(cities);
        citiesList.setItems(data);
    }

    @FXML
    void onSaveCityClick(MouseEvent event) {
        City c=citiesList.getSelectionModel().getSelectedItem();
        c.setName(nameField.getText());
        updateList();
        cityService.save(cities);
    }


    @FXML
    void initialize(){
        cities=cityService.getAll();
        updateList();

        citiesList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends City> observable, City oldValue, City newValue) -> {
            if(newValue!=null)
                nameField.setText(newValue.getName());
        });
    }





}

