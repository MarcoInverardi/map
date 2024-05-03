package clustering;

import distance.ClusterDistance;
import data.Data;

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
