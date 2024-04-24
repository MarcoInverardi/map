package lab1;

class ClusterSet implements ClusterDistance {

	private Cluster C[];
	private int lastClusterIndex=0;
	
	ClusterSet(int k){
		C=new Cluster[k];
	}
	
	void add(Cluster c){
		for(int j=0;j<lastClusterIndex;j++)
			if(c==C[j]) // to avoid duplicates
				return;
		C[lastClusterIndex]=c;
		lastClusterIndex++;
	}
	
	Cluster get(int i){
		return C[i];
	}
	
	
	
	public String toString(){
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str+="cluster"+i+":"+C[i]+"\n";
		
			}
		}
		return str;
		
	}

	
	String toString(Data data){
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str+="cluster"+i+":"+C[i].toString(data)+"\n";
		
			}
		}
		return str;
		
	}


	ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) { 	//unisce due cluster con la distanza euclidea più piccola (ATTENZIONE: USERO' LA MATRICE TRIANGOLARE SUPERIORE "DATA" DELLE DISTANZE EUCLIDEE!!!)
		
		ClusterSet newCS = new ClusterSet(C.length-1);

		Cluster c1 = null; //inizializzo cluster c1

		Cluster c2 = null; //inizializzo cluster c2

		double distanzaMinima = Double.MAX_VALUE; //metto max value così alla prima occorrezza posso memorizzare il valore temporaneamente piu piccolo

		int minimo = 0; //intero che memorizza la posizione del cluster più vicino

		for(int i=0; i<C.length; i++){

			for(int j=0;j<C.length;j++){

				double d = distance(C[i], C[j], data); //chiamo distance scritto qui sotto

				if (d<distanzaMinima){ //se il risultato di distance() è inferiore alla distanza minima allora...

					distanzaMinima=d; //...memorizzo la nuova distanza minima...

					c1=C[i]; //...il cluster c1 diventa c(i)...

					c2=C[j]; //...il cluster c2 diventa c(j)...

					minimo = i; //...memorizzo la posizione del minimo appena trovato.

				}

			}

		}

		Cluster newC = c1.mergeCluster(c2); //effettuo il merge dei cluster più vicini

		for (int i=0; i<C.length; i++){

			if (i == minimo){ //se i è proprio la posizione minima...

				newCS.add(newC); //...allora metto nel cluster che returnerò i cluster appena mergiati

			} else if (C[i] != c1 && C[i] != c2) { //se il cluster alla posizione i è diverso dai cluster c1 e c2...

				newCS.add(C[i]); //...allora aggiungo al cluster che returnerò ciò che si trovava già di partenza lì dentro.

			}



		}

		return newCS;
	}
	
	public double distance(Cluster c1, Cluster c2, Data d) {

		double dist = 0.0;
		
		for (int i=0; i<c1.getSize(); i++) {
			
			dist += (c1.getElement(i) - c2.getElement(i)) * (c1.getElement(i) - c2.getElement(i));
			
		}
		
		return dist;
		
	}

}
