package assignment09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


/**
 * PathFinder algorithm to find the shortest path in a maze.  Uses Breadth First Search.
 * @author Li Yu and Christopher Murphy
 */
public class PathFinder {
	
	
	/**
	 * solveMaze Starts the algorithm for finding the quickest path to a maze file.  
	 * @param inputFile - This is the maze file to be checked
	 * @param outputFile - This is the outputFile it saves the maze and path to
	 */
	public static void solveMaze(String inputFile, String outputFile){
		
		if(new File(inputFile).isFile() == false)
		{
			try {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		Graph maze = null;
		
		//Makes a new graph of the inputfile using a 2d array of nodes
		maze = new Graph(inputFile);
		
		//Finds the start and end Nodes
		Node start = maze.getStart();
		Node end = maze.getEnd();
		
		//This runs the breadth first search algorithm with the start and end nodes.
		BFS(start, end);
		Node temp = end;
		
		//Here it prints out the path using reDraw
		while(temp.cameFrom != start && end.cameFrom != null){
			temp = temp.cameFrom;
			maze.reDraw(temp.x ,temp.y);
		}		
		
		PrintWriter output = null;
		
		//This starts a PrintWriter to save the map to a separate file
		try {
			output = new PrintWriter(outputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//This prints the maze dimensions
		output.println(maze.height + " " + maze.width);
		
		//This prints the rest of the maze
		for(int i=0; i < maze.height; i++){
			for(int t=0; t < maze.width; t++){
				if(maze.graph[i][t] == null){
					output.print('X');
				}else{
					output.print(maze.graph[i][t].data);
				}
			}
			output.println();
		}
		output.close();
	}

	/**
	 * This runs the Breadth First Search algorithm using a Queue.
	 * @param start - Starting Node 'S'
	 * @param end - End Node 'G'
	 */
	private static void BFS(Node start, Node end){
		//creates a queue for the breadth first algorithm
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(start);
		while(!queue.isEmpty()){
			Node current = queue.poll();
			if(current.equals(end)){
				return;
			}
			//this runs the the current neighbors and adds them if they're unvisited 
			//and marks them visited.  Also records where it camefrom to retrace.  
			for(Node next : current.neighbors){
				if(!next.visited){
					next.visited = true;
					next.cameFrom = current;
					queue.add(next);
				}
			}
		}
	}

	/**
	 * This sets up the Graph class 
	 * for all the nodes in a 2d array and connects them to their neighbors.
	 */
	private static class Graph{
		//makes a 2d array of Nodes, height and width, and start and end nodes.
		private Node[][] graph;
		private int height;
		private int width;
		private Node start;
		private Node end;
	
		//prints out the path character
		public void reDraw(int x, int y){
			graph[x][y].data = '.';
		}
	
		public Graph(String fileName){
			File file = new File(fileName);
			BufferedReader input = null;
			try {
				input = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] dimensions = null;
			try {
				dimensions = input.readLine().split(" ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			height = Integer.parseInt(dimensions[0]);
			width = Integer.parseInt(dimensions[1]);
			graph = new Node[height][width];
			Scanner scan = null;
			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//garbage to disregard the first line
			String garbage = scan.nextLine();
			int line = -1;
			while(scan.hasNextLine()){
				String temp = scan.nextLine();
				line++;
				for(int index = 0; index < this.width; index++){
					if(temp.charAt(index) == 'X'){
						graph[line][index] = null;
					}else{
						graph[line][index] = new Node(temp.charAt(index), null, line, index);
					}
				}
			}
			//adds the neighbors to the graph if its not null and checks for start and end values
			for(int x = 1; x < this.height - 1; x++){
				for(int y = 1; y < this.width - 1; y++){
					if(graph[x][y] != null){
						List<Node> neighbors = new ArrayList<Node>();
						if(graph[x+1][y] != null){
							neighbors.add(graph[x+1][y]);
						}
						if(graph[x-1][y] != null){
							neighbors.add(graph[x-1][y]);
						}
						if(graph[x][y+1] != null){
							neighbors.add(graph[x][y+1]);
						}
						if(graph[x][y-1] != null){
							neighbors.add(graph[x][y-1]);
						}
						graph[x][y].neighbors = neighbors;
						if(graph[x][y].data == 'S'){
							start = graph[x][y];
						}
						if(graph[x][y].data == 'G'){
							end = graph[x][y];
						}	
					}
					
				}
			}
			scan.close();
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public Node getStart(){
			return this.start;
		}
		
		public Node getEnd(){
			return this.end;
		}
	}
	
	/**
	 * This sets up the Node class for saving data to each Node.
	 */
	private static class Node{
		//data for each node and a list of neighbors
		Character data;
		Node cameFrom;
		int x;
		int y;
		List<Node> neighbors;
		boolean visited;
	
		public Node(Character data, List<Node> neighbors, int x, int y){
			this.data = data;
			this.neighbors = neighbors;
			this.x = x;
			this.y = y;
			this.cameFrom = null;
			this.visited = false;
		}
	}
}