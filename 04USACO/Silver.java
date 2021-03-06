import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class Silver{
    int[][] map;
    int[][] temp;
    int[][] startend;
    int time;
    int startx, starty, endx, endy;

    public Silver(String filename){
	readFile(filename);
       
    }
    
    public void readFile(String filename){
        File instructions = new File(filename);
        try{
            Scanner lines = new Scanner(instructions);
            
       
            int rows = lines.nextInt();
            int cols = lines.nextInt();
            map = new int[rows][cols];
            temp = new int[rows][cols];
 
            time = lines.nextInt();
            
            for(int i=0; i<rows; i++){
                String line = lines.next();
                for(int j=0; j<cols; j++){
                    if(line.charAt(j)=='*'){
			map[i][j]= -1;
		    }else{
			map[i][j]=0;
		    }
  
      
                }
            }
            //System.out.println(Arrays.deepToString(map));
            
            startx = Integer.parseInt(lines.next())-1;
            starty = Integer.parseInt(lines.next())-1;
            endx = Integer.parseInt(lines.next())-1;
            endy = Integer.parseInt(lines.next())-1;
            
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    public boolean bound(int row, int col){
        return(row >= 0 && row < map.length &&
               col >= 0 && col < map[0].length && map[row][col] != -1);
    }
    
    public int travel(){
        //start by copying old map
       for(int r=0; r < map.length; r++){
	    for(int c=0; c < map[0].length; c++){
		temp[r][c]=map[r][c];
	    }
	}
       map[startx][starty]=1;

       //updating old map
        for(int i=0; i<time; i++){
            
            for(int r=0; r<map.length; r++){
                for(int c=0; c<map[0].length; c++){
                    if(temp[r][c]!=-1){
                        temp[r][c]=0;
                    }
                }
            }

            //recalculating values for old map
            for(int r=0; r<map.length; r++){
                for(int c=0; c<map[0].length; c++){
                    if(map[r][c]!=-1){
                        int total = 0;
                        if(bound(r-1,c)){
                            total += map[r-1][c];
                        }
                        if(bound(r+1,c)){
                            total += map[r+1][c];
                        }
                        if(bound(r,c-1)){
                            total += map[r][c-1];
                        }
                        if(bound(r,c+1)){
                            total += map[r][c+1];
                        }
                        temp[r][c] = total;
                    }

                }

            }
            for(int r = 0; r < map.length; r++){
                for(int c = 0; c < map[0].length; c++){
                    map[r][c]=temp[r][c];
                }
            }
        }
        return map[endx][endy];
       
    }
    
    public void print(int[][] m){
	String print="";
	for(int r=0; r<m.length; r++){
	    for(int c=0; c<m[r].length; c++){
		if(m[r][c]<0){
		    print+=m[r][c]+"  ";
		}else{
                    print+=m[r][c]+"   ";
                }
	    }
	    print+="\n";
	}
	System.out.println(print);
    }

    public void nameSolution(){
        System.out.println(travel()+", 6, Gao, JiaQi");
    }
        
    public static void main(String[]args){
        Silver s = new Silver("test1.in");
        //Silver s = new Silver("test2.in");
        s.nameSolution();
    }
        
    

}
