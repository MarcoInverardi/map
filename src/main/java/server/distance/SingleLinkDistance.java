package server.distance;

import server.clustering.Cluster;
import server.data.Data;
import server.data.Example;
import server.default_package.InvalidSizeException;
import java.util.Iterator;

/**
 * Classe che implementa il calcolo della distanza single-link tra due cluster
 */
public class SingleLinkDistance implements ClusterDistance {

	/**
	 * metodo che calcola la distanza single-link tra due cluster
	 * @param c1 cluster 1
	 * @param c2 cluster 2
	 * @param d Data
	 * @return min (distanza minima tra i due cluster)
     */
	public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
		Iterator<Integer> it1 = c1.iterator();
		Iterator<Integer> it2 = c2.iterator();
		double min=Double.MAX_VALUE;
		Example e1;
		while (it1.hasNext()) {
			e1=d.getExample(it1.next());
			while (it2.hasNext()) {
				double distance = e1.distance(d.getExample(it2.next()));
				if (distance < min)
					min = distance;
			}
		}
		return min;
	}
}
