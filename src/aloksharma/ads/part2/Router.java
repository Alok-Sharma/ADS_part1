package aloksharma.ads.part2;

/**
 * Class representing a single router. Each router has an ip address, a routing table represented by a 
 * BinaryTrie, and a graphID which is the name of the router in the dijkstras undirected graph.
 * @author alsharma
 */
public class Router {
	String ipAddress = null;
	BinaryTrie routingTable = new BinaryTrie();
	int graphId;
	
	public Router() {
		
	}
	
	public BinaryTrie getRoutingTable(){
		return this.routingTable;
	}
}
