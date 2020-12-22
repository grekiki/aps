import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

class I1{
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
public class Naloga1{
	static PrintWriter out=new PrintWriter(System.out);
	static int[] dist;
	static int[] cost;
	static int len;
	static int tank;
	static int postaje;
	static int[] dp;
	static int[] best;
	/**
	 * Stategija: DP. Stanje: stojimo povsem polni na crpalki p, in nas zanima cena do cilja
	 * Ce smo blizu cilja(prvi if) potem je cena ocitno 0.
	 * Drugace moramo pogledati na kateri crpalki polnimo naslednjic. 
	 * Ce se odlocimo polniti na crpalki i, je cena enaka razdalji od 
	 * nas do crpalke * cena na crpalki + cena od tiste crpalke do cilja.
	 * Hkrati si se shranjujemo katero crpalko smo izbrali, da lahko na koncu poberemo 
	 * optimalno pot
	 * @param p
	 * @return cena potovanja od crpalke p do kona.
	 */
	public static int solve(int p){
		if(dist[p]+tank>=len){
			dp[p]=0;
			return 0;
		}
		if(dp[p]!=-1){
			return dp[p];
		}
		int best_score=Integer.MAX_VALUE;
		for(int i=p+1;i<=postaje;i++){
			if(dist[i]<=dist[p]+tank){
				int cena=(dist[i]-dist[p])*cost[i]+solve(i);
				if(cena<=best_score){
					best_score=Math.min(best_score,cena);
					best[p]=i;
				}
			}else{
				break;
			}
		}
		dp[p]=best_score;
		return best_score;
	}
	public static void main(String[] args) throws Exception{
		System.out.println("Working");
		if(args.length>0){
			I1.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		len=I1.readInt();
		tank=I1.readInt();
		postaje=I1.readInt();
		dist=new int[1+postaje];
		cost=new int[1+postaje];
		for(int i=1;i<=postaje;i++){
			I1.readInt();
			dist[i]=I1.readInt()+dist[i-1];
			cost[i]=I1.readInt();
		}
		dp=new int[postaje+1];
		best=new int[postaje+1];
		Arrays.fill(dp,-1);
		Arrays.fill(best,-1);
		solve(0);
		int curr=0;
		//Poberemo optimalno pot
		StringBuilder output=new StringBuilder("");
		while(best[curr]!=-1) {
			output.append(best[curr]+",");
			curr=best[curr];
		}
		out.println(output.toString().substring(0,output.length()-1));
		out.close();

	}

}
