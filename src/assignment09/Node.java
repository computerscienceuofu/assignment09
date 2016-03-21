package assignment09;


public class Node {
	
	String data;
	public Node cameFrom;
	public int visited;
	public int row;
	public int column;

	public Node(String data) 
	{
		this.data = data;

	}

}
