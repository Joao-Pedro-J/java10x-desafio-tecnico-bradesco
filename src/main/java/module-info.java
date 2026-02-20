module com.jpdev.desafio_bradesco {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.jpdev.desafio_bradesco.view to javafx.graphics;
    opens com.jpdev.desafio_bradesco.database.model to javafx.base, javafx.fxml;

    exports com.jpdev.desafio_bradesco;
    opens com.jpdev.desafio_bradesco.controller.dialogo_buscar to javafx.fxml;
    opens com.jpdev.desafio_bradesco.controller.tela_replicador to javafx.fxml;
}

