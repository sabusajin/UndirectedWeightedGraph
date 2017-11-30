package package1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UndirectedGraph {
	static int gbl_ctr = 0;
	static Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
	static ArrayList<Integer>[] array = new ArrayList[10];
	static float [][][] components = new float [10][][];
	static float[][] output = new float [20][];
	
	static int V = 0;
	
	static int ctr_prim = 0;

	static class AdjParam {
		int V;
		float weight;
		public AdjParam(int v, float weight) {
			super();
			V = v;
			this.weight = weight;
		}
	}

	static class Graph
	{
		LinkedList<AdjParam>[] adjListArray;

		// constructor 
		Graph(AdjParam adj)
		{

			adjListArray = new LinkedList[adj.V];

			// Create a new list for each vertex
			// such that adjacent nodes can be stored
			for(int i = 0; i < adj.V ; i++){
				adjListArray[i] = new LinkedList<AdjParam>();
			}
		}
		private static void connectedComponents (Graph g, int V) {
			Boolean[] visited = new Boolean[V];
			int ctr = 0;

			for(int v = 0; v < V; v++)
				visited[v] = false;
			for (int v=0; v<V; v++)
			{
				if (visited[v] == false)
				{
					// print all reachable vertices
					// from v
					DFSUtil(g, v, visited, ctr);
					ctr++;

					System.out.println();
				}
			}
			map.put(gbl_ctr, array[gbl_ctr]);
		}
		private static void DFSUtil(Graph g, int v, Boolean[] visited, int ctr) {
			// TODO Auto-generated method stub
			visited[v] = true;
			if (ctr!=gbl_ctr){
				gbl_ctr++;
				map.put(gbl_ctr-1, array[gbl_ctr-1]);
			}
			array[gbl_ctr].add(v+1);

			for(AdjParam s: g.adjListArray[v]){
				if(!visited[s.V]){
					DFSUtil(g, s.V, visited, ctr);
				}
			}

		}
	}
	static void addEdge(Graph graph, int src, int dest, float weight)
	{
		// Add an edge from src to dest. 
		graph.adjListArray[src].addFirst(new AdjParam(dest, weight));

		// Since graph is undirected, add an edge from dest
		// to src also
		graph.adjListArray[dest].addFirst(new AdjParam(src, weight));
	}
	// A utility function to print the adjacency list 
	// representation of graph
	static void printGraph(Graph graph, int V)
	{       
		for(int v = 0; v < V; v++)
		{
			System.out.println("Adjacency list of vertex "+ v);
			System.out.print("head");
			for(AdjParam pCrawl: graph.adjListArray[v]){
				System.out.print(" -> "+pCrawl.V+" "+pCrawl.weight);
			}
			System.out.println("\n");
		}
	}
	// A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    static int minKey(float key[], Boolean mstSet[], int size)
    {
        // Initialize min value
        float min = Float.MAX_VALUE;
        int min_index=-1;
 
        for (int v = 0; v < size; v++)
            if (mstSet[v] == false && key[v] < min)
            {
                min = key[v];
                min_index = v;
            }
 
        return min_index;
    }
	 // A utility function to print the constructed MST stored in
    // parent[]
    static void printMST(int parent[], int n, float graph[][])
    {
    	System.out.println("MST Number "+(ctr_prim+1));
    	
        System.out.println("Edge   Weight");
        
        	for (int j = 1; j < n; j++){
        		System.out.println(map.get(ctr_prim).get(parent[j])+" - "+ map.get(ctr_prim).get(j)+"    "+
                        graph[j][parent[j]]);
        		output[map.get(ctr_prim).get(parent[j])-1][map.get(ctr_prim).get(j)-1] = graph[j][parent[j]];
        		output[map.get(ctr_prim).get(j)-1][map.get(ctr_prim).get(parent[j])-1] = graph[j][parent[j]];
        		
        	}
        	
                
        	
        
        
    }
 
     // Function to construct and print MST for a graph represented
    //  using adjacency matrix representation
    static void primMST(float graph[][], int size)
    {
        // Array to store constructed MST
        int parent[] = new int[size];
 
        // Key values used to pick minimum weight edge in cut
        float key[] = new float [size];
 
        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[size];
 
        // Initialize all keys as INFINITE
        for (int i = 0; i < size; i++)
        {
            key[i] = Float.MAX_VALUE;
            mstSet[i] = false;
        }
 
        // Always include first 1st vertex in MST.
        key[0] = 0;     // Make key 0 so that this vertex is
                        // picked as first vertex
        parent[0] = -1; // First node is always root of MST
        
        
        	
        for (int count = 0; count < size-1; count++)
        {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet, size);
 
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
 
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < size; v++)
 
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v]!=0 && mstSet[v] == false &&
                    graph[u][v] <  key[v])
                {
                    parent[v]  = u;
                    key[v] = graph[u][v];
                }
        }
        
 
        // print the constructed MST
        printMST(parent, size, graph);
        ctr_prim++;
    }
	//static void (make)

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		File file = new File("input2.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));

		int vertices = Integer.parseInt(br.readLine());
		V= vertices;
		int edges = Integer.parseInt(br.readLine());
		for (int i = 0; i < array.length; i++) {
			array[i] = new ArrayList<>();
			//components[i] = new float[vertices][vertices];
		}
		float[][] adjacencyMat = new float[vertices][vertices];
		AdjParam adjparam = new AdjParam(vertices,0);
		Graph graph = new Graph(adjparam);
		for (int i=0; i<edges; i++) {
			String[] line = br.readLine().split(",");
			adjacencyMat [Integer.parseInt(line[0])-1][Integer.parseInt(line[1])-1] = Float.parseFloat(line[2]);	
			adjacencyMat [Integer.parseInt(line[1])-1][Integer.parseInt(line[0])-1] = Float.parseFloat(line[2]);
			addEdge(graph, Integer.parseInt(line[0])-1, Integer.parseInt(line[1])-1, Float.parseFloat(line[2]));

			//System.out.println(Integer.parseInt(line[0])+","+Integer.parseInt(line[1])+","+Float.parseFloat(line[2]));
		}
		br.close();
		System.out.println("Adjacency matrix is->");
		for (int i=0; i<vertices; i++) {
			for (int j=0; j<vertices; j++) {
				System.out.print(adjacencyMat[i][j]+ " ");

			}
			System.out.println();
		}
		//printGraph(graph, vertices);
		graph.connectedComponents(graph, vertices);
		for (int i=0;i<vertices;i++)
			output[i] = new float[V];
		if(gbl_ctr==0){
			System.out.println("Graph is connected and hence it has one connected component-");
		}
		else {
			System.out.println("Graph is not connected. There are "+(gbl_ctr+1)+" connected components -");
		}
		System.out.println("Connected components are ->");
		for (int i=0; i<=gbl_ctr;i++){
			for (int j=0; j<map.get(i).size();j++){
				System.out.print(map.get(i).get(j)+" ");
			}
			System.out.println();
		}
		
		
		/*Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
		
		
		for (int i=0; i<=gbl_ctr;i++){
			for (int j=0; j<map.get(i).size();j++){
				map2.put(j,map.get(i).get(j));
			}
			components[i] = new float[map2.size()][map2.size()];
			for (int m=0; m<map2.size();m++){
				for (int n=0; n<map2.size();n++){
					components[i][m][n] = 
				}
			}
			
		}*/
		for (int i=0; i<=gbl_ctr;i++){
			components[i] = new float[map.get(i).size()][map.get(i).size()];
			for (int j=0; j<map.get(i).size();j++){
				for (int k=0; k<map.get(i).size();k++){
					components[i][j][k] = adjacencyMat[(map.get(i).get(j))-1][(map.get(i).get(k))-1];
					components[i][j][k] = adjacencyMat[(map.get(i).get(k))-1][(map.get(i).get(j))-1];
					
				}
			}
			System.out.println("Minimum spanning trees of the connected components are->");
			primMST(components[i], map.get(i).size());
			
		}
		
		
		
		
		/*for (int i=0; i<=gbl_ctr;i++){
			for (int j=0; j<map.get(i).size();j++){
				for (int k=j+1; k<map.get(i).size();k++){
					components[i][(map.get(i).get(j))-1][map.get(i).get(k) -1] = adjacencyMat[(map.get(i).get(j))-1][(map.get(i).get(k))-1];
					components[i][(map.get(i).get(k))-1][map.get(i).get(j) -1] = adjacencyMat[(map.get(i).get(k))-1][(map.get(i).get(j))-1];
					
				}
			}
		}*/
		/*for (int i=0; i<vertices; i++) {
			for (int j=0; j<vertices; j++) {
				System.out.print(components[0][i][j]+ " ");

			}
			System.out.println();
		}*/
		//primMST(components);
		System.out.println("Forest is -");
		for (int i=0; i<vertices; i++) {
			for (int j=0; j<vertices; j++) {
				System.out.print(output[i][j]+ " ");

			}
			System.out.println();
		}
		



	}

}
