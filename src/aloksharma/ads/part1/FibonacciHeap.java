package aloksharma.ads.part1;

public class FibonacciHeap {
	
	Node minNode = null;
	int heapSize = 0;
	
	public Node enqueue(int data, int priority){
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
		
		
	}
	
	private Node mergeLists(Node one, Node two){
		if(one == null && two == null){
			return null;
		}else if(one == null && two != null){
			return two;
		}else if(one != null && two == null){
			return one;
		}else{
			//both aren't null, merge them.
			Node oneNext = one.next;
			one.next = two.next;
			one.next.prev = one;
			two.next = oneNext;
			two.next.prev = two;
			
			return one.getPriority() < two.getPriority() ? one : two;
		}
	}
	

}
