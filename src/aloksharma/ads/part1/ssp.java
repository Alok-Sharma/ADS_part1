package aloksharma.ads.part1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ssp {
	static Dijkstra dijkstra;
	
	public static void main(String[] args) throws Exception {
		
//		String inputFileName = "input_5000_1_part1.txt";
		String inputFileName = "sample_input_part1.txt";
//		String inputFileName = "input_1000_50_part1.txt";
//		String inputFileName = "input_1000000.txt";
		
		int source = 2;
//		int dest = 999999;
		int dest = 0;
		
//		String inputFileName = args[0];
//		int source = Integer.parseInt(args[1]);
//		int dest = Integer.parseInt(args[2]);
		
		dijkstra = new Dijkstra();
		readInputFromFile(inputFileName);
//		dijkstra.printGraph();
		dijkstra.findShortestPath(source, dest);
//		dijkstra.printPath();
		writeOutputToFile(dijkstra.getDestNode());
	}
	

	private static void readInputFromFile(String inputFileName) throws Exception{
		FileReader input_file = new FileReader(inputFileName);
		BufferedReader reader = new BufferedReader(input_file);
		
		String line1 = reader.readLine();
		int nodes = line1.charAt(0);
		int edges = line1.charAt(2);
		String line;
		while((line = reader.readLine()) != null){
//			if(line == null){
//				break;
//			}
			line = reader.readLine();
			String[] lineSplit = line.split(" ");
			
			int node1 = Integer.parseInt(lineSplit[0]);
			int node2 = Integer.parseInt(lineSplit[1]);
			double weight = Double.parseDouble(lineSplit[2]);
			dijkstra.insertEdge(node1, node2, weight);
		}
		reader.close();
		input_file.close();
	}
	
	private static void writeOutputToFile(DijkstraNode dest) throws Exception{
		String distance = (int)dest.getSourceDistance() + "";
		String path = dest.getNodeId() + "";
		
		DijkstraNode parent = dest.parentNode;
		while(parent != null){
			path = parent.getNodeId() + " " + path;
			parent = parent.parentNode;
		}
		
		System.out.println(distance);
		System.out.println(path);
	}
}
