package com.jpdev.desafio_bradesco.view;

import com.jpdev.desafio_bradesco.database.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TelaReplicadorProcesso extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tela-replicador/TelaReplicadorProcessoView.fxml"));

        try {
            DbConnection.getConnection();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Conexão");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao conectar ao banco de dados: " + e.getMessage());
            alert.showAndWait();
            System.exit(1);
        }

        Scene scene = new Scene(loader.load());
        stage.setTitle("Replicação - Processo");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

        public static void main(String[] args) {
            launch(args);
        }
}
