module com.jpdev.desafio_bradesco {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.jpdev.desafio_bradesco.controller to javafx.fxml;
    opens com.jpdev.desafio_bradesco.view to javafx.graphics;

    exports com.jpdev.desafio_bradesco;
}

