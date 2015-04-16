package aloksharma.ads.part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FibonacciHeap {
	
	HeapNode minNode = null;
	HashMap<Integer, HeapNode> nodeList = new HashMap<>();
	int heapSize = 0;
	
	/**
	 * Inserts a new HeapNode with the argument values into the heap. This function internally calls
	 * mergeLists to merge the new node in the top level list.
	 * @param data of the new node.
	 * @param priority priority of the new node.
	 * @return HeapNode instance of the newly inserted node.
	 */
	public HeapNode insertNode(int data, double priority){
		HeapNode newNode = new HeapNode(data, priority);
		minNode = mergeLists(minNode, newNode);
		heapSize++;
		nodeList.put(data, newNode);
		return newNode;
	}
	
	public HeapNode getNodeInstance(int data){
		return nodeList.get(data);
	}
	
	public HeapNode getMin(){
		return minNode;
	}
	
	public int getSize(){
		return heapSize;
	}
	
	/**
	 * @return The minimum node in the heap. Also removes it from the heap.
	 */
	public HeapNode removeMin(){
		if(minNode == null)
			return null;
		
		HeapNode minCopy = minNode;
		heapSize--;
		
		//case 1 min node is alone in the heap.
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
			HeapNode child = minCopy.child;
			do{
				child.parent = null;
				child = child.next;
			}while(child != minCopy.child);
		}
		
		minNode = mergeLists(minNode, minCopy.child);
		if(minNode == null)
			return minCopy;
		
		//merge children of min node. Subtrees of same degrees need to be merged. All then should be put in top list.
		//To keep track of trees with varied degrees, keep in arraylist, where index is their degree.
		//If more than 1 sub tree of same degree, merge them first.
		List<HeapNode> treeTable = new ArrayList<>();
		
		List<HeapNode> toVisit = new ArrayList<>();
		
		for(HeapNode currNode = minNode; toVisit.isEmpty() || toVisit.get(0) != currNode; currNode = currNode.next){
			toVisit.add(currNode);
		}
		
		for(HeapNode curr : toVisit){
			while(true){
				
				while(curr.degree >= treeTable.size()){
					treeTable.add(null); //increase size of tree table
				}
				
				if(treeTable.get(curr.degree) == null){
					treeTable.set(curr.degree, curr); //if no one was present at the index of this degree, then just add the node and break out.
					break;
				}
				
				HeapNode existing = treeTable.get(curr.degree);
				treeTable.set(curr.degree, null);
				
				//find out which is the larger degree
				HeapNode min = (existing.priority < curr.priority)? existing : curr;
				HeapNode max = (existing.priority < curr.priority)? curr : existing;
				
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
	
	/**
	 * Merges two linked lists rooted at the two HeapNodes.
	 * @param one 
	 * @param two
	 * @return pointer to smaller node of the merged list.
	 */
	private HeapNode mergeLists(HeapNode one, HeapNode two){
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
			HeapNode twoNext = two.next;
			two.next = one;
			one.prev = two;
			one.next = twoNext;
			twoNext.prev = one;
			return one.priority < two.priority ? one : two;
		}else if(one.next != null && two.next == null){
			HeapNode oneNext = one.next;
			one.next = two;
			two.prev = one;
			two.next = oneNext;
			oneNext.prev = two;
			return one.priority < two.priority ? one : two;
		}else{
			//both aren't null, merge them.
			HeapNode oneNext = one.next;
			one.next = two.next;
			one.next.prev = one;
			two.next = oneNext;
			two.next.prev = two;
			
			return one.priority < two.priority ? one : two;
		}
	}
	
	/**
	 * Decreases the priority of the specified heap node.
	 * @param node
	 * @param newPriority
	 */
	public void decreaseKey(HeapNode node, double newPriority){
		if(newPriority > node.priority){
			//this is a problem.
			System.out.println("invalid new priority");
			return; 
		}
		node.priority = newPriority;
		if(node.parent != null && node.priority <= node.parent.priority)
			removeNode(node);
		
		if(node.priority <= minNode.priority)
			minNode = node;
	}
	
	/**
	 * Remove a specific node from the heap, and places it into the topmost list. This is a private method and
	 * is only used internally by decrease key only.
	 * @param node
	 */
	private void removeNode(HeapNode node){
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
	
	/**
	 * Public method used for deleting a specific node completely from the heap.
	 * @param node
	 */
	public void deleteNode(HeapNode node){
		decreaseKey(node, Double.NEGATIVE_INFINITY);
		removeMin();
	}
	

}
