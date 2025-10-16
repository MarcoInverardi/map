package server.database;

/**
 * Classe DatabaseConnectionException modella un'eccezione lanciata quando non è possibile stabilire una connessione con il database.
 */
public class DatabaseConnectionException extends Exception{

    /**
     * Costruttore della classe DatabaseConnectionException.
     * @param message messaggio di errore.
     */
    public DatabaseConnectionException (String message) {
        super(message);
    }
}
