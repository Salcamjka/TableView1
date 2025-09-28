// src/main/java/salca_bachir/Lanzador.java
package salca_bachir;

/**
 * Clase lanzadora para iniciar la aplicación JavaFX.
 * Proporciona un punto de entrada estándar que delega en {@link App}.
 */
public final class Lanzador {

    private Lanzador() {
        // Clase de utilidad: constructor privado para evitar instanciación
    }

    /**
     * Punto de entrada principal de la aplicación.
     * Delega la ejecución en la clase {@link App}.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        App.main(args);
    }
}
