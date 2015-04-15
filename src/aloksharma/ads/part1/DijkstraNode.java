package aloksharma.ads.part1;

import java.util.HashMap;

public class DijkstraNode {
	int nodeId;
	double sourceDistance;
	public DijkstraNode parentNode;
	
	HashMap<DijkstraNode, Double> adjacencyList = new HashMap<>(); //Neigbour node, and distance to it.
	
	public DijkstraNode(int id){
		this.nodeId = id;
		this.parentNode = null;
		this.sourceDistance = Double.POSITIVE_INFINITY;
	}
	
	public void resetNode(){
		this.parentNode = null;
		this.sourceDistance = Double.POSITIVE_INFINITY;
	}
	
	public void addNeighbour(DijkstraNode node, double distance){
		adjacencyList.put(node, distance);
	}
	
	//will return null if node isnt a neighbour to me.
	public double getNeighbourDistance(DijkstraNode node){
		return adjacencyList.get(node);
	}
	
	public HashMap<DijkstraNode, Double> getAllNeighbours(){
		return adjacencyList;
	}
	
	public int getNodeId(){
		return this.nodeId;
	}
	
	public double getSourceDistance(){
		return sourceDistance;
	}
	
	public void setSourceDistance(double distance){
		this.sourceDistance = distance;
	}
}
