import java.util.*;

/**
 * This class represents a Graph, consisting of several Vertex objects
 * connected by Edges.
 *
 * @author Lucas
 */
public class Graph
{
    // Random object to be used globally
    private static Random rand = new Random();

    // Instance variable declaration
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    /**
     * Constructs a new empty Graph
     */
    public Graph()
    {
        this.vertices = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
    }

    /**
     * Adds the given Vertex to the graph if it does not already exist
     *
     * @param vertex The Vertex to be added
     */
    public void addVertex(Vertex vertex)
    {
        if (!vertices.contains(vertex))
        {
            vertices.add(vertex);
        }
    }

    /**
     * Adds the given Edge to this graph, as well as the two Vertexs that it
     * connects.
     *
     * @param edge The Edge to be added
     */
    public void addEdge(Edge edge)
    {
        edges.add(edge);
        this.addVertex(edge.getVertex1());
        this.addVertex(edge.getVertex2());
    }

    /**
     * Returns the list of Vertexs that make up this Graph.
     *
     * @return The Vertexs in this Graph
     */
    public ArrayList<Vertex> getVertices()
    {
        return this.vertices;
    }

    /**
     * Returns the list of Edges that make up this Graph.
     *
     * @return The Edges in this Graph
     */
    public ArrayList<Edge> getEdges()
    {
        return this.edges;
    }

    /**
     * Prints out the shortest path between two Vertexs in this Graph using
     * Dijkstra's algorithm
     */
    public void shortestPath(Vertex start, Vertex end)
    {
        // Keeps track of distance between each vertex and the source
        int[] distances = new int[vertices.size()];
        for (int i = 0 ; i < distances.length ; i++)
            distances[i] = 100000;

        // Keeps track of the vertex preceeding each vertex en route to source
        int[] previous = new int[vertices.size()];
        for (int i = 0 ; i < previous.length ; i++)
            previous[i] = -1;

        // Distance from source to itself is 0
        distances[start.getIndex()] = 0;

        // Initialize the queue of vertices 
        ArrayList<Vertex> verts = new ArrayList<Vertex>();
        for (Vertex v : vertices)
            verts.add(v);

        // Keeps track of vertices that have already been used
        ArrayList<Integer> used = new ArrayList<Integer>();

        // Go while there are still unchecked vertices
        while(!verts.isEmpty())
        {
            // Find the next closest vertex in the graph
            int minIndex = 0;
            int min = 100000;
            for (int i = 0 ; i < distances.length ; i++)
            {
                // If a new minimum is found, use it
                if (distances[i] < min &&  !used.contains(i))
                {
                    minIndex = i;
                    min = distances[i];
                }
            }

            // Find the Vertex object that corresponds to the new minimum
            Vertex current = null;
            for (Vertex v : verts)
            {
                if (v.getIndex() == minIndex)
                {
                    current = v;
                    break;
                }
            }

            //System.out.println("current = " + current);
            //for (int i : distances) System.out.print(i + " "); System.out.println();

            // Adjust the distances for each of the vertex that is adjacent
            // to the newly found minimum-distance vertex
            for (Vertex relation : current.getRelations())
            {
                // Store the index of the relation for convenience
                int index = relation.getIndex();

                // Find the edge which connects the adjacent vertex
                // to the current vertex
                Edge edge = null;
                for (Edge e : current.getEdges())
                {
                    if (e.getVertex1().equals(relation) || e.getVertex2().equals(relation))
                    {
                        edge = e;
                        break;
                    }
                }

                int dist = distances[current.getIndex()] + edge.getWeight();
                //System.out.println("\tdist of relation " + relation + " is " + dist);
                // Update the distances
                if (dist < distances[index])
                {
                    distances[index] = dist;
                    previous[index] = current.getIndex();
                }
            }

            // Mark the current minimum-distance vertex as used, and remove
            // it from our list of vertices
            used.add(current.getIndex());
            verts.remove(current);
        }

        // After calculating the distance array, find the shortest path
        Stack<Vertex> stack = new Stack<Vertex>();
        Vertex target = end;
        while (previous[target.getIndex()] >= 0)
        {
            stack.push(target);
            for (Vertex v : vertices)
            {
                if (v.getIndex() == previous[target.getIndex()])
                {
                    target = v;
                    continue;
                }
            }
        }
        stack.push(start);

        // Print out the shortest path
        while(!stack.isEmpty())
        {
            System.out.print(stack.pop().getIndex() + " ");
        }

        // Print out the distance of the shortest path
        System.out.println(" with total length " + distances[end.getIndex()]);
    }

