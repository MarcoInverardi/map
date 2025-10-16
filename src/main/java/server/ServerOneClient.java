package server;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import server.clustering.HierachicalClusterMiner;
import server.data.NoDataException;
import server.default_package.InvalidDepthException;
import server.data.Data;
import server.default_package.InvalidSizeException;
import server.distance.AverageLinkdistance;
import server.distance.SingleLinkDistance;

/**
 * Classe che si occupa della connessione con i client.
 */
public class ServerOneClient extends Thread {
    private final Socket clientSocket;
    private Data data;
    private String tableName;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    /**
     * Costruttore gestore client.
      * @param socket è il socket lato client.
     * @throws IOException se si verifica un errore I/O.
     */
    public ServerOneClient(Socket socket) throws IOException {
        /*serverSocket = new ServerSocket(port);
        this.run();*/
        this.clientSocket = socket;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.start();
    }


    /**
     * Metodo che gestisce le richieste lato client.
     */
    public void run() {
        try {
            boolean running = true;
            while (running) {
                int command = (Integer) in.readObject();
                switch (command) {
                    case 0:
                        handleLoadData(in, out);
                        break;
                    case 1:
                        handleMineDendrogram(in, out);
                        break;
                    case 2:
                        handleLoadDendrogramFromFile(in, out);
                        break;
                    default:
                        out.writeObject("Unknown command");
                        break;
                }
            }
        }catch (SocketException e){
            System.out.println("Client disconnected.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidDepthException | InvalidSizeException | NoDataException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (clientSocket != null){
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("An error has occurred.");
            }
        }
    }


    /**
     * Metodo che gestisce il caricamento dei dati dal database.
     * @param in
     * @param out
     * @throws IOException
     * @throws ClassNotFoundException se la classe non è trovata.
     * @throws NoDataException se la tabella è nulla o vuota.
     */
    private void handleLoadData(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, NoDataException {
        try {
            String tableName = (String) in.readObject();
            // Simulate loading data from database
            if (tableName != null && !tableName.isEmpty()) {
                this.data = new Data(tableName);
                this.tableName = tableName;
                out.writeObject("OK");
            } else {
                out.writeObject("Table name is empty");
            }
        }catch (EOFException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che gestisce il mining del dendrogramma.
     * @param in usato per inviare richieste dal client al server.
     * @param out usato per dare risposta dal server al client.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InvalidDepthException
     * @throws InvalidSizeException
     * @throws NoDataException
     */
    private void handleMineDendrogram(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, InvalidDepthException, InvalidSizeException, NoDataException {
        int depth = (Integer) in.readObject();
        int dType = (Integer) in.readObject();
        // Simulate dendrogram mining
        if (depth > 0 && (dType == 1 || dType == 2)) {
            out.writeObject("OK");
            Data data = new Data(tableName);
            double[][] distancematrix = data.distance();
            HierachicalClusterMiner clustering2;
            clustering2 = new HierachicalClusterMiner(depth);
            if (dType == 1) {
                clustering2.mine(data, new SingleLinkDistance());
            } else {
                clustering2.mine(data, new AverageLinkdistance());
            }

            String dendrogramString = clustering2.toString(data);
            out.writeObject(dendrogramString);
            out.writeObject("Dendrogram mined successfully");
            String fileName = (String) in.readObject();
            try (FileOutputStream fileOut = new FileOutputStream(fileName);
                 ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {
               clustering2.salva(fileName);
                System.out.println("Dendrogram saved as " + fileName);
            } catch (IOException e) {
                System.out.println("Error saving dendrogram: " + e.getMessage());
            }

            // Simulate saving the dendrogram to a file
            System.out.println("Dendrogram saved as " + fileName);
        } else {
            out.writeObject("Invalid depth or distance type");
        }
    }

    /**
     * Metodo che gestisce il caricamento del dendrogramma da un file.
     * @param in
     * @param out
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void handleLoadDendrogramFromFile(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        String fileName = (String) in.readObject();
        try {
            HierachicalClusterMiner clustering = HierachicalClusterMiner.loaHierachicalClusterMiner(fileName);
            if (data == null) {
                out.writeObject("Dati non caricati");
                return;
            }
            out.writeObject("OK");
            String cluster = clustering.toString(data);
            out.writeObject(cluster);
        } catch (FileNotFoundException e) {
            out.writeObject("File non trovato: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            out.writeObject(e.getMessage());
        } catch (NullPointerException e){
            out.writeObject("Dati non caricati");
        }
    }
}







