import java.util.*;

public class Driver
{
    public static void main(String[] args)
    {
        int[] weights = new int[]{10, 25, 50, 100};
        Random rand = new Random();

        // Data production for Kruskal's Algorithm
        for (int weight : weights)
        {
            System.out.println("\nFor weight " + weight);
            Graph graph = Graph.createRandomGraph(50, weight);

            for (int j = 0 ; j < 10 ; j++)
            {
                Graph mst = graph.mst();
                for (Vertex v : mst.getVertices())
                {
                    System.out.print(v.getIndex() + " ");
                }

                int totalWeight = 0;
                for (Edge e : mst.getEdges())
                {
                    totalWeight += e.getWeight();
                }
                System.out.print(" --- " + totalWeight);
                System.out.println();
            }
        }

        // Data production for Dijkstra's Algorithm
        for (int weight : weights)
        {
            System.out.println("\nFor weight " + weight);
            Graph graph = Graph.createRandomGraph(50, weight);

            Vertex start = graph.getVertices().get(rand.nextInt(50));
            Vertex end = graph.getVertices().get(rand.nextInt(50));

            graph.shortestPath(start, end);
            graph.shortestPath(end, start);
        }
    }
}