    /**
     * Calculates and returns the minimum spanning tree for the current
     * Graph using Kruskal's algorithm
     */
    public Graph mst()
    {
        // The Graph that will be populates with the minimum spanning tree
        Graph minTree = new Graph();

        // Variables used for managing the parent partition
        int[] parent = initPartition(vertices.size());
        int edgeCount = 0;
        int includedCount = 0;

        // Sort the edges by weight
        Collections.sort(edges);

        // Continue while there are still edges and we haven't yet used N-1 of them
        while (edgeCount < edges.size() && includedCount <= vertices.size() - 1)
        {
            // Determine the number of edges that all share 
            // the current minimum weight
            int sameCount = 0;
            while (edgeCount+sameCount < edges.size() && 
                        edges.get(edgeCount+sameCount).getWeight() == 
                        edges.get(edgeCount).getWeight())
            {
                sameCount++;
            }

            // Randomly calculate the index of the edge with the minimum weight
            // that will be used
            int offset = rand.nextInt(sameCount);
            int edgeIndex = edgeCount + offset; 

            // Find the parents in the partition of the two vertices connected
            // by the current edge
            int parent1 = findRoot(edges.get(edgeIndex).getVertex1().getIndex(), parent);
            int parent2 = findRoot(edges.get(edgeIndex).getVertex2().getIndex(), parent);

            // If the two vertices are not currently part of the same tree,
            // add the edge to the minimum spanning tree and join the two vertices
            if (parent1 != parent2)
            {
                minTree.addEdge(edges.get(edgeIndex));
                includedCount++;
                parent = union(parent1, parent2, parent);
            }

            // Swap the one you are using to the front of the section with the same weight
            Collections.swap(edges, edgeCount, edgeIndex);

            // Increment the number of edges we have used
            edgeCount++;
        }

        // Return the minimum spanning tree
        return minTree;
    }

    /**
     * Helper method for the Kruskal's algorithm to initialize the partition
     * keeping track of parents in the tree.
     *
     * @param n The number of nodes in the tree
     */
    public int[] initPartition(int n)
    {
        // Create the partition
        int[] parent = new int[n];

        // Initialize all values to -1
        for (int i = 0 ; i < parent.length ; i++)
        {
            parent[i] = -1;
        }

        // Return the initialized partition
        return parent;
    }

    /**
     * Helper method for Kruskal's algorithm that joins to trees in the 
     * partition.
     *
     * @param i The index of the first tree to join
     * @param j The index of the second tree to join
     * @param parent The partition
     *
     * @return The partition after the union is done
     */
    public static int[] union(int i, int j, int[] parent)
    {
        // Calculate the total number of elements in the two trees
        int totalElements = parent[i] + parent[j];

        // Join the two together
        if (parent[i] >= parent[j])
        {
            parent[i] = j;
            parent[j] = totalElements;
        }
        else
        {
            parent[j] = i;
            parent[i] = totalElements;
        }

        // Return the new, joined partition
        return parent;
    }

    /**
     * Helper method for Kruskal's algorithm that finds the root of the
     * node with the given index in the parent partition.
     *
     * @param index The index of the node
     * @param parent The partition
     * @return The index of the root node
     */
    public static int findRoot(int index, int[] parent)
    {
        // Progress through each parent until a root is found
        int result = index;
        while(parent[result] >= 0)
        {
            result = parent[result];
        }

        // The index of the root node
        return result;
    }

    /**
     * A utility method that generates a complete Graph of n vertices
     * whose edges have weight ranging between 1 and the maximum weight
     * given as a parameter.
     *
     * @param n The number of vertices in the graph
     * @param maxWeight The maximum possible weight for any edge
     * @return The newly created Graph
     */
    public static Graph createRandomGraph(int n, int maxWeight)
    {
        Graph graph = new Graph();

        // Initialize all of the vertices
        Vertex[] v = new Vertex[n];
        for (int i = 0 ; i < v.length ; i++)
        {
            v[i] = new Vertex(i, (char)(i+33));
        }

        // Add edges between all of the vertices
        for (int i = 0 ; i < v.length-1 ; i++)
        {
            for (int j = i+1 ; j < v.length ; j++)
            {
                if (i != j)
                {
                    // Generate a random weight
                    int weight = rand.nextInt(maxWeight) + 1;

                    // Add the new Edge
                    graph.addEdge(new Edge(v[i], v[j], weight));
                }
            }
        }

        // Return the Graph
        return graph;
    }

    /**
     * Creates an easily readable String representation of the current Graph.
     *
     * @return The String representation of this Graph.
     */
    @Override
    public String toString()
    {
        String str = "";
        for (Edge e : edges)
        {
            str += Character.toString(e.getVertex1().getLabel()) + " --- " + 
                   e.getWeight() + " --- " + 
                   Character.toString(e.getVertex2().getLabel()) + "\n";
        }

        return str;
    }
}
