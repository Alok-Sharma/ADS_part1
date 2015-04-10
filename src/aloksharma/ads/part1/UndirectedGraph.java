package aloksharma.ads.part1;

import java.io.BufferedReader;
import java.io.FileReader;

public class UndirectedGraph {
	static Dijkstra dijkstra;
	
	public static void main(String[] args) throws Exception {
		int randomSource = 0;
		int randomDest = 4;
		dijkstra = new Dijkstra(randomSource, randomDest);
		readFromFile();
	}
	

	private static void readFromFile() throws Exception{
		FileReader input_file = new FileReader("sample_input_part1.txt");
		BufferedReader reader = new BufferedReader(input_file);
		
		String line1 = reader.readLine();
		int nodes = line1.charAt(0);
		int edges = line1.charAt(2);
		
		while(reader.readLine() != null){
			String line = reader.readLine();
			int node1 = Character.getNumericValue(line.charAt(0));
			int node2 = Character.getNumericValue(line.charAt(2));
			double weight = Character.getNumericValue(line.charAt(4));
			makeEdge(node1, node2, weight);
		}
		dijkstra.printGraph();
		dijkstra.findShortestPath();
		dijkstra.printPath();
		reader.close();
		input_file.close();
	}
	
	private static void makeEdge(int node1, int node2, double weight){
//		System.out.println("node1: " + node1 + " node2: " + node2 + " weight: " + weight);
		dijkstra.insertEdge(node1, node2, weight);
	}
}
