package aloksharma.ads.part2;

import java.util.LinkedList;
import java.util.Queue;


public class BinaryTrie {
	public TrieNode rootNode;
	
	public BinaryTrie(){
		rootNode = new TrieNode();
		rootNode.isLeaf = false;
	}
	
	public void insert(String destinationIP, String nextHop){
		TrieNode currNode = search(destinationIP);
		
		TrieNode newNode = new TrieNode();
		newNode.destinationIP = destinationIP;
		newNode.nextHopIP = nextHop;

		if(currNode.isLeaf == false){
			/*
			 * if the search returned back a non-leaf node, then 
			 * insert the new node as a child of this node.
			 */
			currNode.addChild(newNode);
			//System.out.println("added child");
		}else{
			/*
			 * You reached a leaf node.
			 */
			TrieNode parent = currNode.parentNode;
			int parentLvl = parent.level;
			//compare substrings after the parent level index.
			String currDestSubstring = currNode.destinationIP.substring(parentLvl);
			String newDestSubstring = newNode.destinationIP.substring(parentLvl);
			
			//compare these substrings and see how many nodes need to be put in.
			int i = 0;
			for(i = 0; i < currDestSubstring.length(); i++){
				if(currDestSubstring.charAt(i) != newDestSubstring.charAt(i)){
					break;
				}else{
					/*
					 * if the two characters are same at that point, insert a new node.
					 * This new node should either be a left or a right child depending on what
					 * is the char value at this point of the two substrings.
					 */
					TrieNode newIntermediateNode = new TrieNode();
					newIntermediateNode.isLeaf = false;
					newIntermediateNode.level = parent.level + 1;
					newIntermediateNode.parentNode = parent;
					
					//BE CAREFUL, NOT USING ADDCHILD FUNCTION HERE!
					if(currDestSubstring.charAt(i) == '0'){
						parent.leftChild = newIntermediateNode;
						newIntermediateNode.prefix = parent.prefix + "0";
					}else{
						parent.rightChild = newIntermediateNode;
						newIntermediateNode.prefix = parent.prefix + "1";
					}
					parent = newIntermediateNode;
				}
			}
			
			/*
			 * Now all intermediate nodes have been put in. Insert the newNode and the currNode
			 * as children of the parent.
			 */
			parent.addChild(newNode);
			parent.addChild(currNode); //Hopefully these two wouldve been put as left and right children, instead of just fighting for the same spot.
//			System.out.println("added child after splits");
		}
	}
	
	/*
	 * returns TrieNode where search ended. If the returned TrieNode
	 * has isLeaf true, then there is a nextHopIP for this destinationIP,
	 * else, I found no rule for this destination. Longest prefix matched TrieNode will be returned.
	 * 
	 */
	public TrieNode search(String destinationIP){
		//break the destinationIP. 
		//Iterate over the integers. If 0, get left child, else get right.
		//if isLeaf = true, extract data from it.
		TrieNode currNode = rootNode;
		TrieNode child;
		
		for(int i = 0; i < destinationIP.length() ; i++){
			int digit = Integer.parseInt(destinationIP.substring(i, i+1));
			if(digit == 0){
				child = currNode.leftChild;
			}else{
				child = currNode.rightChild;
			}
			
			if(child == null && currNode.isLeaf == false){
				//I have no rule for this destinationIP
				return currNode;
			}else if(child == null && currNode.isLeaf == true){
				/*
				 * I ended up at a leaf node. This may or may not be the exact destination you are looking for.
				 */
				return currNode;
			}else{
				//child != null, so go to my child.
				currNode = child;
			}
		}
		
		return currNode;
	}
	
	/*
	 * Returns the nextHopIP address only if the destinationIP was found.
	 * If you want to use longest prefix matching, then use the search() function instead.
	 */
	public String searchExactDestination(String destinationIP){
		TrieNode result = search(destinationIP);
		
		if(destinationIP.equals(result.destinationIP)){
			return result.nextHopIP;
		}else{
			return null;
		}
	}
	
	/*
	 * currNode = rootNode
	 */
	final TrieNode DONT_MERGE = new TrieNode();
	public TrieNode merge(TrieNode currNode){
		if(currNode == null){
			return null;
		}else if(!currNode.isLeaf){
			//I'm not a leaf node. Do the post order traversal and get the value of my children.
			TrieNode leftNextHop = merge(currNode.leftChild);
			TrieNode rightNextHop= merge(currNode.rightChild);

			if(leftNextHop == DONT_MERGE || rightNextHop == DONT_MERGE){
				return DONT_MERGE;
			}else if(leftNextHop == null){
				//my left child is null, take value of my right child
				currNode.isLeaf = true;
				currNode.nextHopIP = rightNextHop.nextHopIP;
				currNode.destinationIP = rightNextHop.destinationIP;
				currNode.rightChild = null;
				currNode.leftChild = null;
			}else if(rightNextHop == null){
				//my right child is null, take value of my left child
				currNode.isLeaf = true;
				currNode.nextHopIP = leftNextHop.nextHopIP;
				currNode.destinationIP = leftNextHop.destinationIP;
				currNode.rightChild = null;
				currNode.leftChild = null;
			}else if(leftNextHop.nextHopIP.equals(rightNextHop.nextHopIP)){
				//Both children same. Make me the child, and take either childs value.
				currNode.isLeaf = true;
				currNode.nextHopIP = leftNextHop.nextHopIP; //or rightNextHop
				currNode.destinationIP = leftNextHop.destinationIP;
				currNode.rightChild = null;
				currNode.leftChild = null;
			}else{
				//children werent same, parent cannot merge me.
				return DONT_MERGE;
			}
			return currNode;
		}else{
			//I'm a leaf node, just return my value.
			return currNode;
		}
	}
	
	public void printTrie(){
		TrieNode currNode;
		Queue<TrieNode> q = new LinkedList<TrieNode>();
		q.add(rootNode);
		while(!q.isEmpty()){
			currNode = q.remove();
			if(!currNode.isLeaf){
//				System.out.println("Internal node at: " + currNode.prefix);
			}else{
				System.out.println("Leaf at: " + currNode.prefix + " next hop:" + currNode.nextHopIP);
			}
			
			if(currNode.leftChild != null){
//				System.out.println("left: " + currNode.leftChild.level);
				q.add(currNode.leftChild);
			}
			if(currNode.rightChild != null){
//				System.out.println("right: " + currNode.rightChild.level);
				q.add(currNode.rightChild);
			}
			
		}
	}
}
