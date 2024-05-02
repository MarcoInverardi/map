package lab1;

class ClusterSet implements ClusterDistance {

	private Cluster C[];
	private int lastClusterIndex=0;
	
	ClusterSet(int k){ //inizializza il ClusterSet (dimensione k)
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


	/*ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) { 	//unisce due cluster con la distanza euclidea più piccola

		ClusterSet newCS = new ClusterSet(this.C.length);

		Cluster c1 = null; //inizializzo cluster c1

		Cluster c2 = null; //inizializzo cluster c2

		double distanzaMinima = Double.MAX_VALUE; //metto max value così alla prima occorrezza posso memorizzare il valore temporaneamente piu piccolo

		int minimo = 0; //intero che memorizza la posizione del cluster più vicino

		for(int i=0; i<this.C.length; i++){

			for(int j=i+1;j<this.C.length;j++){

				double d = distance.distance(this.C[i], this.C[j], data); //chiamo distance scritto qui sotto

				if (d<distanzaMinima){ //se il risultato di distance() è inferiore alla distanza minima allora...

					distanzaMinima=d; //...memorizzo la nuova distanza minima...

					c1=this.C[i]; //...il cluster c1 diventa c(i)...

					c2=this.C[j]; //...il cluster c2 diventa c(j)...

					minimo = i; //...memorizzo la posizione del minimo appena trovato.

				}

			}

		}


        Cluster newC = c1.mergeCluster(c2); //effettuo il merge dei cluster più vicini

		for (int i=0; i<this.C.length; i++){

			if (i == minimo){ //se i è proprio la posizione minima...

				newCS.add(newC); //...allora metto nel cluster che returnerò i cluster appena mergiati

			} else if (this.C[i] != c1 && this.C[i] != c2) { //se il cluster alla posizione i è diverso dai cluster c1 e c2...

				newCS.add(this.C[i]); //...allora aggiungo al cluster che returnerò ciò che si trovava già di partenza lì dentro.

			}



		}

		return newCS;
	}*/

	ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) {
		ClusterSet newCS = new ClusterSet(this.C.length);
		Cluster c1 = null;
		Cluster c2 = null;
		double distanzaMinima = Double.MAX_VALUE;
		int minimo = 0;

		for(int i=0; i<this.C.length; i++){
			for(int j=i+1;j<this.C.length;j++){
				if (this.C[i] != null && this.C[j] != null) {
					double d = distance.distance(this.C[i], this.C[j], data);
					if (d<distanzaMinima){
						distanzaMinima=d;
						c1=this.C[i];
						c2=this.C[j];
						minimo = i;
					}
				}
			}
		}

		if (c1 != null && c2 != null) {
			Cluster newC = c1.mergeCluster(c2);
			for (int i=0; i<this.C.length; i++){
				if (i == minimo){
					newCS.add(newC);
				} else if (this.C[i] != null && this.C[i] != c1 && this.C[i] != c2) {
					newCS.add(this.C[i]);
				}
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
