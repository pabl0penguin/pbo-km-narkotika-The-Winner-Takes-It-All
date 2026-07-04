package com.example.tugasbesarpbonarikotikac.app;

import com.example.tugasbesarpbonarikotikac.controller.KnowledgeController;
import com.example.tugasbesarpbonarikotikac.model.KnowledgeRepository;
import com.example.tugasbesarpbonarikotikac.view.JavaFXController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final KnowledgeRepository repository = new KnowledgeRepository();
    private static final KnowledgeController controller = new KnowledgeController(repository);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/tugasbesarpbonarikotikac/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        JavaFXController javaFXController = fxmlLoader.getController();
        javaFXController.setController(controller);
        stage.setTitle("KMS, Selamat Datang Di KSM Narkotika!");
        stage.setScene(scene);
        stage.show();
    }
}
