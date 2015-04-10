package aloksharma.ads.part1;

public class HeapNode {
	private int data;
	public int degree;
	public double priority;
	
	public HeapNode prev;
	public HeapNode next;
	public HeapNode parent;
	public HeapNode child;
	
	public Boolean childCut;
	
	public HeapNode(int data, double priority){
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
