package bot;

import bot.UserSession;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Classe principale del bot Telegram per la gestione delle sessioni utente e dei processi
 * che ne garantisce il funzionamento.
 */
public class InvGmpBot extends TelegramLongPollingBot {

    /**
     * Mappa che memorizza sessioni utente,associando l'ID della chat
     */
    private Map<Long, UserSession> userSessions = new ConcurrentHashMap<>();

    /**
     * Esecutore usato per pianificare e gestire attività periodiche
     * come il controllo dello stato dei processi.
     */
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);



    /**
     * Gestice gli aggiornamenti ricevuti dal bot, più specificamente comandi e messaggi inviati dall'utente.
     * @param update l'oggetto che contiene il messaggio inviato dall'utente.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String command = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if (command.equals("/start")) {
            String message = "Ciao, " + update.getMessage().getFrom().getFirstName() + "!\n"
                    + "Benvenuto in MineBot! Per maggiori informazioni scrivi /help.\n"
                    + "Connessione al server...";
            sendTextMessage(chatId, message);

            new Thread(() -> startMainTestProcess(chatId)).start();
        } else if (command.equals("/stop")) {
            stopMainTestProcess(chatId);
            sendTextMessage(chatId, "Client fermato.");
        } else if (command.equals("/filesList")) {
            //visualizza una lista dei file .dat presenti nella cartella radice
            String rootDirectoryPath = ".";
            File rootDirectory = new File(rootDirectoryPath);
            if (rootDirectory.exists() && rootDirectory.isDirectory()) {
                File[] files = rootDirectory.listFiles();
                if (files != null && files.length > 0) {
                    StringBuilder fileList = new StringBuilder("Lista dei file presenti sul server:\n");
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".dat"))
                        fileList.append(file.getName()).append(file.isDirectory() ? " (Directory)" : " (File)").append("\n");
                    }
                    sendTextMessage(chatId, fileList.toString());
                } else {
                    sendTextMessage(chatId, "La directory è vuota.");
                }
            }
        } else if (command.equals("/help")) {
            String message = "Ecco una lista dei comandi disponibili:\n\n"
                    + "/start - Avvia una nuova sessione con il bot.\n"
                    + "/stop - Ferma la sessione attiva.\n"
                    + "/help - Mostra questa guida di aiuto.\n"
                    + "/info - Mostra informazioni generali sul bot.\n"
                    + "/filesList - Mostra la lista dei file (in formato .dat) presenti sul server.\n"
                    + "Nota: I comandi devono essere scritti senza virgolette.";
            sendTextMessage(chatId, message);
        } else if(command.equals("/info")){
            String message = "Questo bot utilizza algoritmi di clustering per l'analisi dei dati."
                    + "Puoi caricare dataset, configurare i parametri di clustering e "
                    + "visualizzare i risultati direttamente attraverso l'interfaccia del bot. \n\n";
            sendTextMessage(chatId, message);
        } else if (command.startsWith("/")){
        sendTextMessage(chatId, "Comando sconosciuto.");
    }else{
            try {
                handleAnswersFromClient(update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Metodo che crea e avvia un nuovo processo utilizzando 'ProcessBuilder' per eseguire la classe 'client.MainTest'.
     * Avviato il processo, il metodo crea i flussi di input e output per comunicare con esso e
     * memorizza queste informazioni nella sessione utente. Avvia un thread per ascoltare l'output del processo
     * e pianifica un'attività per controllare periodicamente lo stato del processo.
     *
     * @param chatId L'ID della chat dell'utente per cui avviare il processo.
     */
    private void startMainTestProcess(Long chatId) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "target/classes", "client.MainTest");
            Process mainTestProcess = processBuilder.start();

            BufferedReader processOutput = new BufferedReader(new InputStreamReader(mainTestProcess.getInputStream()));
            BufferedWriter processInput = new BufferedWriter(new OutputStreamWriter(mainTestProcess.getOutputStream()));

            userSessions.put(chatId, new UserSession(mainTestProcess, processOutput, processInput));

            new Thread(() -> listenForOutput(chatId)).start();
            scheduler.scheduleAtFixedRate(() -> checkMainTestStatus(chatId), 0, 5, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
            sendTextMessage(chatId, "Error starting the process: " + e.getMessage());
        }
    }



    /**
     * Metodo che interrompe il processo in esecuzione per l'utente identificato con 'chatId'.
     * Una volta che il processo è stato terminato, la sessione dell'utente viene rimossa dalla lista delle sessioni.
     *
     * @param chatId
     */
    private void stopMainTestProcess(Long chatId) {
        UserSession session = userSessions.get(chatId);
        if (session != null) {
            session.getProcess().destroy();
            userSessions.remove(chatId);
        }
    }



    /**
     * Metodo che legge continuamente l'output generato dal processo associato alla sessione dell'utente
     * identificato da 'chatId'. Ogni riga di output viene inviata all'utente tramite il bot.
     * @param chatId
     */
    private void listenForOutput(Long chatId) {
        UserSession session = userSessions.get(chatId);
        if (session == null) return;

        try {
            String line;
            while ((line = session.getProcessOutput().readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    sendTextMessage(chatId, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendTextMessage(chatId, "Error reading process output: " + e.getMessage());
        }
    }





    /**
     *Invia un messaggio di testo a un utente specifico su Telegram.
     * @param chatId
     * @param text
     */
    private void sendTextMessage(Long chatId, String text) {
        if (text == null || text.trim().isEmpty()) {
            return; // Do not send empty messages
        }
        SendMessage response = new SendMessage();
        response.setChatId(chatId.toString());
        response.setText(text);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    /**
     * Metodo che controlla lo stato del main associato alla sessione dell'utente.
     *
     * Verifica se l'utente identificato da 'chatId' ha una sessione attiva e,
     * se il processo associato alla sessione non è più attivo, invia un messaggio all'utente
     * per notificare che il programma è terminato e rimuove la sessione dalla lista delle sessioni esistenti.
     *
     * @param chatId
     */
    private void checkMainTestStatus(Long chatId) {
        UserSession session = userSessions.get(chatId);
        if (session != null && !session.getProcess().isAlive()) {
            sendTextMessage(chatId, "Programma terminato");
            userSessions.remove(chatId);
        }
    }




    /**
     * Metodo che restituisce l'Username del bot
     * @return il nome utente del bot.
     */
    @Override
    public String getBotUsername() {
        return "InvGmpBot";
    }

    /**
     * Metodo che restituisce il token del bot
     * @return il token del bot.
     */
    @Override
    public String getBotToken() {
        return "7250961972:AAFSCkMChjquRUWBssnGYVk274n2GYtXTpk";
    }

    /**
     * Gestisce le risposte inviate dal client (utente) al bot.
     *
     * Questo metodo riceve un aggiornamento dal client (utente), verifica se l'utente ha una sessione attiva,
     * e se sì, inoltra il comando ricevuto al processo corrispondente alla sessione utente.
     * Se la sessione non esiste, il metodo termina senza fare nulla.
     *
     * @param update
     * @throws IOException
     */
    public void handleAnswersFromClient(Update update) throws IOException {
        Long chatId = update.getMessage().getChatId();
        UserSession session = userSessions.get(chatId);
        if (session == null) return;

        String command = update.getMessage().getText();
        session.getProcessInput().write(command);
        session.getProcessInput().newLine();
        session.getProcessInput().flush();
    }
}