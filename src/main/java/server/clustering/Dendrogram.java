package server.clustering;

import server.data.Data;
import server.default_package.InvalidDepthException;
import java.io.Serializable;

/**
 * Classe che implementa il dendrogramma.
 */
public class Dendrogram implements Serializable{

    private final ClusterSet[] tree;

    /**
     * costruttore di default del dendrogramma
     * @param depth (profondità del dendrogramma)
     * @throws InvalidDepthException
     */
    Dendrogram(int depth) throws InvalidDepthException{ //inizializza tree con dimensione depth

        if (depth < 0 || depth > Data.getNumberOfExamples()) {
            throw new InvalidDepthException("Depth cannot be greater than number of examples");
        }

        tree = new ClusterSet[depth];
        for (int i = 0; i < depth; i++) {

            tree[i] = new ClusterSet(depth);

        }
    }

    /**
     * metodo che imposta il clusterset c al livello level di tree
     * @param c ClusterSet
     * @param level
     */
    void setClusterSet(ClusterSet c, int level){

        tree[level] = c;  //assegna c a tree[level]

    }

    /**
     * metodo che restituisce il clusterset al livello level di tree
     * @return l'altezza dell'albero tree
     */
    public int getDepth(){ //restituisce la profondità di tree

        return tree.length;

    }

    /**
     * metodo che restituisce il clusterset sotto forma di stringa
     * @return v (stringa rappresentante il clusterset)
     */
    public String toString() {

        String v="";

        for (int i=0;i<tree.length;i++)

            v+=("level"+i+":\n"+tree[i]+"\n");

        return v;

    }

    /**
     * metodo che restituisce il clusterset sottoforma di stringa comprensivo di data
     * @param data data.
     * @return v (stringa rappresentante il clusterset)
     */
    String toString(Data data) {

        String v="";

        for (int i=0;i<tree.length;i++)

            v+=("level"+i+":\n"+tree[i].toString(data)+"\n");

        return v;

    }

}
