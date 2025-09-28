module salca_bachir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.slf4j;

    //  ¡ESTO ES CLAVE! Abre el paquete del controlador
    opens salca_bachir.controladores to javafx.fxml;
    opens salca_bachir to javafx.fxml;

    exports salca_bachir;
}