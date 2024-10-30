import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Graph {
    private int vertices;
    private int[][] adjacencyMatrix;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyMatrix = new int[vertices][vertices];
    }

    public void addEdge(int source, int destination, int weight) {
        adjacencyMatrix[source][destination] = weight;
        adjacencyMatrix[destination][source] = weight; // For undirected graph
    }

    public void dijkstra(int startVertex) {
        int[] distances = new int[vertices]; // shortest distances from source
        boolean[] visited = new boolean[vertices]; // to track visited vertices
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(vertices, (a, b) -> Integer.compare(a.cost, b.cost));
        priorityQueue.add(new Node(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            int currentVertex = node.vertex;

            if (!visited[currentVertex]) {
                visited[currentVertex] = true;

                for (int i = 0; i < vertices; i++) {
                    if (adjacencyMatrix[currentVertex][i] > 0 && !visited[i]) {
                        int newDist = distances[currentVertex] + adjacencyMatrix[currentVertex][i];
                        if (newDist < distances[i]) {
                            distances[i] = newDist;
                            priorityQueue.add(new Node(i, distances[i]));
                        }
                    }
                }
            }
        }

        printDijkstra(distances, startVertex);
    }

    private void printDijkstra(int[] distances, int startVertex) {
        System.out.println("Dijkstra's Algorithm: (Shortest distances from source vertex " + startVertex + ")");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("Vertex " + i + " : Distance " + distances[i]);
        }
    }

    static class Node {
        int vertex;
        int cost;

        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int vertices = scanner.nextInt();

        Graph graph = new Graph(vertices);

        System.out.print("Enter the number of edges: ");
        int edges = scanner.nextInt();

        System.out.println("Enter the edges (source, destination, weight): ");
        for (int i = 0; i < edges; i++) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(source, destination, weight);
        }

        System.out.print("Enter the source vertex: ");
        int startVertex = scanner.nextInt();

        graph.dijkstra(startVertex);
        scanner.close();
    }
}
