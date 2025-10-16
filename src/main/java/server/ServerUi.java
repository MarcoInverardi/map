package server;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Classe Main che avvia il server con interfaccia grafica Swing per mostrare l'output.
 */
public class ServerUi {
    public static void main(String[] args) {
        // Creiamo la finestra Swing
        JFrame frame = new JFrame("Server Output");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Creiamo un JTextArea per mostrare l'output
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Rende l'area di testo non modificabile
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setResizable(false);
        // Reindirizziamo l'output della console al JTextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        // Visualizziamo la finestra
        frame.setVisible(true);

        // Avvio del server
        int port = 8080;
        try {
            MultiServer server = new MultiServer(port);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Classe interna per reindirizzare l'output della console a JTextArea.
     */
    static class TextAreaOutputStream extends OutputStream {
        private final JTextArea textArea;

        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            // Aggiungiamo il carattere all'area di testo
            textArea.append(String.valueOf((char) b));
            // Manteniamo la vista scorrevole verso il basso
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
