package distance.distance;


import distance.clustering.Cluster;
import distance.data.Data;
import distance.data.Example;
import distance.default_package.InvalidSizeException;

public class AverageLinkdistance implements ClusterDistance {

    public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException{

        double SumOfDistances=0.0;
        int CountOfCouples=0;


        for (int i=0;i< c1.getSize();i++)
        {
            Example e1=d.getExample(c1.getElement(i));

            for(int j=0; j<c2.getSize();j++) {

                double distance=e1.distance(d.getExample(c2.getElement(j)));

                SumOfDistances += distance;
                CountOfCouples ++;

            }
        }

        return SumOfDistances/CountOfCouples;

    }


}
