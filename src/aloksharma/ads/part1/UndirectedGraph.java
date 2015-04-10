package aloksharma.ads.part1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UndirectedGraph {
	static Dijkstra dijkstra;
	
	public static void main(String[] args) throws Exception {
		
//		String inputFileName = "sample_input_part1.txt";
//		int source = 0;
//		int dest = 4;
		
		String inputFileName = args[0];
		int source = Integer.parseInt(args[1]);
		int dest = Integer.parseInt(args[2]);
		
		dijkstra = new Dijkstra(source, dest);
		readInputFromFile(inputFileName);
//		dijkstra.printGraph();
		dijkstra.findShortestPath();
//		dijkstra.printPath();
		writeOutputToFile(dijkstra.getDestNode());
	}
	

	private static void readInputFromFile(String inputFileName) throws Exception{
		FileReader input_file = new FileReader(inputFileName);
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
		reader.close();
		input_file.close();
	}
	
	private static void makeEdge(int node1, int node2, double weight){
//		System.out.println("node1: " + node1 + " node2: " + node2 + " weight: " + weight);
		dijkstra.insertEdge(node1, node2, weight);
	}
	
	private static void writeOutputToFile(DijkstraNode dest) throws Exception{
		System.out.println("Output to file:");
		String distance = (int)dest.getSourceDistance() + "";
		String path = dest.getNodeId() + "";
		
		DijkstraNode parent = dest.parentNode;
		while(parent != null){
			path = parent.getNodeId() + " " + path;
			parent = parent.parentNode;
		}
		
		System.out.println(distance);
		System.out.println(path);

		FileWriter output_file = new FileWriter(new File("output.txt"));
		output_file.write(distance);
		output_file.write(System.lineSeparator());
		output_file.write(path);
		output_file.close();
	}
}
