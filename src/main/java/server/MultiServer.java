package server;
import java.io.*;
import java.net.*;

/**
 * classe Server riferita alla gestione dei vari client
 * che si connettono al server.
 */
public class MultiServer {
    private ServerSocket serverSocket;
    int port;

    /**
     * Costruttore della classe MultiServer
     * @param port (porta a cui deve connettersi il server)
     * @throws IOException
     */
    public MultiServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.port = port;
        this.start();
    }

    /**
     * metodo che avvia il server e si mette in attesa di client
     * @throws IOException
     */
    public void start() throws IOException {
        System.out.println("Server started. Waiting for clients...");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new ServerOneClient(clientSocket);
                System.out.println("Client connected!");
                new ClientHandler(clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * classe gestore dei thread client
     */
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
    }
}

