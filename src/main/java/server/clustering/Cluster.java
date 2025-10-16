package server.clustering;

import server.data.Data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Set;

/**
 * classe che modella un cluster
 */
public class Cluster implements Iterable<Integer>, Cloneable, Serializable {

	private Set<Integer> clusteredData = new TreeSet<>();

	/**
	 * metodo iterator
	 * @return un iteratore per il cluster
	 */
	@Override
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}

	/**
	 * metodo che aggiunge il dato id al cluster
	 * @param id
	 */
	void addData(int id) {
		clusteredData.add(id);
	}

	/**
	 * metodo che restituisce la dimensione del cluster
	 * @return la dimensione del cluster
	 */
	public int getSize() {
		return clusteredData.size();
	}

	/**
	 * crea una copia del cluster corrente
	 * @return c clone del cluster.
	 */
	public Cluster clone() {
		Cluster c = new Cluster();
		Iterator<Integer> it = clusteredData.iterator();
		while (it.hasNext()) {
			c.addData(it.next());
		}
		return c;
	}

	/**
	 * crea un nuovo cluster che fa la fusione dei due cluster pre-esistenti
	 * @param c cluster.
	 * @return newC (nuovo cluster)
	 */
	Cluster mergeCluster(Cluster c) {
		Iterator<Integer> it1 = this.iterator();
		Cluster newC = new Cluster();
		while (it1.hasNext()) {
			newC.addData(it1.next());
		}
		Iterator<Integer> it2 = c.iterator();
		while (it2.hasNext()) {
			newC.addData(it2.next());
		}
		return newC;
	}

	/**
	 * metodo che restituisce una rappresentazione testuale del cluster
	 * @return str (rappresentazione testuale del cluster)
	 */
	 public String toString() {
		String str = "";
		Iterator<Integer> it2 = clusteredData.iterator();
		int j = 0;
		while (it2.hasNext() && j < this.getSize()-1) {
			str += it2.next() + ",";
			j++;
		}
		str += it2.next();
		return str;
	}

	/**
	 * metodo che restituisce una rappresentazione testuale del cluster comprensivo di esempi
	 * @param data data.
	 * @return str (rappresentazione testuale del cluster)
	 */
	String toString (Data data) {
		String str = "";
		Iterator<Integer> it = clusteredData.iterator();
		while (it.hasNext()) {
			str += "<" + data.getExample(it.next()) + ">";
		}
		return str;

	}
}

