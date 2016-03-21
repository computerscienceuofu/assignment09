package assignment09;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class PathFinder {
	
	//I've started this without using a specific graph class.  I think we will need to incorporate one somehow.
	// Not sure how to yet though.  
	public static void solveMaze(String inputFile, String outputFile) 
	{	
		//Gets the dimensions to assign to the 2d String array size
		String[][] Dimensions = getDimensions(inputFile);
		
		//Gets each row and column character of the maze file and adds it the 2d String array
		String[][] Map = getMap(inputFile, Dimensions);
		
		int startRow = 0;
		int startColumn = 0;		
		
		
		//This finds the Starting values, startRow and startColumn
		for(int i = 0; i < Map.length; i++)
		{
			for(int j = 0; j < Map[0].length; j++)
			{
				String check = Map[i][j];
				if (check.compareTo("S") == 0)
				{
					startRow = i;
					startColumn = j;
				}
			}
		}

		//This sends the startRow, startColumn, 2d String array named Map (which contains all the coordinates), 
		//and the output and input file names to getPath.  
		System.out.println(getPath(startRow, startColumn, Map, outputFile, inputFile));		
	}	
	
	
	private static boolean getPath(int startRow, int startColumn, String[][] Map, String outputFile, String inputFile) {
		//Creates a Queue from java's LinkedList, which is assigned to the Node class
		Queue<Node> q = new LinkedList<Node>();				
		
		//Sets the Start Node Row and Column values.
		Node Start = new Node(startRow + " " + startColumn);
		Start.column = startColumn;
		Start.row = startRow;
		
		//This is the up, left, right, and down coordinates for searching neighboring items.
		int[]dx = {0, 0, -1, 1};
		int[]dy = {-1, 1, 0, 0};
		

		//Adds the Start Node to the Queue.
		q.add(Start);
		while(q.size() > 0)
		{
			//This takes the first item in the Queue for comparing and looking at its neighbors.
			Node x = q.poll();
			
			if(x.visited != 1)
			{
				
			//If the current node is the Goal, then it retraces it's steps (cameFrom) and prints out the path onto the 2d array named Map.
			if (Map[x.row][x.column].compareTo("G") == 0)
					{
					while (x.cameFrom != null)
					{
						if (Map[x.row][x.column].compareTo("G") != 0)
						{
						Map[x.row][x.column] = ".";
						}
						x = x.cameFrom;
					}					
					try {
						//Here is where it writes the solution and maze to a text file.
						File file = new File(outputFile);

						if (!file.exists()) {
							file.createNewFile();
						}

						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						
						File text = new File(inputFile);
						Scanner rowColumn;
						try {
							rowColumn = new Scanner(text);
							String row = rowColumn.next();
							String row2 = rowColumn.next();
							bw.write(row + " " + row2 + "\n");								
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}												
						
						for(int i = 0; i < Map.length; i++)
						{
							for(int j = 0; j < Map[0].length; j++)
							{
								bw.write(Map[i][j]);							
							}
							bw.write("\n");
						}						
						bw.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
					
					return true;
					}
			
			//This checks the up, left, right, and down values of the current Node.
			for(int i = 0; i < 4; i++)
			{
				int yy = x.row + dx[i];
				int xx = x.column + dy[i];
			
				if(xx >= 1 && yy >= 1)
				{
				
					if(Map[yy][xx].compareTo(" ") == 0 && Map[yy][xx].compareTo("X") != 0 && (x.visited != 1) || Map[yy][xx].compareTo("G") == 0)
					{
						boolean on = true;
						
						//This checks the Queue and makes sure the current item isn't in there.  
						//If it is, then it turns off the switch. 
						for(Node I: q)
						{
						if (I.column == xx && I.row == yy)
						{
						on = false;
						}
						
						}
						if (on == true)
						{
							
						//If a node hasn't been looked at yet, it creates a node for it and adds it to the Queue.  
						Node Neighbor = new Node(yy + " " + xx);
						Neighbor.column = xx;
						Neighbor.row = yy;
						Neighbor.cameFrom = x;
						q.add(Neighbor);
						}
						//Turns the switch back on after the ForEach loop
						on = true;					
					}
				}
				
			}
		}
			
			//Here is where I'm trying to check if all the Nodes in the Queue have been visited,
			//If so, then it will return false because there is no path.   Not currently working though.  
			x.visited = 1;
			int counter = 0;
			for(Node temp: q)
			{
				if (temp.visited == 1)
					{
					counter++;										
					}
				if (q.size() == counter)
					{
					return false;
					}
			}
	}		
		return false;
	}


	private static String[][] getMap(String inputFile, String[][] Map) {
		
		//Gets Dimensions of the File and makes a String array named dimensions
		File text = new File(inputFile);	
		int j = 0;

		try {
			Scanner s = new Scanner(text);
			String nex = s.nextLine();
			
			while(s.hasNextLine())
			{
			nex = s.nextLine();
			for (int i = 0; i < nex.length(); i++)
			{
				char c = nex.charAt(i);
				String d = Character.toString(c);
				Map[j][i] = d;	
			}
			j++;
			}	
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return Map;
}


	public static String[][]  getDimensions(String inputFile)
	{
		//Gets Dimensions of the File and makes a String array named dimensions
				File text = new File(inputFile);
				Scanner rowColumn;
				String[][] dimensions = null;
				try {
					rowColumn = new Scanner(text);
					String row = rowColumn.next();
					String column = rowColumn.next();
					Integer row2 = Integer.parseInt(row);
					Integer column2 = Integer.parseInt(column);		
					dimensions = new String[row2][column2];
					rowColumn.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return dimensions;
	}

}
