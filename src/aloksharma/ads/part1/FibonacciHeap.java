package aloksharma.ads.part1;

import java.util.ArrayList;
import java.util.List;

//merge function not implemented.
public class FibonacciHeap {
	
	Node minNode = null;
	int heapSize = 0;
	
	public Node insertNode(int data, double priority){
		Node newNode = new Node(data, priority);
		minNode = mergeLists(minNode, newNode);
		heapSize++;
		return newNode;
	}
	
	public Node getMin(){
		return minNode;
	}
	
	public int getSize(){
		return heapSize;
	}
	
	public Node removeMin(){
		if(minNode == null)
			return null;
		
		Node minCopy = minNode;
		heapSize--;
		
		//case 1, min node is alone.
		if(minNode.next == minNode){
			minNode = null; //remove it.
		}
		//case 2, connect the neighbours of min node together.
		else{ 
			minNode.prev.next = minNode.next;
			minNode.next.prev = minNode.prev;
			minNode = minNode.next;
		}
		//at this point minNode is some arbit sibling of the min element, in the topmost list.
		
		//clear parent field of all children of minNode.
		if(minCopy.child != null){
			Node child = minCopy.child;
			do{
				child.parent = null;
				child = child.next;
			}while(child != minCopy.child);
		}
		
		minNode = mergeLists(minNode, minCopy.child);
		if(minNode == null)
			return minCopy;
		
		//merge children of min node. Subtress of same degrees need to be merged. All then should be put in top list.
		//To keep track of trees with varied degrees, keep in arraylist, where index is their degree.
		//If more than 1 sub tree of same degree, merge them first.
		List<Node> treeTable = new ArrayList<>();
		
		List<Node> toVisit = new ArrayList<>();
		
		for(Node currNode = minNode; toVisit.isEmpty() || toVisit.get(0) != currNode; currNode = currNode.next){
			toVisit.add(currNode);
		}
		
		for(Node curr : toVisit){
			while(true){
				
				while(curr.degree >= treeTable.size()){
					treeTable.add(null); //increase size of tree table
				}
				
				if(treeTable.get(curr.degree) == null){
					treeTable.set(curr.degree, curr); //if no one was present at the index of this degree, then just add the node and break out.
					break;
				}
				
				Node existing = treeTable.get(curr.degree);
				treeTable.set(curr.degree, null);
				
				//find out which is the larger degree
				Node min = (existing.priority < curr.priority)? existing : curr;
				Node max = (existing.priority < curr.priority)? curr : existing;
				
				//remove max out of root list, and make child of min.
				max.next.prev = max.prev;
				max.prev.next = max.next;
				
				max.next = max.prev = max;
				min.child = mergeLists(min.child, max);
				
				max.parent = min;
				max.childCut = false;
				
				min.degree++;
				curr = min; //continue merging this tree
			}
			
			if(curr.priority <= minNode.priority) minNode = curr;
		}
		return minCopy;
		
	}
	
	private Node mergeLists(Node one, Node two){
		if(one == null && two == null){
			return null;
		}else if(one == null && two != null){
			return two;
		}else if(one != null && two == null){
			return one;
		}else if(one.next == null && two.next == null){
			one.next = two;
			one.prev = two;
			two.next = one;
			two.prev = one;
			return one.priority < two.priority ? one : two;
		}else if(one.next == null && two.next != null){
			Node twoNext = two.next;
			two.next = one;
			one.prev = two;
			one.next = twoNext;
			twoNext.prev = one;
			return one.priority < two.priority ? one : two;
		}else if(one.next != null && two.next == null){
			Node oneNext = one.next;
			one.next = two;
			two.prev = one;
			two.next = oneNext;
			oneNext.prev = two;
			return one.priority < two.priority ? one : two;
		}else{
			//both aren't null, merge them.
			Node oneNext = one.next;
			one.next = two.next;
			one.next.prev = one;
			two.next = oneNext;
			two.next.prev = two;
			
			return one.priority < two.priority ? one : two;
		}
	}
	
	public void decreaseKey(Node node, double newPriority){
		if(newPriority > node.priority)
			System.err.println("New priority exceeds old");
		
		node.priority = newPriority;
		if(node.parent != null && node.priority <= node.parent.priority)
			removeNode(node);
		
		if(node.priority <= minNode.priority)
			minNode = node;
	}
	
	private void removeNode(Node node){
		node.childCut = false;
		
		if(node.parent == null) return; //if no parents, do nothing.
		
		if(node.next != node){ 
			//has siblings
			node.next.prev = node.prev;
			node.prev.next = node.next;
		}
		
		//if I am the child pointer of my parents, re wire to a sibling.
		if(node.parent.child == node){
			if(node.next != node){
				node.parent.child = node.next;
			}else{
				node.parent.child = null; //if no siblings.
			}
		}
		
		node.parent.degree--;
		
		node.prev = node.next = node;
		minNode = mergeLists(minNode, node);
		
		if(node.parent.childCut)
			removeNode(node.parent);
		else
			node.parent.childCut = true;
		
		node.parent = null;
	}
	
	public void deleteNode(Node node){
		decreaseKey(node, Double.NEGATIVE_INFINITY);
		
		removeMin();
	}
	

}
