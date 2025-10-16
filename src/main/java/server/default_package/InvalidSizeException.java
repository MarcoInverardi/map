package server.default_package;

/**
 * Classe che modella InvalidSizeException che viene lanciata quando la dimensione passata al costruttore di HierachicalClusterMiner non è valida.

 */
public class InvalidSizeException extends Exception {
    public InvalidSizeException(String message) {
        super(message);
    }
}
