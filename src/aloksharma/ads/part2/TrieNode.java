package aloksharma.ads.part2;

public class TrieNode {
	boolean isLeaf;
	String nextHopIP;
	String destinationIP;
	TrieNode leftChild;
	TrieNode rightChild;
	TrieNode parentNode;
	String prefix;
	int level;
	
	public TrieNode() {
		this.leftChild = null;
		this.rightChild = null;
		this.parentNode = null;
		this.isLeaf = true;
		this.level = 0;
		this.nextHopIP = null;
		this.destinationIP = null;
		this.prefix = "";
	}
	
	public boolean hasChild(){
		if(this.leftChild != null || this.rightChild != null){
			return true;
		}else{
			return false;
		}
	}
	
	//whoever calls me better know if I already have children.
	public void addChild(TrieNode child){
		//check the level at which you are at. Get that ith bit
		//if that bit is 0-> left child, else right child.
		String childDest = child.destinationIP;
		
		int childDecide = Integer.parseInt(childDest.charAt(level) + "");
		if(childDecide == 0){
			this.leftChild = child;
			child.prefix = this.prefix + "0";
		}else{
			this.rightChild = child;
			child.prefix = this.prefix + "1";
		}
		child.level = this.level + 1;
		child.parentNode = this;
	}
	
	public String getLongestPrefix(String inputDestinationIP){
		int i = 0;
		for(i = 0; i < inputDestinationIP.length(); i++){
			if(inputDestinationIP.charAt(i) != this.destinationIP.charAt(i)){
				break;
			}
		}
		
		return inputDestinationIP.substring(0, i);
	}
	
	
}
 