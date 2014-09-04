import java.util.*;

/**
 * This class represents an edge on a graph. Each edge keeps track of the two
 * vertices that it connects, and its weight.
 *
 * @author Lucas Kushner
 */
public class Edge implements Comparable<Edge>
{
    // Instance variable declaration
    private Vertex vertex1;
    private Vertex vertex2;
    private int weight;

    /**
     * Constructs an edge connecting the two given vertices with the given
     * weight. Note that the Edge also adds itself to each vertices list
     * of edges.
     *
     * @param v1 One of the vertices the edge connects.
     * @param v2 One of the vertices the edge connects.
     * @param weight The weight of the edge.
     */
    public Edge(Vertex v1, Vertex v2, int weight)
    {
        this.vertex1 = v1;
        this.vertex2 = v2;
        this.weight = weight;

        v1.addEdge(this);
        v2.addEdge(this);
    }

    /**
     * Returns the weight of the Edge
     *
     * @return The weight of the current Edge
     */
    public int getWeight()
    {
        return this.weight;
    }

    /**
     * Returns the first Vertex this Edge is connected to.
     *
     * @return The Vertex
     */
    public Vertex getVertex1()
    {
        return this.vertex1;
    }

    /**
     * Returns the second Vertex this Edge is connected to.
     *
     * @return The Vertex
     */
    public Vertex getVertex2()
    {
        return this.vertex2;
    }

    /**
     * Compares the current Edge to the given one. The edges are compared
     * based on their weight, with an edge of lower weight being less than
     * the other edge, and vice versa.
     *
     * @param e The Edge to compare to.
     * @return Postive if the current Edge is greater, negative if less than
     *         and 0 if they are equal.
     */
    @Override
    public int compareTo(Edge e)
    {
        return this.weight - e.getWeight();
    }

    /**
     * Creates an easily readable String representation of the current Edge.
     *
     * @return The String representation of this Edge.
     */
    @Override
    public String toString()
    {
        return Integer.toString(this.weight) + 
               this.vertex1.toString() + 
               this.vertex2.toString();
    }
}
