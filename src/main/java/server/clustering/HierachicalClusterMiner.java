package server.clustering;

import server.default_package.InvalidDepthException;
import server.distance.ClusterDistance;
import server.data.Data;
import server.default_package.InvalidSizeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 * Classe che implementa il clustering gerarchico.
 */
public class HierachicalClusterMiner implements Serializable {

	private final Dendrogram dendrogram;


	public HierachicalClusterMiner(int depth) throws InvalidDepthException {

		dendrogram = new Dendrogram(depth);



	}

    /**
     * metodo che carica un HierachicalClusterMiner da file
     * @param fileName nome del file
     * @return hcm
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
	public static HierachicalClusterMiner loaHierachicalClusterMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
		HierachicalClusterMiner hcm = null;
		try {

			java.io.FileInputStream file = new java.io.FileInputStream(fileName);

			java.io.ObjectInputStream in = new java.io.ObjectInputStream(file);

			hcm = (HierachicalClusterMiner) in.readObject();

			in.close();

			file.close();

		} catch (java.io.IOException e) {

			e.printStackTrace();

		}

		return hcm;
	}

    /**
     * metodo che salva su file un HierachicalClusterMiner
     * @param fileName nome del file.
     * @throws FileNotFoundException
     * @throws IOException
     */
	public void salva(String fileName) throws FileNotFoundException, IOException {

			try {

				java.io.FileOutputStream file = new java.io.FileOutputStream(fileName);

				java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(file);

				out.writeObject(this);

				out.close();

				file.close();

			} catch (java.io.IOException e) {

				e.printStackTrace();

			}

	}

    /**
     * metodo che converte in stringa testuale il dendrogramma (solo cluster)
     * @return rappresentazione testuale del dendrogramma
     */
	public String toString() {

		return dendrogram.toString();

	}

    /**
     * metodo che converte in stringa testuale il dendrogramma comprensivo di esempi
     * @param data
     * @return
     */
	public String toString(Data data) {
		return dendrogram.toString(data);
	}


    /**
     * metodo che calcola il clustering del dataset data
     * @param data
     * @param distance
     * @throws InvalidSizeException
     */
	public void mine(Data data, ClusterDistance distance) throws InvalidSizeException {

		int n = Data.getNumberOfExamples();

		ClusterSet clusterSet = new ClusterSet(n);

		// Inizializza il ClusterSet con cui popolare il dendrogramma
		for (int i = 0; i < n; i++) {

			Cluster cluster = new Cluster();

			cluster.addData(i);

			clusterSet.add(cluster);

		}

		//inizializziamo la base del dendrogramma con tutti i cluster
		int level = 0;

		dendrogram.setClusterSet(clusterSet, level);

		//effettuiamo i merge dei cluster livello per livello fino a raggiungere la radice
		for (int i=dendrogram.getDepth()-2; i>=0; i--) {

			level++;

			ClusterSet newClusterSet = clusterSet.mergeClosestClusters(distance, data);

			dendrogram.setClusterSet(newClusterSet, level);

			clusterSet = newClusterSet;

		}
	}
}