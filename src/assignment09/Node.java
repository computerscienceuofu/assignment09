package assignment09;

import java.util.List;

public class Node {
	
	String data;
	public Node cameFrom;
	public int visited;
	public int row;
	public int column;
	List<Node> Neighbors;

	public Node(String data) 
	{
		this.data = data;

	}

}
