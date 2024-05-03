package data;

public class Example{
	
	Double[] example;
	
	Example(int length){

		example = new Double[length];

		for (int i=0; i<length; i++) {
			
			example[i] = 0.0;
			
		}
		
	}
	
	void set(int index, Double v) {
		
		example[index] = v;
		
	}

	
	public Double get(int index) {
		
		return example[index];
		
	}
	
	
	public double distance(Example newE) {
		
		int dist = 0;
		
		for (int i=0; i<newE.example.length; i++) {
			
			dist += (this.get(i) - newE.get(i)) * (this.get(i) - newE.get(i)); 
			
		}
		
		return dist;
		
	}

	public String toString(){

		StringBuilder ans = new StringBuilder();

		ans.append("[");

		for (int i=0; i<example.length; i++) {

			ans.append(example[i]);

			if (i != example.length-1) {

				ans.append(", ");

			}

		}

		ans.append("]");

		return ans.toString();
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