package distance.distance;

import distance.clustering.Cluster;
import distance.data.Data;

public interface ClusterDistance {
		double distance(Cluster c1, Cluster c2, Data d);
}
