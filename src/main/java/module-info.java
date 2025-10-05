module salca_bachir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.slf4j;
    requires java.sql;

    //  ¡ESTO ES CLAVE! Abre el paquete del controlador
    opens salca_bachir.controladores to javafx.fxml;
    opens salca_bachir to javafx.fxml;
    opens salca_bachir.modelos to javafx.base, javafx.fxml;

    exports salca_bachir;
}