import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class I2{
	static BufferedReader in;
	static StringTokenizer st;
	public static String nextToken() throws Exception{
		if(in==null){
			in=new BufferedReader(new FileReader(new File("input.txt")));
		}
		while(st==null||!st.hasMoreTokens()){
			st=new StringTokenizer(in.readLine(),"[ ,:]");
		}
		return st.nextToken();
	}
	public static int[] readInts() throws Exception{
		if(in==null){
			in=new BufferedReader(new FileReader(new File("input.txt")));
		}
		if(st!=null&&st.hasMoreTokens()){
			System.out.println("Parsing warning in readInts()");
		}
		String s=in.readLine().replaceAll("[^0-9 \\-]","");
		StringTokenizer st=new StringTokenizer(s);
		int[] q=new int[st.countTokens()];
		for(int i=0;i<q.length;i++){
			q[i]=Integer.parseInt(st.nextToken());
		}
		return q;
	}
	public static int readInt() throws Exception{
		return Integer.parseInt(nextToken());
	}
	public static long readLong() throws Exception{
		return Long.parseLong(nextToken());
	}
	public static double readDouble() throws Exception{
		return Double.parseDouble(nextToken());
	}
	public static String readString() throws Exception{
		return nextToken();
	}
	public static String readLine() throws Exception{
		if(in==null){
			in=new BufferedReader(new FileReader(new File("input.txt")));
		}
		return in.readLine();
	}
	public static boolean hasNext() throws Exception{
		if(in==null){
			in=new BufferedReader(new FileReader(new File("input.txt")));
		}
		if(st!=null&&st.hasMoreTokens()){
			return true;
		}
		if(in.ready()){
			return true;
		}
		return false;
	}
	public static void reset() throws Exception{
		in=new BufferedReader(new FileReader(new File("input.txt")));
	}
	public static void reset(String filename) throws Exception{
		in=new BufferedReader(new FileReader(new File(filename)));
	}
	public static void reset(int p) throws Exception{
		in=new BufferedReader(new FileReader(new File("input"+p+".txt")));
	}
}
class tocka{
	int x,y;
	tocka(int a,int b){
		x=a;
		y=b;
	}

	@Override public String toString(){
		return "tocka [p="+x+", q="+y+"]";
	}

}
public class Naloga2{
	static int[]px=new int[100];
	static int[]py=new int[100];
	static int best=0;
	static String aans=null;
	static tocka start=null;
	static PrintWriter out=new PrintWriter(System.out);
	public static void print(boolean[][] q){
		for(int j=0;j<q[0].length;j++){
			for(int i=0;i<q.length;i++){
				System.out.print(q[i][j]?"#":".");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static int solve(boolean[][] q){
		int h=q[0].length;
		int w=q.length;
		int longest=0;
		for(int x=0;x<w;x++){
			for(int y=0;y<h;y++){
				if(q[x][y]){
					//Rekurzija pa to
					int t=solve(q,x,y,new boolean[w][h],0);
					if(t>longest){
						longest=t;
					}
				}
			}
		}
		return longest;
	}

	public static int solve(boolean[][] q,int x,int y,boolean[][] done,int dist){
		done[x][y]=true;
		px[dist]=x;
		py[dist]=y;
		int h=q[0].length;
		int w=q.length;
		int[] dx={1,0,-1,0};
		int[] dy={0,-1,0,1};
		int ans=dist;
		boolean worked=false;
		for(int d=0;d<4;d++){
			int x2=x+dx[d];
			int y2=y+dy[d];
			if(x2>=0&&y2>=0&&x2<w&&y2<h){
				if(q[x2][y2]&&!done[x2][y2]){
					worked=true;
					int t=solve(q,x2,y2,done,dist+1);
					ans=Math.max(ans,t);
				}
			}
		}
		if(!worked&&ans>best) {
			best=ans;
			start=new tocka(px[0],py[0]);
			StringBuilder resp=new StringBuilder("");
			for(int i=1;i<=best;i++) {
				if(px[i]==px[i-1]+1) {
					resp.append("DESNO,");
				}
				if(px[i]==px[i-1]-1) {
					resp.append("LEVO,");
				}
				if(py[i]==py[i-1]+1) {
					resp.append("DOL,");
				}
				if(py[i]==py[i-1]-1) {
					resp.append("GOR,");
				}
			}
			aans=resp.toString().substring(0,resp.length()-1);
		}
		done[x][y]=false;
		return ans;

	}
	public static void main(String[] args) throws Exception{
//		System.out.println("Working");
		if(args.length>0){
			I2.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
//		I.reset("T1.2\\I2_10.txt");
		int h=I2.readInt();
		int w=I2.readInt();
		int[][] grid=new int[w][h];
		int p=0;
		for(int y=0;y<h;y++){
			String s=I2.readLine().replace(",","");
			for(int x=0;x<w;x++){
				grid[x][y]=s.charAt(x)-'a';
			}
		}
		int longest=0;
		boolean[][] done=new boolean[w][h];
		boolean[][] solve=new boolean[w][h];
		for(int y=0;y<h;y++){
			for(int x=0;x<w;x++){
				if(!done[x][y]){
					for(int i=0;i<w;i++){
						Arrays.fill(solve[i],false);
					}
					tocka init=new tocka(x,y);
					done[x][y]=true;
					Queue<tocka> bfs=new LinkedList<tocka>();
					bfs.add(init);
					int[] dx={1,0,-1,0};
					int[] dy={0,-1,0,1};
					int count=0;
					while(!bfs.isEmpty()){
						tocka t=bfs.poll();
						count++;
						solve[t.x][t.y]=true;
						for(int d=0;d<4;d++){
							int x2=t.x+dx[d];
							int y2=t.y+dy[d];
							if(0<=x2&&x2<w&&0<=y2&&y2<h){
								if(!done[x2][y2]&&grid[t.x][t.y]==grid[x2][y2]){//PROBABLY NEED SOME TEST
									bfs.add(new tocka(x2,y2));
									done[x2][y2]=true;
								}
							}
						}
					}
					if(count>longest){
//						print(solve);
						int t=solve(solve);
						longest=Math.max(longest,t);
					}
				}
			}
		}

		out.println(start.y+","+start.x);
		out.println(aans);
		out.close();
	}

}