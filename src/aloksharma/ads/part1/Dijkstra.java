package aloksharma.ads.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * The undirected graph on which we run our Dijkstras algorithm.
 */
public class Dijkstra {

	int source;
	int destination;
	HashMap<Integer, DijkstraNode> nodeList = new HashMap<>(); //integer hold node id.
	
	/**
	 * 
	 * @return Complete map of all nodes within this graph.
	 */
	public HashMap<Integer, DijkstraNode> getAllNodes(){
		return nodeList;
	}
	
	/**
	 * Insert edges into this graph. If nodes already exist within the graph, a new edge is simply added to 
	 * them.
	 * @param node1 Name of node1 to insert
	 * @param node2 Name of node2 to insert
	 * @param weight Weight of the edge between node1 and node2
	 */
	public void insertEdge(int node1, int node2, double weight){
		DijkstraNode dnode1;
		DijkstraNode dnode2;
		
		//Make a new node, only if node doesnt already exist within nodeList.
		if(!nodeList.containsKey(node1)){
			dnode1 = new DijkstraNode(node1);
			nodeList.put(node1, dnode1);
		}else{
			dnode1 = nodeList.get(node1);
		}
		
		if(!nodeList.containsKey(node2)){
			dnode2 = new DijkstraNode(node2);
			nodeList.put(node2, dnode2);
		}else{
			dnode2 = nodeList.get(node2);
		}
		
		//Add the two nodes in each others adjaceny list.
		dnode1.addNeighbour(dnode2, weight);
		dnode2.addNeighbour(dnode1, weight);
	}
	
	/**
	 * Reset the node properties for each node. Use this if we are going to be running
	 * over the Dijktstra algorithm multiple times. Each time it is run, we need to
	 * reset the distance and parent parameters for each node.
	 */
	private void resetNodes(){
		//iterate over all nodes in the nodelist and call their reset function.
		Iterator<Integer> allDijkstraNodes = nodeList.keySet().iterator();
		DijkstraNode tempNode;
		while(allDijkstraNodes.hasNext()){
			tempNode = nodeList.get(allDijkstraNodes.next());
			tempNode.resetNode();
		}
	}
	
	/**
	 * Executes the Dijkstra algorithm from the source to the destination. At the end of this,
	 * the destination node will have its parent node property set. We can then reverse traverse from
	 * the destination, back to the source and determine the shortest path.
	 * @param source
	 * @param destination
	 */
	public void findShortestPath(int source, int destination){
		this.source = source;
		this.destination = destination;
		resetNodes();
		DijkstraNode sourceNode = nodeList.get(source);
		sourceNode.setSourceDistance(0);
		DijkstraNode destNode = nodeList.get(destination);
		FibonacciHeap heap = new FibonacciHeap();
		
		//Add all nodes to the heap.
		Iterator<Integer> dNodes = nodeList.keySet().iterator();
		DijkstraNode tempNode;
		while(dNodes.hasNext()){
			
			tempNode = nodeList.get(dNodes.next());
			int nodeId = tempNode.getNodeId();
			double sourceDist = tempNode.getSourceDistance();
			
			heap.insertNode(nodeId, sourceDist);
		}
		
		//store all nodes that are on your shortest path in here:
		ArrayList<DijkstraNode> pathNodes = new ArrayList<>();
		
		//for every vertex in the graph
		Iterator<Integer> dijkstraNodes = nodeList.keySet().iterator();
		while(dijkstraNodes.hasNext()){
			//remove the minimum element from heap/
			dijkstraNodes.next();
			HeapNode minHeapNode = heap.removeMin();
			DijkstraNode pathNode = nodeList.get(minHeapNode.getData());
			pathNodes.add(pathNode);
			
			//Stop calculating when reached destination.
			if(pathNode.getNodeId() == destNode.getNodeId()){
				break;
			}
			
			//get adjacency list of this node
			HashMap<DijkstraNode, Double> neighbours =  pathNode.getAllNeighbours();
			
			//remove nodes that are already on the shortest path.
			//Neighbours = removeFromNeighbours(neighbours, pathNodes);
			
			Iterator<DijkstraNode> neighbourIterator = neighbours.keySet().iterator();
			while(neighbourIterator.hasNext()){
				//if distance of pathNode + weight between pathNode and this neighbour < distance of this neighbour
				DijkstraNode neighbourNode = neighbourIterator.next();
				double existingDistance = neighbourNode.getSourceDistance();
				double dijkstraDistance = pathNode.getSourceDistance() + pathNode.getNeighbourDistance(neighbourNode);
				
				if(dijkstraDistance < existingDistance){
					neighbourNode.setSourceDistance(dijkstraDistance);
					neighbourNode.parentNode = pathNode;
					//how to perform decrease key? Have node instance? Had to create a nodeList in the heap to store node instances.
					HeapNode heapNode = heap.getNodeInstance(neighbourNode.getNodeId());
					heap.decreaseKey(heapNode, dijkstraDistance);
				}
			}
		}
	}
	
	//removes those nodes from the adjacency list of the current node, that have already been added to the list.
	private HashMap<DijkstraNode, Double> removeFromNeighbours(HashMap<DijkstraNode, Double> neighbours, ArrayList<DijkstraNode> pathNodes){
		
		for(int i = 0; i < pathNodes.size(); i++){
			if(neighbours.containsKey(pathNodes.get(i))){
				neighbours.remove(pathNodes.get(i));
			}
		}
		return neighbours;
	}
	
	/**
	 * Iterates in reverse from the destination node, back to the source, printing out the path in reverse.
	 */
	public void printPath(){
		DijkstraNode destNode = nodeList.get(destination);
		System.out.println("path of length: " + destNode.getSourceDistance());
		System.out.println(destNode.getNodeId());
		DijkstraNode parent = destNode.parentNode;
		
		//parent will be null for the source node only.
		while(parent != null){
			System.out.println(parent.getNodeId());
			parent = parent.parentNode;
		}
	}
	
	/**
	 * @return The destination node. Can be used to iterate backwards to source.
	 */
	public DijkstraNode getDestNode(){
		return nodeList.get(destination);
	}
	
	/**
	 * Traverses in reverse from the destination node back to the source, and the node immediately
	 * after the source on the shortest path.
	 * @return name of the node immediately after the source, on the shortest path to the destination.
	 */
	public int getNextHopFromSourceToDest(){
		//from dest, go back to source.
		DijkstraNode currNode = this.getDestNode();
		DijkstraNode parent = currNode.parentNode;
		if(parent == null){
			System.out.println("parent is null for " + this.getDestNode().nodeId + " source: " + this.source);
		}
		while(parent.nodeId != this.source){
			currNode = parent;
			parent = parent.parentNode;
		}
		return currNode.getNodeId();
	}
	
	/**
	 * For debug purposes, to check correctness of the constructed graph.
	 */
	public void printGraph(){
		Iterator<Integer> iterator = nodeList.keySet().iterator();
		DijkstraNode node;
		while(iterator.hasNext()){
			//for each node in the nodelist, get its adjacency list.
			node = nodeList.get(iterator.next());
			System.out.println("neighbours of " + node.getNodeId());
			HashMap<DijkstraNode, Double> adjaceny = node.getAllNeighbours();
			
			//Then iterate over this nodes adjacency list.
			Iterator<DijkstraNode> iterator2 = adjaceny.keySet().iterator();
			while(iterator2.hasNext()){
				//For each neighbour, print the neighbour name, and the distance.
				DijkstraNode neighbour = iterator2.next();
				System.out.println(neighbour.getNodeId() + " at a distance of " + adjaceny.get(neighbour));
			}
		}
	}
}
