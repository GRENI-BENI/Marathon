package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.City;
import com.example.kursbdmvn.entity.Gender;
import com.example.kursbdmvn.service.CityService;
import com.example.kursbdmvn.service.GenderService;
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
public class GendersController {

    @Autowired
    GenderService service;

    List<Gender> genders;

    @FXML
    private Button addButton;

    @FXML
    private ListView<Gender> gendersList;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField nameField;

    @FXML
    private Button saveButton;

    @FXML
    void onAddCityClick(MouseEvent event) {
        Gender gender=new Gender();
        gender.setName("Gender name");
        genders.add(gender);
        updateList();
    }

    @FXML
    void onDeleteCityClick(MouseEvent event) {
        Gender selected=gendersList.getSelectionModel().getSelectedItem();
        genders.remove(selected);
        nameField.clear();
        updateList();
        service.remove(selected);
    }

    private void updateList(){
        ObservableList<Gender> data = FXCollections.observableList(genders);
        gendersList.setItems(data);
    }

    @FXML
    void onSaveCityClick(MouseEvent event) {
        Gender gender=gendersList.getSelectionModel().getSelectedItem();
        gender.setName(nameField.getText());
        updateList();
        service.save(genders);
    }


    @FXML
    void initialize(){
        genders=service.getAll();
        updateList();

        gendersList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Gender> observable, Gender oldValue, Gender newValue) -> {
            if(newValue!=null)
                nameField.setText(newValue.getName());
        });
    }





}