package assignment09;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.*;

public class junitTest {

	
	static long startTime = 0;
	static long stopTime = 0;
	static long aveTime = 0;
	
	@Test
	public void straightLineOppositeCornersTimingTest() {
				
		startTime = System.nanoTime();
		PathFinder.solveMaze("randomMaze.txt", "randomMazeOutput.txt");
		stopTime = System.nanoTime();
		aveTime = stopTime - startTime;
		System.out.println("Corners Test " + aveTime);		
	}

	@Test
	public void bottomLinefarTimingTest() {		
		startTime = System.nanoTime();
		PathFinder.solveMaze("randomMaze3.txt", "randomMaze3Output.txt");
		stopTime = System.nanoTime();
		aveTime = stopTime - startTime;
		System.out.println("bottom line far test " + aveTime);		
	}
	
	@Test
	public void nextToEachOthertimingTest() {		
		startTime = System.nanoTime();
		PathFinder.solveMaze("randomMaze4.txt", "randomMaze4Output.txt");
		stopTime = System.nanoTime();
		aveTime = stopTime - startTime;
		System.out.println("Next To each other test " + aveTime);		
	}
	
	@Test(expected = Exception.class) 
	public void FileNotFoundCheck() {				
		PathFinder.solveMaze("nofile.txt", "nofileoutput.txt");		
	}
	
	@Test
	public void countingTheDotsToTheExampleSolution() {		

		int counter1 = 0;
		int counter2 = 0;
		
		PathFinder.solveMaze("randomMaze.txt", "randomMazeOutput.txt");
		File text1 = new File("randomMazeOutput.txt");	
		File text2 = new File("randomMazeSol.txt");	


		try {
			Scanner s = new Scanner(text1);
			Scanner n = new Scanner(text2);
			
			String nex = s.nextLine();
			String nex2 = n.nextLine();
			
			while(s.hasNextLine())
			{
			nex = s.nextLine();
			for (int i = 0; i < nex.length(); i++)
			{
				char c = nex.charAt(i);
				String d = Character.toString(c);
				if (d.compareTo(".") == 0)
				{
					counter1++;
				}
			}
			}	
			s.close();
						
			while(n.hasNextLine())
			{
			nex2 = n.nextLine();
			for (int i = 0; i < nex2.length(); i++)
			{
				char e = nex2.charAt(i);
				String f = Character.toString(e);
				if (f.compareTo(".") == 0)
				{
					counter2++;
				}
			}
			}	
			n.close();
			assertEquals(true, counter1 == counter2);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		
		
		
	}
	



}
