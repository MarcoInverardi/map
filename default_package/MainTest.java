package distance.default_package;

import distance.clustering.HierachicalClusterMiner;
import distance.data.Data;
import distance.distance.AverageLinkdistance;
import distance.distance.ClusterDistance;
import distance.distance.SingleLinkDistance;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InvalidDepthException, InvalidSizeException, InvalidChooseException {
		boolean flag = true;
		do{
			flag = false;
		try {

			Data data = new Data();
			System.out.println(data);
			int k = 5;
			HierachicalClusterMiner clustering = new HierachicalClusterMiner(k);

			System.out.println("Single link distance");
			ClusterDistance distance = new SingleLinkDistance();

			double[][] distancematrix = data.distance();
			System.out.println("Distance matrix:\n");
			for (int i = 0; i < distancematrix.length; i++) {
				for (int j = 0; j < distancematrix.length; j++)
					System.out.print(distancematrix[i][j] + "\t");
				System.out.println();
			}
      
			/*clustering.mine(data, distance);
			System.out.println(clustering);
			System.out.println(clustering.toString(data));*/


			System.out.println("Enter depth for clustering: ");
				try {
					int depth = Keyboard.readInt();

					HierachicalClusterMiner clustering2 = null;

					clustering2 = new HierachicalClusterMiner(depth);

					System.out.println("Choose the distance: 1 for Single Link, 2 for Average Link");

					try {

						ClusterDistance distance1 = null;

						int choice = Keyboard.readInt();

						if (choice == 1) {
							distance1 = new SingleLinkDistance();
						} else if (choice == 2) {
							distance1 = new AverageLinkdistance();
						} else {
							throw new InvalidChooseException("Invalid input for choosing distance");
						}

						clustering2.mine(data, distance1);

						System.out.println(clustering2);

						System.out.println(clustering2.toString(data));

					} catch (InvalidChooseException e) {

						System.out.println("Invalid input for choosing distance");
						flag = true;
					}

				} catch (InvalidDepthException e) {

					System.out.println("Depth cannot be greater than number of examples");
					flag = true;
				}

			} catch(InvalidSizeException e){

				System.out.println("Invalid size of data");
				flag = true;
			}

		boolean flag2 = true;

			do {

				if (flag == true) {

					System.out.println("Do you want to try again? (y/n)");
					String choice = Keyboard.readString();
					if (choice.equals("n") || choice.equals("N")){
						flag = false;
						flag2 = false;
					} else if (choice.equals("y")|| choice.equals("Y")) {
						flag = true;
						flag2 = false;
					}
					if (flag2 == true) {
						System.out.println("Invalid input. Try again.");
					}

				}

			} while (flag2 == true);

		} while (flag == true);


	}

}
