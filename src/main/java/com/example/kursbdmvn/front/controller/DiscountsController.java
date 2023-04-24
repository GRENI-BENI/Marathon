package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Discount;
import com.example.kursbdmvn.service.DiscountService;
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
import java.util.List;
@Component
public class DiscountsController {
    @Autowired
    DiscountService service;


    @FXML
    private Button addDiscountBtn;

    @Autowired
    DiscountController discountController;
    @FXML
    private ListView<Discount> discountsListView;

    List<Discount> elements;
    @Autowired
    private ApplicationContext ac;

    @Value("classpath:/DiscountWindow.fxml")
    Resource fxml;
    @Value("classpath:/icon.png")
    Resource icon;

    @FXML
    void initialize() {
        //elements = service.getAll();
        updateList();

        discountsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Discount> observable, Discount oldValue, Discount newValue) -> {
            if (newValue != null) {
                try {
                    discountController.discount = newValue;
                    Stage stage = new Stage();
                    URL url = fxml.getURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    fxmlLoader.setControllerFactory(ac::getBean);
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root, 900, 120);
                    //MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.DEFAULT);
                    try {
                        stage.getIcons().add(new Image(icon.getURL().toURI().toString()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Discount");
                    stage.setOnHidden(handler->{ updateList();});
                    stage.show();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    @FXML
    void addDiscountClick(ActionEvent event) {
        Discount discount=new Discount();
        discount.setTitle("Title");
        discount.setPercent(0.05f);
        service.save(discount);
        updateList();
    }

    private void updateList() {
        elements= service.getAll();
        ObservableList<Discount> data = FXCollections.observableList(elements);
        discountsListView.setItems(data);
    }
}
