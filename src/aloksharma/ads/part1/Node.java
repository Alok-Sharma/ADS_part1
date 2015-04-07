package aloksharma.ads.part1;

public class Node {
	private int data;
	public int degree;
	public double priority;
	
	public Node prev;
	public Node next;
	public Node parent;
	public Node child;
	
	public Boolean childCut;
	
	public Node(int data, double priority){
		this.data = data;
		this.childCut = false;
		this.priority = priority;
	}
	
	/*
	 * Setter methods.
	 */
	public void setData(int data){
		this.data = data;
	}
	
	/*
	 * Getter methods
	 */
	public int getData(){
		return data;
	}
}
