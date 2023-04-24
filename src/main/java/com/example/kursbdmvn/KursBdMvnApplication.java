package com.example.kursbdmvn;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KursBdMvnApplication {

    public static void main(String[] args) {
        //SpringApplication.run(KursBdMvnApplication.class, args);
        Application.launch(JavaFxApplication.class,args);
    }


}
