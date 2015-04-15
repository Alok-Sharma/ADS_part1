package aloksharma.ads.part2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import aloksharma.ads.part1.Dijkstra;
import aloksharma.ads.part1.DijkstraNode;


public class Part2 {

	static Dijkstra dijkstra;
	static ArrayList<Router> routerList = new ArrayList<>();
	static ArrayList<Integer> shortestPath = new ArrayList<>();
	
	public static void main(String[] args) {
		
		
		String inputGraphFileName = "input_graphsmall_part2.txt";
		String inputIPFilename = "input_ipsmall_part2.txt";
		
		int source = 0;
		int dest = 3;
		dijkstra = new Dijkstra();
		
		try {
			readInputGraphFromFile(inputGraphFileName);
			readInputIPFromFile(inputIPFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dijkstra.findShortestPath(source, dest);
		getShortestPath(dijkstra.getDestNode());
		
		executeRouting();
		
		
//		BinaryTrie trie = new BinaryTrie();
//		trie.insert("000", "alok");
//		trie.insert("010", "alok");
//		trie.insert("011", "alok");
//		trie.insert("10", "nikita is lame");
//		trie.insert("11", "nikita is so lame haha");
//		
//		trie.printTrie();
//		trie.merge(trie.rootNode);
//		System.out.println("after merge=====");
//		trie.printTrie();
//		
//		trie.insert(convertIPToBinary("192.168.1.1"), "182.168.1.1");
//		trie.insert(convertIPToBinary("192.122.1.1"), "182.168.1.1");
//		trie.insert(convertIPToBinary("191.2.2.2"), "192.1.1.2");
//		trie.insert(convertIPToBinary("191.3.2.2"), "193.1.1.2");
//		trie.printTrie();
//		TrieNode result = trie.search(convertIPToBinary("191.3.2.1"));
//		String longestPrefix = result.getLongestPrefix(convertIPToBinary("191.3.2.1"));
//		System.out.println(result.nextHopIP);
//		System.out.println(longestPrefix);
//		System.out.println(trie.convertIPToBinary("192.122.1.1"));
//		System.out.println(trie.convertIPToBinary("191.2.2.2"));
//		System.out.println(convertIPToBinary("191.3.2.2"));
	}
	
	/*
	 * For each node in the shortestPath list, find route to every other node in the graph.
	 * Then get the next hop router for each destination.
	 * Insert the destination, and next hop into the trie for this source node. Do this for all destinations (ie all nodes)
	 * Get longest prefix match from this source to the next router on the shortest path to the final destination.
	 * Go to the next hop router now and repeat.
	 */
	private static void executeRouting(){
		HashMap<Integer, DijkstraNode> allNodes = dijkstra.getAllNodes();
		
		int source, destination, nextHop, finalDestination;
		Router sourceRouter;
		String finalDestinationIP;
		
		finalDestination = shortestPath.get(shortestPath.size()-1); //last element in the shortest path is the final dest.
		finalDestinationIP = routerList.get(finalDestination).ipAddress;
		
		for(int i = 0; i < shortestPath.size() -1; i++){
			//find shortest path from this to every other node.
			source = shortestPath.get(i);
			sourceRouter = routerList.get(source);
			
			BinaryTrie sourceRoutingTable = sourceRouter.getRoutingTable();
			
			Iterator<Integer> allNodesIterate = allNodes.keySet().iterator();
			while(allNodesIterate.hasNext()){
				destination = allNodesIterate.next();
				if(destination != source){
					//find shortest path from source to all other nodes.
					dijkstra.findShortestPath(source, destination);
//					System.out.println("source: " + source + " dest: " + destination);
//					dijkstra.printPath();
					
					//get the next hop router for this pair of source and destination.
					nextHop = dijkstra.getNextHopFromSourceToDest();
					
					//now we have the source, destination, and nextHop. Get the ip now.
					String destIp = routerList.get(destination).ipAddress;
					String nextHopIp = routerList.get(nextHop).ipAddress;
//					System.out.println("inserting into trie: " + destIp + ", " + nextHopIp);
					//insert destination and next hop into the trie of the source.
					sourceRoutingTable.insert(convertIPToBinary(destIp), nextHopIp);
				}
			}
			sourceRoutingTable.merge(sourceRoutingTable.rootNode);

			//Print longest prefix match here, searching for the final destination.
			TrieNode result = sourceRoutingTable.search(convertIPToBinary(finalDestinationIP));
			System.out.println("prefix: " + result.prefix);
		}
	}
	
	
	private static void getShortestPath(DijkstraNode destNode){
		int path = destNode.getNodeId();
		shortestPath.add(0, path);
		System.out.println(destNode.getSourceDistance());
		
		DijkstraNode parent = destNode.parentNode;
		while(parent != null){
			path = parent.getNodeId();
			shortestPath.add(0, path); //add to the beginning of the list. Subsequent nodes will be pushed ahead.
			parent = parent.parentNode;
		}
	}
	
	private static void readInputIPFromFile(String inputFileName) throws Exception{
		FileReader input_file = new FileReader(inputFileName);
		BufferedReader reader = new BufferedReader(input_file);
		int i = 0;
		String ip;
		
		while((ip = reader.readLine()) != null){
			Router router = new Router();
			router.ipAddress = ip;
			router.graphId = i;
			routerList.add(i, router);
			i++;
			reader.readLine();
		}
	}
	
	private static void readInputGraphFromFile(String inputFileName) throws Exception{
		FileReader input_file = new FileReader(inputFileName);
		BufferedReader reader = new BufferedReader(input_file);
		
		String line1 = reader.readLine();
		int nodes = line1.charAt(0);
		int edges = line1.charAt(2);
		
		while(reader.readLine() != null){
			String line = reader.readLine();
			if(line == null){
				break;
			}
			String[] lineSplit = line.split(" ");
			
			int node1 = Integer.parseInt(lineSplit[0]);
			int node2 = Integer.parseInt(lineSplit[1]);
			double weight = Double.parseDouble(lineSplit[2]);
			dijkstra.insertEdge(node1, node2, weight);
		}
		reader.close();
		input_file.close();
	}
	
	/*
	 * Converts String ip to binary, duh.
	 */
	public static String convertIPToBinary(String inputIp){
		byte[] bytes;
		try {
			bytes = InetAddress.getByName(inputIp).getAddress();
			String data_out = new BigInteger(1, bytes).toString(2);
			return data_out;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
