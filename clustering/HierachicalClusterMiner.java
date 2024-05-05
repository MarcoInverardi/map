package distance.clustering;

import distance.default_package.InvalidDepthException;
import distance.distance.ClusterDistance;
import distance.data.Data;
import distance.default_package.InvalidSizeException;

public class HierachicalClusterMiner {

	private Dendrogram dendrogram;


	public HierachicalClusterMiner(int depth) throws InvalidDepthException {

		dendrogram = new Dendrogram(depth);


	}


	public String toString() {

		return dendrogram.toString();

	}

	public String toString(Data data) {
		return dendrogram.toString(data);
	}

	public void mine(Data data, ClusterDistance distance) throws InvalidSizeException {

		int n = data.getNumberOfExamples();

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