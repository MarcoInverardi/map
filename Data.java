package data;

public class Data {
	Example data [];
	int numberOfExamples;
	
	public Data(){


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

	public int getNumberOfExamples() {
		return numberOfExamples;
	}


	public Example getExample(int ExampleIndex) {
		return data[ExampleIndex];
	}
	
	
	public double [][] distance(){
		
		int n= numberOfExamples;
		
		double [][] distances = new double [n][n];
		
		for (int i=0; i<n; i++) {
			
			for(int j=0; j<n; j++) {
				
				if(j<=i) {
					distances[i][j]=0.0;
				}
				else {
					distances[i][j]= data[i].distance(data[j]);
					
				}
						
			
			}

	    }

		return distances;

	}
	public String toString() {
		
			StringBuilder str = new StringBuilder();
			
			for (int i=0; i<numberOfExamples; i++) {
				
				str.append(i+": [");
				
				for (int j=0; j<data[i].example.length; j++) {
					
					str.append(data[i].example[j]);
					
					if (j != data[i].example.length-1) {
						
						str.append(", ");
					}
				}
				
			str.append("]\n");		
			}

			String str2 = str.toString();

			return str2;

	}

}


