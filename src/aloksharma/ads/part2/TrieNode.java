package aloksharma.ads.part2;

/**
 * Class representing a single node in the binary trie.
 * @author alsharma
 *
 */
public class TrieNode {
	boolean isLeaf;
	String nextHopIP;
	String destinationIP;
	TrieNode leftChild;
	TrieNode rightChild;
	TrieNode parentNode;
	String prefix;
	int level; //The level at which this node is present at in the trie. The root node is at level 0. 
	
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
	
	/**
	 * Check if this node has any children.
	 * @return true if has a left or right child. Else false.
	 */
	public boolean hasChild(){
		if(this.leftChild != null || this.rightChild != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Add a new child to this node. This function will check itself if the new child
	 * should be a left or a right child depending on the appropraite bit and the 
	 * level at which I am present in the trie. Update the prefixes of my child.
	 * @param child The new child to add.
	 */
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
}
 