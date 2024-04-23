package lab1;

class Example{
	
	Double[] example;
	
	Example(int length){
		
		
		for (int i=0; i<length; i++) {
			
			example[i] = 0.0;
			
		}
		
	}
	
	void set(int index, Double v) {
		
		example[index] = v;
		
	}

	
	Double get(int index) {
		
		return example[index];
		
	}
	
	
	double distance(Example newE) throws Exception {
		
		if (newE.example.length != this.example.length) {
			
			throw new Exception("I due vettori hanno dimensione diversa!");
			
		}
		
		int dist = 0;
		
		for (int i=0; i<newE.example.length; i++) {
			
			dist += (this.get(i) - newE.get(i)) * (this.get(i) - newE.get(i)); 
			
		}
		
		return dist;
		
	}
	
	
	public String toString(Example newE) {
		
		StringBuilder ans = new StringBuilder();
		
		ans.append("[");

		for (int i=0; i<newE.example.length; i++) {
			
			ans.append(get(i));
			
			if (i != newE.example.length-1) {
				
				ans.append(", ");
				
			}
			
		}
		
		ans.append("]");
		
		return ans.toString();

	}
	
	
}