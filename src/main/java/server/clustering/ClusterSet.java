package server.clustering;

import server.default_package.InvalidSizeException;
import server.distance.ClusterDistance;
import server.data.Data;
import java.util.Iterator;
import java.io.Serializable;

/**
 * Classe ClusterSet implementa un insieme di cluster.
 */
class ClusterSet implements ClusterDistance, Serializable {

	private final Cluster[] C;
	private int lastClusterIndex = 0;


	/**
	 * Costruttore di ClusterSet che crea un'istanza di classe clusterSet di dimensione k.
	 * @param k dimensione dell'insieme di cluster.
	 */
	ClusterSet(int k) { //inizializza il ClusterSet (dimensione k)
		C = new Cluster[k];
	}


	/**
	 * Metodo che aggiunge il cluster c all'insieme cluster e controlla che il cluster non sia già presente nell'insieme.
	 *
	 *  @param c indica il cluster da aggiungere all'insieme.
	 */
	void add(Cluster c) {
		for (int j = 0; j < lastClusterIndex; j++)
			if (c == C[j]) // to avoid duplicates
				return;
		C[lastClusterIndex] = c;
		lastClusterIndex++;
	}


	/**
	 * Metodo che restituisce il cluster in posizione i.
	 * @param i indice del cluster da restituire.
	 * @return cluster in posizione i.
	 */
	Cluster get(int i) {
		return C[i];
	}


	/**
	 * Metodo che restituisce una stringa contenente gli indici degli esempi raggruppati nei cluster.
	 * @return str stringa contenente gli indici degli esempi raggruppati nei cluster.
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str += "cluster " + i + ":" + C[i] + "\n";

			}
		}
		return str;

	}


	/**
	 * Metodo che restituisce una stringa contenente gli esempi raggruppati nei cluster.
	 * @param data oggetto di classe Data che modella il dataset su cui il clustering è calcolato.
	 * @return str stringa contenente gli esempi raggruppati nei cluster.
	 */
	String toString(Data data) {
		String str = "";
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str += "cluster" + i + ":" + C[i].toString(data) + "\n";

			}
		}
		return str;

	}


	/**
	 * Metodo che restituisce un nuovo insieme di cluster che è l'unione dei due cluster più vicini.
	 * @param distance interfaccia di calcolo della distanza tra due cluster.
	 * @param data dataset.
	 * @return insieme dei cluster con i due cluster più vicini, fusi.
	 * @throws InvalidSizeException
	 */
	ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) throws InvalidSizeException {
		ClusterSet newCS = new ClusterSet(this.C.length);
		Cluster c1 = null;
		Cluster c2 = null;
		double distanzaMinima = Double.MAX_VALUE;
		int minimo = 0;

		for (int i = 0; i < this.C.length; i++) {
			for (int j = i + 1; j < this.C.length; j++) {
				if (this.C[i] != null && this.C[j] != null) {
					double d = distance.distance(this.C[i], this.C[j], data);
					if (d < distanzaMinima) {
						distanzaMinima = d;
						c1 = this.C[i];
						c2 = this.C[j];
						minimo = i;
					}
				}
			}
		}

		if (c1 != null && c2 != null) {
			Cluster newC = c1.mergeCluster(c2);
			for (int i = 0; i < this.C.length; i++) {
				if (i == minimo) {
					newCS.add(newC);
				} else if (this.C[i] != null && this.C[i] != c1 && this.C[i] != c2) {
					newCS.add(this.C[i]);
				}
			}
		}

		return newCS;
	}


	/**
	 * Metodo che calcola la distanza euclidea tra due cluster.
	 * @param c1 cluster 1.
	 * @param c2 cluster 2.
	 * @param d Data.
	 * @return dist calcolo distanza euclidea.
	 * @throws InvalidSizeException
	 */
	public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {

		if (c1.getSize() != c2.getSize()) {
			throw new InvalidSizeException("Clusters' dimensions are different");
		}

		double dist = 0.0;
		Iterator it1 = c1.iterator();
		Iterator it2 = c2.iterator();
		for (int i = 0; i < c1.getSize(); i++) {
			dist += ((Double)it1.next() - (Double)it2.next() * (Double)it1.next() - (Double)it2.next());
		}
		return dist;
	}

}

