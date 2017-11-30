package package1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class UndirectedGraph {
	static int gbl_ctr = 0;
	static int k = 0;
	static Map<Integer, int[]> map = new HashMap<Integer, int[]>();
	static int [][] list  = new int[3][10];
	
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
        //AdjParam adj;
        LinkedList<AdjParam>[] adjListArray;
         
        // constructor 
        Graph(AdjParam adj)
        {
        	//this.adj.V = adj.V;
        	//this.adj.weight = adj.weight;
        	//System.out.println(adj.V);
                        
            // define the size of array as 
            // number of vertices
        	
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
            }System.out.println(gbl_ctr);
        	map.put(gbl_ctr, list[gbl_ctr]);
        }
		private static void DFSUtil(Graph g, int v, Boolean[] visited, int ctr) {
			// TODO Auto-generated method stub
			visited[v] = true;
			if (ctr!=gbl_ctr){
				gbl_ctr++;
				System.out.println((ctr+1)+" connected component detected!!");
				map.put(gbl_ctr-1, list[gbl_ctr-1]);
				k=0;
				
								
			}
			
			list[gbl_ctr][k] = (v+1);
			k++;
		    System.out.print((v+1)+" ");
		    
		    for(AdjParam s: g.adjListArray[v]){
		    	if(!visited[s.V]){
		    		DFSUtil(g, s.V, visited, ctr);
		    		//System.out.print(s.V);
		    	}
		    	
		    }
		    
		    //System.out.println();
		    /*for(AdjParam pCrawl: g.adjListArray[v]){
		    	DFSUtil(g, pCrawl.V, visited);
		    	System.out.println();
		    }*/
		    
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

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		File file = new File("input2.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
	 
		int vertices = Integer.parseInt(br.readLine());
		int edges = Integer.parseInt(br.readLine());
		float[][] adjacencyMat = new float[vertices][vertices];
		//System.out.println(vertices+"\n"+edges);
		AdjParam adjparam = new AdjParam(vertices,0);
		//System.out.println(adjparam.V);
		Graph graph = new Graph(adjparam);
		for (int i=0; i<edges; i++) {
			String[] line = br.readLine().split(",");
			adjacencyMat [Integer.parseInt(line[0])-1][Integer.parseInt(line[1])-1] = Float.parseFloat(line[2]);	
			adjacencyMat [Integer.parseInt(line[1])-1][Integer.parseInt(line[0])-1] = Float.parseFloat(line[2]);
			addEdge(graph, Integer.parseInt(line[0])-1, Integer.parseInt(line[1])-1, Float.parseFloat(line[2]));
			
			//System.out.println(Integer.parseInt(line[0])+","+Integer.parseInt(line[1])+","+Float.parseFloat(line[2]));
		}
		br.close();
		for (int i=0; i<vertices; i++) {
			for (int j=0; j<vertices; j++) {
				System.out.print(adjacencyMat[i][j]+ " ");
				
			}
			System.out.println();
		}
		printGraph(graph, vertices);
		System.out.println("The following are the connected components");
		graph.connectedComponents(graph, vertices);
		
		/*System.out.println("Testing for new hashmap function");
		for (int i=0; i<=gbl_ctr;i++){
			for (int j=0; j<10;j++){
			System.out.print(map.get(i)[j]+" ");
			}
			System.out.println();
		}*/
			
		

	}

}
