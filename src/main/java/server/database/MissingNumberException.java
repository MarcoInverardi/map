package server.database;


/**
 * Classe MissingNumberException modella un'eccezione lanciata quando manca un numero.
 */
public class MissingNumberException extends Exception{

    /**
     * Costruttore della classe MissingNumberException.
     * @param message messaggio di errore.
     */
    public MissingNumberException(String message) {
        super(message);
    }
}
