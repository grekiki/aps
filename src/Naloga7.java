import java.io.*;
import java.util.*;
/**
 * Povezava med s in d
 * 
 * @author Gregor
 *
 */
class connection implements Comparable<connection>{
	int s,d;
	connection(int a,int b){
		s=Math.min(a,b);
		d=Math.max(a,b);
	}
	@Override public String toString(){
		return s+"--"+d;
	}
	@Override public int hashCode(){
		return Objects.hash(s,d);
	}
	@Override public boolean equals(Object obj){
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		connection other=(connection)obj;
		return d==other.d&&s==other.s;
	}
	@Override public int compareTo(connection o){
		return s-o.s;
	}
}
/**
 * Osnovna implementacija usmerjenega grafa. E[i] so vse povezave iz tocke i.
 * 
 * @author Gregor
 *
 */
class graf{
	int n;
	ArrayList<Integer>[] E;
	graf(int n){
		this.n=n;
		E=new ArrayList[n];
		for(int i=0;i<n;i++){
			E[i]=new ArrayList<Integer>();
		}
	}
	@Override public String toString(){
		String s="";
		for(var e:E){
			s+=e.toString()+"\n";
		}
		return s;
	}

	//BFS bi delal, ampak smo leni pa uporabimo Dijkstro iz naloge 6. Ce je program prepocasen, bo problem verjetno tukaj.
	ArrayList<Integer> solve(int pos,int end){
		long[] dist=new long[n];
		Arrays.fill(dist,Long.MAX_VALUE/2);
		int[] par=new int[n];
		Arrays.fill(par,-1);
		PriorityQueue<Integer> dij=new PriorityQueue<Integer>(new Comparator<Integer>(){
			public int compare(Integer a,Integer b){
				return Long.compare(dist[a],dist[b]);
			}
		});
		dist[pos]=0;
		dij.add(pos);
		while(!dij.isEmpty()){
			int p=dij.poll();
			for(int e:E[p]){
				if(dist[e]>dist[p]+1){
					dist[e]=dist[p]+1;
					par[e]=p;
					dij.add(e);
				}
			}
		}
		ArrayList<Integer> a=new ArrayList<Integer>();
		int p=end;
		if(par[p]==-1){
			return null;
		}
		while(p!=-1){
			a.add(p);
			p=par[p];
		}
		return a;
	}
	ArrayList<connection> bridges=new ArrayList<connection>();
	boolean[] done;
	int[] tin;
	int[] low;
	int time=0;
	//Poberemo https://cp-algorithms.com/graph/bridge-searching.html
	ArrayList<connection> find_bridges(){
		tin=new int[n];
		Arrays.fill(tin,-1);
		low=new int[n];
		Arrays.fill(low,-1);
		done=new boolean[n];
		for(int i=0;i<n;i++){
			if(!done[i]){
				dfs(i,-1);
			}
		}
		return bridges;
	}
	void dfs(int v,int p){
		done[v]=true;
		tin[v]=time;
		low[v]=time;
		time++;
		for(int to:E[v]){
			if(to==p)
				continue;
			if(done[to]){
				low[v]=Math.min(low[v],tin[to]);
			}else{
				dfs(to,v);
				low[v]=Math.min(low[v],low[to]);
				if(low[to]>tin[v]){
					bridges.add(new connection(v,to));
				}
			}
		}
	}
}

class I7{
	static BufferedReader in;
	static StringTokenizer st;
	static String nextToken() throws Exception{
		if(in==null){
			in=new BufferedReader(new FileReader(new File("input.txt")));
		}
		while(st==null||!st.hasMoreTokens()){
			st=new StringTokenizer(in.readLine(),"[ ,:]");
		}
		return st.nextToken();
	}
	static int[] readInts() throws Exception{
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
	static int readInt() throws Exception{
		return Integer.parseInt(nextToken());
	}
	static long readLong() throws Exception{
		return Long.parseLong(nextToken());
	}
	static double readDouble() throws Exception{
		return Double.parseDouble(nextToken());
	}
	static String readString() throws Exception{
		return nextToken();
	}
	static String readLine() throws Exception{
		if(in==null){
			in=new BufferedReader(new FileReader(new File("input.txt")));
		}
		return in.readLine();
	}
	static boolean hasNext() throws Exception{
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
	static void reset() throws Exception{
		in=new BufferedReader(new FileReader(new File("input.txt")));
	}
	static void reset(String filename) throws Exception{
		in=new BufferedReader(new FileReader(new File(filename)));
	}
	static void reset(int p) throws Exception{
		in=new BufferedReader(new FileReader(new File("input"+p+".txt")));
	}
}

class Naloga7{
	static PrintWriter out=new PrintWriter(System.out);

	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I7.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		int n=I7.readInt();
		int E=I7.readInt();
		graf g=new graf(n);
		int l=I7.readInt();
		int r=I7.readInt();
		for(int i=0;i<E;i++){
			int pl=I7.readInt();
			int pr=I7.readInt();
			g.E[pl].add(pr);
			g.E[pr].add(pl);
		}
//		System.out.println(g);
		//Poiscemo najkrajso pot med l in r
		ArrayList<Integer> path=g.solve(l,r);
		//Zacetna in koncna tocka nista povezani
		if(path==null){
			out.close();
			return;
		}
//		System.out.println(path);
//		System.out.println(g.find_bridges());
		//Poiscemo mostove v grafu
		HashSet<connection> hs=new HashSet<connection>();
		hs.addAll(g.find_bridges());
//		System.out.println(hs);
		//Izpisemo mostove ki so na najkrajsi poti(poljubna pot bi tudi delovala)
		ArrayList<connection> important=new ArrayList<connection>();
		for(int i=0;i<path.size()-1;i++){
			connection a=new connection(path.get(i),path.get(i+1));
			if(hs.contains(a)){
				important.add(a);
			}
		}
//		System.out.println(important);
		Collections.sort(important);
		for(connection c:important) {
			out.println(c.s+" "+c.d);
		}
		out.close();

	}

}
