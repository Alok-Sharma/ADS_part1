package aloksharma.ads.part2;

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
