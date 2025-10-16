package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Classe Main che avvia il bot Telegram con interfaccia grafica Swing per mostrare l'output.
 */
public class ClientUi {
    public static void main(String[] args) {
        // viene creata una finestra Swing
        JFrame frame = new JFrame("Bot Telegram Output");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // viene creata una JTextArea per mostrare l'output
        JTextArea textArea = new JTextArea();
        textArea.append("Bot Telegram avviato.\nPer collegarsi al bot, cercare @InvGmpBot su Telegram.\nRicordarsi di avviare prima il server, altrimenti il bot non funzionerà.\n");
        textArea.setEditable(false); // Rende l'area di testo non modificabile
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Reindirizziamo l'output della console al JTextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        // Visualizziamo la finestra
        frame.setVisible(true);

        // Avvio del bot Telegram
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new InvGmpBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Classe interna per reindirizzare l'output della console a JTextArea.
     */
    static class TextAreaOutputStream extends OutputStream {
        private final JTextArea textArea;

        /**
         * Costruttore della classe TextAreaOutputStream
         * @param textArea Il componente 'JTextArea' che riceverà l'output del flusso.
         *                 Deve essere fornito un componente già esistente e visibile nella GUI.
         */
        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }


        /**
         * Scrive un byte nel flusso di output, aggiornando il componente associato.
         * @param b Il byte da scrivere nel flusso di output. Viene convertito in un carattere e aggiunto al componente {@code JTextArea}.
         */
        @Override
        public void write(int b) {
            // Aggiungiamo il carattere all'area di testo
            textArea.append(String.valueOf((char) b));
            // Manteniamo la vista scorrevole verso il basso
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
