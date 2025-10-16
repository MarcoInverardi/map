package server.data;

/**
 * Classe NoDataException modella un'eccezione lanciata se il file caricato non contiene dati.
 */
public class NoDataException extends Exception {

   /**
    * Costruttore della classe NoDataException.
    * @param message messaggio di errore.
    */
   public NoDataException (String message) {super(message);}
}
