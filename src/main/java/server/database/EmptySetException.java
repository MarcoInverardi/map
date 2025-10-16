package server.database;

/**
 * Classe EmptySetException modella un'eccezione lanciata quando un insieme è vuoto.
 */
public class EmptySetException extends Exception{

    /**
     * Costruttore della classe EmptySetException.
     * @param message messaggio di errore.
     */
    public EmptySetException(String message) {
        super(message);
    }
}
