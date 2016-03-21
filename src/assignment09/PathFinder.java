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
	
	
	public static void solveMaze(String inputFile, String outputFile) 
	{				
		String[][] Dimensions = getDimensions(inputFile);	
		String[][] Map = getMap(inputFile, Dimensions);
		
		int startRow = 0;
		int startColumn = 0;		
		
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

		System.out.println(getPath(startRow, startColumn, Map, outputFile, inputFile));		
	}	
	
	
	private static boolean getPath(int startRow, int startColumn, String[][] Map, String outputFile, String inputFile) {
		Queue<Node> q = new LinkedList<Node>();
		
		Node Start = new Node(startRow + " " + startColumn);
		Start.column = startColumn;
		Start.row = startRow;
		
		int[]dx = {0, 0, -1, 1};
		int[]dy = {-1, 1, 0, 0};
		

		q.add(Start);
		while(q.size() > 0)
		{
			
			Node x = q.poll();
			
			if(x.visited != 1)
			{
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
			
			for(int i = 0; i < 4; i++)
			{
				int yy = x.row + dx[i];
				int xx = x.column + dy[i];
			
				if(xx >= 1 && yy >= 1)
				{
				
					if(Map[yy][xx].compareTo(" ") == 0 && Map[yy][xx].compareTo("X") != 0 && (x.visited != 1) || Map[yy][xx].compareTo("G") == 0)
					{
						boolean on = true;
						for(Node I: q)
						{
						if (I.column == xx && I.row == yy)
						{
						on = false;
						}
						
						}
						if (on == true)
						{
						Node Neighbor = new Node(yy + " " + xx);
						Neighbor.column = xx;
						Neighbor.row = yy;
						Neighbor.cameFrom = x;
						q.add(Neighbor);
						}
						on = true;					
					}
				}
				
			}
		}
			x.visited = 1;
			int counter = 0;
			for(Node temp: q)
			{
				if (temp.visited == 1)
					{
					counter++;					
					if (q.size() == counter)
						{
						return false;
					}
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
