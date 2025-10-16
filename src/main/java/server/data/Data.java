package server.data;

import server.database.*;
import server.default_package.InvalidSizeException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

/**
 * Classe che modella un insieme di examples
 */
public class Data implements Serializable {
	private List<Example> data = new ArrayList<>();
	static int numberOfExamples;
	String nomeTabella;

	public Data(String tableName) {
		DbAccess db = new DbAccess();
		nomeTabella = tableName;
		try {
			TableData tableData = new TableData(db);
			List<Example> examples = tableData.getDistinctTransazioni(tableName);
			this.data.addAll(examples);
			numberOfExamples = examples.size();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EmptySetException e) {
			e.printStackTrace();
		} catch (MissingNumberException e) {
			e.printStackTrace();
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * restituisce il numero di esempi presenti in data
	 * @return numberOfExamples
	 */
	public static int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * restituisce l'example numero ExampleIndex
	 * @param ExampleIndex
	 * @return l'example alla posizione ExampleIndex
	 */
	public Example getExample(int ExampleIndex) {
		return data.get(ExampleIndex);
	}

	/**
	 * metodo iterator per data
	 * @return un iteratore per scorrere gli examples
	 */
	public Iterator<Example> iterator() {
		return data.iterator();
	}

	/**
	 * metodo che costruisce la matrice delle distanze
	 * @return distances (matrice)
	 * @throws InvalidSizeException
	 */
	public double [][] distance() throws InvalidSizeException {
		
		int n = numberOfExamples;
		
		double [][] distances = new double [n][n];
		
		for (int i=0; i<n; i++) {
			
			for(int j=0; j<n; j++) {
				
				if(j<=i) {
					distances[i][j]=0.0;
				}
				else {
					distances[i][j] = data.get(i).distance(data.get(j));
					
				}
						
			
			}

	    }

		return distances;

	}

	/**
	 * metodo che restituisce una rappresentazione testuale di data
	 * @return str2 (rappresentazione testuale di data)
	 */
	public String toString() {
		
			StringBuilder str = new StringBuilder();
			Iterator it = data.iterator();
			int i = 0;
			while (it.hasNext()){
				str.append(i+": " + it.next().toString()+"\n");
				i++;
			}
			String str2 = str.toString();

			return str2;

	}
}