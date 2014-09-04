import java.util.*;

/**
 * This class represents an vertex on a graph. Each vertex keeps track of the 
 * edges that connect to it, and the other vertices that are adjacent to it.
 * Additionally, each Vertex has both an index and a label. The index is 
 * used programmatically for uniquely identifying each Vertex, whereas the 
 * label is simply what is displayed when displaying this Vertex.
 *
 * @author Lucas Kushner
 */
public class Vertex
{
    // Instance variable declaration
    private int index;
    private char label;
    private ArrayList<Edge> edges;
    private ArrayList<Vertex> relations;

    /**
     * Constructs a vertex with the given index and label.
     *
     * @param index The index of the vertex.
     * @param Label The label of the vertex.
     */
    public Vertex(int index, char label)
    {
        this.index = index;
        this.label = label;
        this.edges = new ArrayList<Edge>();
        this.relations = new ArrayList<Vertex>();
    }

    /**
     * Adds the given edge to this Vertex's list of edges. Also adds the 
     * connected Vertex to the list of adjacent vertices.
     *
     * @param edge The Edge to add to this Vertex.
     */
    public void addEdge(Edge edge)
    {
        this.edges.add(edge);
        this.relations.add(edge.getVertex1().equals(this) ? 
                            edge.getVertex2() : edge.getVertex1());
    }

    /**
     * Returns the index of the Vertex.
     *
     * @return The index of the current Vertex
     */
    public int getIndex()
    {
        return this.index;
    }

    /**
     * Returns the label of the Vertex.
     *
     * @return The label of the current Vertex
     */
    public char getLabel()
    {
        return this.label;
    }

    /**
     * Returns the list of Edges connecting to this Vertex.
     *
     * @return The list of Edges of the current Vertex
     */
    public ArrayList<Edge> getEdges()
    {
        return this.edges;
    }

    /**
     * Returns the list of Vertexs adjacent to this Vertex.
     *
     * @return The list of Vertexs of the current Vertex
     */
    public ArrayList<Vertex> getRelations()
    {
        return this.relations;
    }

    /**
     * Determines whether or not the current Vertex is equal to the given one.
     * Two vertices are considered equal if they have the same index. If the
     * given parameter is not a Vertex object, this will return false.
     *
     * @param obj The Vertex to compare to.
     * @return Whether or not the vertices are equal
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vertex)
            return this.index == ((Vertex)obj).getIndex();
        return false;
    }

    /**
     * Creates an easily readable String representation of the current Vertex.
     *
     * @return The String representation of this Vertex.
     */
    @Override
    public String toString()
    {
        return Character.toString(this.label);
    }
}
