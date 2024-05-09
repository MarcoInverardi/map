package distance.clustering;

import distance.data.Data;
import distance.default_package.InvalidDepthException;

public class Dendrogram {

    private final ClusterSet[] tree;


    Dendrogram(int depth) throws InvalidDepthException{ //inizializza tree con dimensione depth

        if (depth < 0 || depth > Data.getNumberOfExamples()) {
            throw new InvalidDepthException("Depth cannot be greater than number of examples");
        }

        tree = new ClusterSet[depth];
        for (int i = 0; i < depth; i++) {

            tree[i] = new ClusterSet(depth);

        }
    }

    void setClusterSet(ClusterSet c, int level){

        tree[level] = c;  //assegna c a tree[level]

    }


    int getDepth(){ //restituisce la profondità di tree

        return tree.length;

    }

    public String toString() {

        String v="";

        for (int i=0;i<tree.length;i++)

            v+=("level"+i+":\n"+tree[i]+"\n");

        return v;

    }


    String toString(Data data) {

        String v="";

        for (int i=0;i<tree.length;i++)

            v+=("level"+i+":\n"+tree[i].toString(data)+"\n");

        return v;

    }


}
