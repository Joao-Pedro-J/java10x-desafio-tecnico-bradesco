package com.jpdev.desafio_bradesco.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TelaReplicadorDirecao extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TelaReplicadorDirecaoView.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Replicação - Direção");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
