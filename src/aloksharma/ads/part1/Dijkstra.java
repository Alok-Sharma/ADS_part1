package aloksharma.ads.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

	int source;
	int destination;
//	ArrayList<DijkstraNode> nodeList = new ArrayList<>();
	HashMap<Integer, DijkstraNode> nodeList = new HashMap<>(); //integer hold node id.
	
	public Dijkstra(int source, int destination){
		this.source = source;
		this.destination = destination;
	}
	
	public void insertEdge(int node1, int node2, double weight){
		DijkstraNode dnode1;
		DijkstraNode dnode2;
		
		if(!nodeList.containsKey(node1)){
			dnode1 = new DijkstraNode(node1);
			nodeList.put(node1, dnode1);
//			nodeList.add(dnode1.getNodeId(), dnode1); //storing the ith node at the ith position.
		}else{
			dnode1 = nodeList.get(node1);
		}
		
		if(!nodeList.containsKey(node2)){
			dnode2 = new DijkstraNode(node2);
			nodeList.put(node2, dnode2);
//			nodeList.add(dnode2.getNodeId(), dnode2);
		}else{
			dnode2 = nodeList.get(node2);
		}
		
		dnode1.addNeighbour(dnode2, weight);
		dnode2.addNeighbour(dnode1, weight);
	}
	
	public void findShortestPath(){
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
//		System.out.println("min: " + heap.getMin().getData()); //should be the source.
		
		//store all nodes that are on your shortes path in here:
		ArrayList<DijkstraNode> pathNodes = new ArrayList<>();
		
		//for every vertex in the graph
		Iterator<Integer> dijkstraNodes = nodeList.keySet().iterator();
//		System.out.println("path: ");
		while(dijkstraNodes.hasNext()){
			//remove the minimum element
			dijkstraNodes.next();
			HeapNode minHeapNode = heap.removeMin();
			DijkstraNode pathNode = nodeList.get(minHeapNode.getData());
			pathNodes.add(pathNode);
//			System.out.println(pathNode.getNodeId());
			
			if(pathNode.getNodeId() == destNode.getNodeId()){
				break;
			}
			
			//get adjacency list of this node
			HashMap<DijkstraNode, Double> neighbours =  pathNode.getAllNeighbours();
			
			//remove nodes that are already on the shortest path.
			neighbours = removeFromNeighbours(neighbours, pathNodes);
			
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
	
	public void printPath(){
		DijkstraNode destNode = nodeList.get(destination);
		System.out.println("path of length: " + destNode.getSourceDistance());
		System.out.println(destNode.getNodeId());
		DijkstraNode parent = destNode.parentNode;
		
		while(parent != null){
			System.out.println(parent.getNodeId());
			parent = parent.parentNode;
		}
	}
	
	
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
