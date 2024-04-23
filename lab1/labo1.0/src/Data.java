class Data {
	Example data [];
	int numberOfExamples;
	
	Data(){
		
		
		//data
		
		data = new Example [5];
		Example e=new Example(3);
		e.set(0, 1.0);
		e.set(1, 2.0);
		e.set(2, 0.0);
		data[0]=e;
		
		e=new Example(3);
		e.set(0, 0.0);
		e.set(1, 1.0);
		e.set(2, -1.0);
		data[1]=e;
		
		e=new Example(3);
		e.set(0, 1.0);
		e.set(1, 3.0);
		e.set(2, 5.0);
		data[2]=e;
		
		e=new Example(3);
		e.set(0, 1.0);
		e.set(1, 3.0);
		e.set(2, 4.0);
		data[3]=e;
		
		e=new Example(3);
		e.set(0, 2.0);
		e.set(1, 2.0);
		e.set(2, 0.0);
		data[4]=e;
		
		// numberOfExamples
		numberOfExamples=5;
		
		
	}

	int getNumberOfExamples() {
		return numberOfExamples;
	}


	Example getExample(int ExampleIndex) {
		return data[ExampleIndex];
	}
	
	
	double [][] distance(){
		
		int n= numberOfExamples;
		
		double [][] distances = new double [n][n];
		
		for (int i=0; i<n; i++) {
			
			for(int j=0; j<n; j++) {
				
				if(j<=i) {
					distances[i][j]=0.0;
				}
				else {
					distances[i][j]= Data.Example[i].distance.Example(Data.Example[j]);
					
				}
						
			
			}
			
			return distances;
		
			

	    }
	}
	public String toString() {
		
			StringBuilder str=new StringBuilder ();
			
			for (int i=0; i<numberOfExamples; i++) {
				
				str.append(i+": [");
				
				for (int j=0; j<Data.Example.length; j++) {
					
					str.append(get(j));
					
					if (j != Data.Example.length-1) {
						
						str.append(", ");
					}
				}
				
			str.append("]\n");		
			}
	}

	
	public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
		double [][] distancematrix=trainingSet.distance();
		System.out.println("Distance matrix:\n");
		for(int i=0;i<distancematrix.length;i++) {
			for(int j=0;j<distancematrix.length;j++)
				System.out.print(distancematrix[i][j]+"\t");
			System.out.println("");
		}
		
		
	
	
	}
	
































}


