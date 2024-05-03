package clustering;

import data.Data;

public class Dendrogram {

    private ClusterSet tree[];


    Dendrogram(int depth){  //inizializza tree con dimensione depth

        tree = new ClusterSet[depth];

        for (int i=0; i<depth; i++){

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
