package server.default_package;

/**
 * Classe che modella InvalidDepthException che viene lanciata quando la profondità passata al costruttore di HierachicalClusterMiner non è valida.
 */
public class InvalidDepthException extends Exception {
    public InvalidDepthException(String message) {
        super(message);
    }
}
