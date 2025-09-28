// src/main/java/salca_bachir/App.java
package salca_bachir;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/tabla.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(App.class.getResource("/css/style.css").toExternalForm());
        stage.setTitle("Tabla de Clientes");
        stage.setScene(scene);
        stage.show();
        LOGGER.info("Aplicación iniciada");
    }

    public static void main(String[] args) {
        launch(); //
    }
}