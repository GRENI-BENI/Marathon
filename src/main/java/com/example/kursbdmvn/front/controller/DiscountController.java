package com.example.kursbdmvn.front.controller;

import com.example.kursbdmvn.entity.Discount;
import com.example.kursbdmvn.service.DiscountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DiscountController {


    @Autowired
    DiscountService discountService;

    public static Discount discount;


    @FXML
    private Button deleteDiscountBtn;







    @FXML
    private Button saveDiscountBtn;

    @FXML
    private Button closeDiscountBtn;



    @FXML
    private TextField discountTitleField;

    @FXML
    private TextField percentField;

    @FXML
    void deleteDiscountClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete discount confirmation");
        alert.setHeaderText("Are you sure that you want to delete discount?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            discountService.remove(discount);
            closeButtonAction();
        }

    }

    @FXML
    void saveDiscountClick(ActionEvent  event) {
        try {
            discount.setPercent(Float.parseFloat(percentField.getText()));
        } catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input error");
            alert.setHeaderText("Invalid percent input");
            alert.show();
            return;
        }
        discount.setTitle(discountTitleField.getText());


        discountService.save(discount);
    }

    @FXML
    void initialize() {
        discountTitleField.setText(discount.getTitle());
        percentField.setText(String.valueOf(discount.getPercent()));
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeDiscountBtn.getScene().getWindow();
        stage.close();
    }
}
