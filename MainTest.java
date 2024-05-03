package lab1;

import clustering.HierachicalClusterMiner;
import data.Data;
import distance.AverageLinkdistance;
import distance.ClusterDistance;
import distance.SingleLinkDistance;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


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
			System.out.println("");
		}
		clustering.mine(data, distance);
		System.out.println(clustering);
		System.out.println(clustering.toString(data));


		System.out.println("Average link distance");
		distance = new AverageLinkdistance();
		clustering.mine(data, distance);
		System.out.println(clustering);
		System.out.println(clustering.toString(data));


		System.out.println("Enter depth for clustering: ");

        int depth = Keyboard.readInt();

        HierachicalClusterMiner clustering2 = null;

        if (depth > 0 || depth < data.getNumberOfExamples()) {

            clustering2 = new HierachicalClusterMiner(depth);

        }

        System.out.println("Choose the distance: 1 for Single Link, 2 for Average Link");

        ClusterDistance distance1 = null;

        try {

            int choice = Keyboard.readInt();

            if (choice == 1) {
                distance1 = new SingleLinkDistance();
            } else if (choice == 2) {
                distance1 = new AverageLinkdistance();
            }

            clustering2.mine(data, distance1);

            System.out.println(clustering2);

            System.out.println(clustering2.toString(data));

        } catch (Exception e) {
				System.out.println("Invalid input for distance");
        }

	}

}
