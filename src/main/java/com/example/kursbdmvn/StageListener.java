package com.example.kursbdmvn;


import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import jakarta.validation.Valid;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;
    private final Resource fxml;
    private final Resource icon;
    private final ApplicationContext ac;

    StageListener(@Value("${app.appTitle}") String applicationTitle,@Value("classpath:/Main.fxml") Resource fxml, @Value("classpath:/icon.png") Resource icon, ApplicationContext ac){
        this.applicationTitle=applicationTitle;
        this.fxml=fxml;
        this.ac=ac;
        this.icon=icon;
    }
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent){

        try {
            Stage stage=stageReadyEvent.getStage();
            URL url=this.fxml.getURL();
            FXMLLoader fxmlLoader=new FXMLLoader(url);
            fxmlLoader.setControllerFactory(ac::getBean);
            Parent root= fxmlLoader.load();
            Scene scene=new Scene(root,900,574);
            //MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.DEFAULT);
            try {
                stage.getIcons().add(new Image(icon.getURL().toURI().toString()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.setTitle(this.applicationTitle);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
