package server.distance;


import server.clustering.Cluster;
import server.data.Data;
import server.data.Example;
import server.default_package.InvalidSizeException;
import java.util.Iterator;

/**
 * Classe che implementa la distanza media tra due cluster
 */
public class AverageLinkdistance implements ClusterDistance {
    /**
     * metodo che calcola la distanza media tra due cluster
     * @param c1 cluster 1
     * @param c2 cluster 2
     * @param d Data
     * @return la distanza media tra i due cluster
     * @throws InvalidSizeException
     */
    public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException{

        double SumOfDistances=0.0;
        int CountOfCouples=0;
        Iterator<Integer> it1 = c1.iterator();
        Iterator<Integer> it2 = c2.iterator();
        Example e1;
        while (it1.hasNext())
        {
            e1=d.getExample(it1.next());

            while (it2.hasNext())
            {

                double distance=e1.distance(d.getExample((it2.next())));
                SumOfDistances += distance;
                CountOfCouples ++;

            }
        }

        return SumOfDistances/CountOfCouples;

    }


}
