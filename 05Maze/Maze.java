import java.util.*;
import java.io.*;

public class Maze{

    private char[][]maze;
    private int startx,starty;
    private boolean animate;

    /*Constructor loads a maze text file.
      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - locations that cannot be moved onto
      ' ' - locations that can be moved onto
      'E' - the location of the goal (only 1 per file)
      'S' - the location of the start(only 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found, print an error and exit the program.
    */

    public Maze(String filename, boolean ani){
        animate = ani;
        File instructions = new File(filename);
        try{
            Scanner lines = new Scanner(instructions);
	    int rows=1;

            String lineOne = lines.nextLine();
	    int cols = lineOne.length(); 
	    while(lines.hasNext()){
		String countLines = lines.nextLine();
		rows++; 
	    }
	    //System.out.println(rows);
	    //System.out.println(cols); 
	    
	    lines = new Scanner(instructions);
	    maze = new char[cols][rows];
	    Scanner copy = new Scanner(instructions);
	    
	    for(int i=0; i<rows; i++){
		String copyy=copy.nextLine();
		for(int j=0; j<cols; j++){
		    if(copyy.charAt(j)=='S'){
			startx=i;
			starty=j;
		    }
		    maze[j][i] = copyy.charAt(j);
		}
	    }
	    /*
	    System.out.println(startx);
	    System.out.println(starty);
            System.out.println(Arrays.deepToString(maze));
	    */
            
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        
    }


    /*Main Solve Function

      Things to note:
       When no S is contained in maze, print an error and return false.
    */
    public boolean solve(){
        if(startx < 0){
            System.out.println("No starting point 'S' found in maze.");
            return false;
        }else{
            maze[startx][starty] = ' ';
            return solve(startx,starty);
        }
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.
      The S is replaced with '@' but the 'E' is not.

      Postcondition:
        Returns true when the maze is solved,
        Returns false when the maze has no solution.

        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'

    */
    private boolean solve(int x, int y){
        if(animate){
            System.out.println(this);
            wait(20);
        }
	if(maze[x][y]=='E'){
	    return true;
	}
        if(posmove(x,y)){
            if(solve(x-1,y) || solve(x+1, y) ||
               solve(x, y-1) || solve(x, y+1)){
                return true;
            }else{
                maze[x][y] = '.';
                return false;
            }
               
        }
        
	//maze[x][y]='.';
	
	
        //COMPLETE SOLVE

        return false; //so it compiles
    }

    public boolean posmove(int x, int y){
	if(maze[x][y] != ' '){
            return false;
        }else{
            maze[x][y] = '@';
            return true;
        }
    }

    //FREE STUFF!!! *you should be aware of this*


    public void clearTerminal(){
        System.out.println(CLEAR_SCREEN);
    }


   public String toString(){
        int maxx = maze.length;
        int maxy = maze[0].length;
        String ans = "";
        if(animate){
            ans = "Solving a maze that is " + maxx + " by " + maxy + "\n";
        }
        for(int i = 0; i < maxx * maxy; i++){
            if(i % maxx == 0 && i != 0){
                ans += "\n";
            }
            char c =  maze[i % maxx][i / maxx];
            if(c == '#'){
                ans += color(38,47)+c;
            }else{
                ans += color(33,40)+c;
            }
        }
        return HIDE_CURSOR + go(0,0) + ans + "\n" + SHOW_CURSOR + color(37,40);
    }


    //MORE FREE STUFF!!! *you can ignore all of this*

    //Terminal keycodes to clear the terminal, or hide/show the cursor
    private static final String CLEAR_SCREEN =  "\033[2J";
    private static final String HIDE_CURSOR =  "\033[?25l";
    private static final String SHOW_CURSOR =  "\033[?25h";
    //terminal specific character to move the cursor
    private String go(int x,int y){
        return ("\033[" + x + ";" + y + "H");
    }

    private String color(int foreground,int background){
        return ("\033[0;" + foreground + ";" + background + "m");
    }

    private void wait(int millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
    }

    /*
    public static void main(String[]args){
	Maze m = new Maze("data1.dat", false);
        
	//System.out.println(Arrays.deepToString(m.maze));
	System.out.println(m.solve());
	System.out.println(m);
    }
    */



}
