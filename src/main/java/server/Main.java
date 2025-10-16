package server;

/**
 * classe Main che avvia il server.
 */
public class Main {
    public static void main(String[] args) {
        int port = 8080;
        try {
            MultiServer server = new MultiServer(port);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
