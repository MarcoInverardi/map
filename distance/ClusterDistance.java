package distance.distance;

import distance.clustering.Cluster;
import distance.data.Data;
import distance.default_package.InvalidSizeException;


public interface ClusterDistance {
		double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException;

}
