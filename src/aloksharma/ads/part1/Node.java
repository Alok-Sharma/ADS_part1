package aloksharma.ads.part1;

public class Node {
	private int data;
	private int degree;
	private int priority;
	
	public Node prev;
	public Node next;
	public Node parent;
	public Node child;
	
	private Boolean childCut;
	
	public Node(int data, int priority){
		this.data = data;
		this.childCut = false;
		this.priority = priority;
	}
	
	/*
	 * Setter methods.
	 */
	public void setPriority(int priority){
		this.priority = priority;
	}
	
	public void setData(int data){
		this.data = data;
	}
	
	public void setChildCut(Boolean childCut){
		this.childCut = childCut;
	}
	
	/*
	 * Getter methods
	 */
	public int getData(){
		return data;
	}
	
	public boolean isChildCut(){
		return childCut;
	}
	
	public int getPriority(){
		return priority;
	}
	
}
