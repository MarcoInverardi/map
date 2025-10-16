package bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Questa classe  tiene traccia di un processo associato a una sessione utente, nonché dei
 * flussi di input e output utilizzati per comunicare con il processo. La classe è utilizzata per
 * gestire la comunicazione tra il bot e i processi in esecuzione, permettendo l'invio di comandi e la
 * ricezione di risposte.
 */
public class UserSession {
    private Process process;
    private BufferedReader processOutput;
    private BufferedWriter processInput;

    /**
     * Costruttore della classe UserSession
     * @param process Processo associato alla sessione utente. Questo processo rappresenta il processo
     *                in esecuzione che la sessione gestisce.
     * @param processOutput Flusso di output del processo, utilizzato per leggere l'output generato dal processo.
     * @param processInput Flusso di input del processo, utilizzato per inviare comandi o dati al processo.
     */
    public UserSession(Process process, BufferedReader processOutput, BufferedWriter processInput) {
        this.process = process;
        this.processOutput = processOutput;
        this.processInput = processInput;
    }

    /**
     * Restituisce il processo associato alla sessione utente.
     * @return Il processo associato alla sessione utente, rappresentato da un oggetto (process).
     */
    public Process getProcess() {
        return process;
    }

    /**
     * Restituisce il flusso di output del processo associato alla sessione utente.
     *
     * @return Il flusso di output del processo, rappresentato da un oggetto (BufferedReader)
     */
    public BufferedReader getProcessOutput() {
        return processOutput;
    }

    /**
     *Restituisce il flusso di input del processo associato alla sessione utente.
     * @return Il flusso di input del processo, rappresentato da un oggetto (BufferedWriter).
     */
    public BufferedWriter getProcessInput() {
        return processInput;
    }
}
