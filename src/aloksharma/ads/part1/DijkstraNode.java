package aloksharma.ads.part1;

import java.util.HashMap;

public class DijkstraNode {
	int nodeId;
	HashMap<DijkstraNode, Integer> adjacencyList = new HashMap<>();
	
	public DijkstraNode(int id){
		this.nodeId = id;
	}
	
	public void addNeighbour(DijkstraNode node, int distance){
		adjacencyList.put(node, distance);
	}
	
	//will return null is node isnt a neighbour to me.
	public int getNeighbour(DijkstraNode node){
		return adjacencyList.get(node);
	}
	
	public HashMap<DijkstraNode, Integer> getAllNeighbours(){
		return adjacencyList;
	}
	
	public int getNodeId(){
		return this.nodeId;
	}
}
