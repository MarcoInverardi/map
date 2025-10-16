package server.data;

import server.default_package.InvalidSizeException;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

/**
 * classe che modella un esempio
 */
public class Example implements Iterable<Double>, Serializable {
	
	private List<Double> example;

	/**
	 * metodo iterator
	 * @return
	 */
	@Override
	public Iterator<Double> iterator() {
		return example.iterator();
	}

	/**
	 * costruttore di Example
	 */
	public Example(){
		example = new LinkedList<>();
	}

	/**
	 * metodo che aggiunge un valore v all'esempio
	 * @param v
	 */
	public void add(Double v) {
		
		example.add(v);
		
	}

	/**
	 * metodo che restituisce il numero presente all'indice index
	 * @param index
	 * @return numero alla posizione index
	 */
	public Double get(int index) {
		
		return example.get(index);
		
	}

	/**
	 * metodo che calcola la distanza tra due esempi
	 * @param newE
	 * @return dist (distanza tra due esempi)
	 * @throws InvalidSizeException
	 */
	public double distance(Example newE) throws InvalidSizeException {
		
		int dist = 0;

		if (this.example.size() != newE.example.size()) {

			throw new InvalidSizeException("The two examples have different sizes.");

		}
		
		Iterator it1 = this.iterator();
		Iterator it2 = newE.iterator();
		
		while (it1.hasNext()) {
			Double value1 = (Double)it1.next();
			Double value2 = (Double)it2.next();
			dist += (value1 - value2) * (value1 - value2);
		}
		
		return dist;
		
	}

	/**
	 * metodo che restituisce una rappresentazione testuale dell'esempio
	 * @return la stringa che rappresenta testualmente l'esempio
	 */
	public String toString(){

		StringBuilder ans = new StringBuilder();

		ans.append("[");

		for (int i=0; i<example.size(); i++) {

			ans.append(example.get(i));

			if (i != example.size()-1) {

				ans.append(", ");

			}

		}

		ans.append("]");

		return ans.toString();
	}


	/**
	 * Metodo che restituisce i valori di Example.
	 * @param newE istanza di classe Example.
	 * @return la stringa che rappresenta testualmente l'esempio.
	 */
	public String toString(Example newE) {
		
		StringBuilder ans = new StringBuilder();
		
		ans.append("[");

		for (int i=0; i<newE.example.size(); i++) {
			
			ans.append(get(i));
			
			if (i != newE.example.size()-1) {
				
				ans.append(", ");
				
			}
			
		}
		
		ans.append("]");
		
		return ans.toString();

	}
	
	
}