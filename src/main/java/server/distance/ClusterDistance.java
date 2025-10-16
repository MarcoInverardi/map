package server.distance;

import server.clustering.Cluster;
import server.data.Data;
import server.default_package.InvalidSizeException;


public interface ClusterDistance {
		double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException, InvalidSizeException;

}
