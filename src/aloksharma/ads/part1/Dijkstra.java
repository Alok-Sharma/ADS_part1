package aloksharma.ads.part1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

	int source;
	int destination;
//	ArrayList<DijkstraNode> nodeList = new ArrayList<>();
	HashMap<Integer, DijkstraNode> nodeList = new HashMap<>();
	
	public Dijkstra(int source, int destination){
		this.source = source;
		this.destination = destination;
	}
	
	public void insertEdge(int node1, int node2, int weight){
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
		
	}
	
	public void printGraph(){
		Iterator<Integer> iterator = nodeList.keySet().iterator();
		DijkstraNode node;
		while(iterator.hasNext()){
			node = nodeList.get(iterator.next());
			System.out.println("neighbours of " + node.getNodeId());
			HashMap<DijkstraNode, Integer> adjaceny = node.getAllNeighbours();
			Iterator<DijkstraNode> iterator2 = adjaceny.keySet().iterator();
			while(iterator2.hasNext()){
				DijkstraNode neighbour = iterator2.next();
				System.out.println(neighbour.getNodeId() + " at a distance of " + adjaceny.get(neighbour));
			}
		}
	}
	
}
