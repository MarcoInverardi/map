class HierachicalClusterMiner {
	
	private Dendrogram dendrogram;

	
	
	
	HierachicalClusterMiner(int depth) {
		dendrogram= new Dendrogram(depth);
	
	}
	
	
	public String toString() {
		return dendrogram.toString();
	}
	
	String toString(Data data) {
		return dendrogram.toString(data);
	}

	void mine(Data data, ClusterDistance distance ){
		void mine(Data data, ClusterDistance distance) {
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
			while (clusterSet.getSize() > 1) {
				ClusterSet newClusterSet = clusterSet.mergeClosestClusters(distance, data);
				level++;
				dendrogram.setClusterSet(newClusterSet, level);
				clusterSet = newClusterSet;
			}
		}
	}
	
}