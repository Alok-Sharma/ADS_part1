package aloksharma.ads.part1;

import java.util.HashMap;

/**
 * Class representing a single node in the undirected Dijkstra graph.
 * @author alsharma
 *
 */
public class DijkstraNode {
	int nodeId; //data stored within each node i.e.: The node name.
	double sourceDistance; //distance from this node to the source node.
	public DijkstraNode parentNode; //parent of this node. used for reverse traversal back to the source.
	
	HashMap<DijkstraNode, Double> adjacencyList = new HashMap<>(); //Neigbour nodes, and distance to it.
	
	public DijkstraNode(int id){
		this.nodeId = id;
		this.parentNode = null;
		this.sourceDistance = Double.POSITIVE_INFINITY; //initially, every node is at an infinite distance.
	}
	
	/**
	 * Resets parentNode and sourceDistance parameters of this node.
	 */
	public void resetNode(){
		this.parentNode = null;
		this.sourceDistance = Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Add a node to the adjaceny list of this node.
	 * @param node {@link DijkstraNode} Neighbouring node to add.
	 * @param distance {@link double} Distance to neighbouring node being added.
	 */
	public void addNeighbour(DijkstraNode node, double distance){
		adjacencyList.put(node, distance);
	}
	
	/**
	 * Get the distance to a neighbouring node.
	 * @param node Neighbouring node to get distance to.
	 * @return null if node isnt a neighbour to me.
	 */
	public double getNeighbourDistance(DijkstraNode node){
		return adjacencyList.get(node);
	}
	
	/**
	 * Returns the complete adjacency list of this node.
	 * @return A HashMap adjaceny list of this node. Keys are the neighbour nodes, and values are distances to them.
	 */
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
