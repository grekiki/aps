import java.io.*;
import java.util.*;
//Tole je razred za obdelavo vhodnih podatkov
class I10{
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

class edge2{
	int l,r,cost;

	public edge2(int l, int r, int cost) {
		this.l = l;
		this.r = r;
		this.cost = cost;
	}
	
}
class Naloga10{
	static PrintWriter out=new PrintWriter(System.out);
	
	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I10.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		ArrayList<edge2>[]adj=new ArrayList[10000];
		for(int i=0;i<adj.length;i++) {
			adj[i]=new ArrayList<edge2>();
		}
		int E=I.readInt();
		for(int i=0;i<E;i++) {
			int pl=I10.readInt();
			int pr=I10.readInt();
			int c=I10.readInt();
			adj[pl].add(new edge2(pl,pr,c));
		}
		int start=I10.readInt();
		int end=I10.readInt();
		int[]h=new int[100000];
		int inf=Integer.MAX_VALUE/2;
		ArrayList<Integer> pvisits=null;
		while(true) {
			ArrayList<Integer> visits=new ArrayList<Integer>();
			HashSet<Integer> visitsSet=new HashSet<Integer>();
			boolean worked=false;
			visits.add(start);
			visitsSet.add(start);
			int curr=start;
			while(curr!=end) {
				if(adj[curr].size()==0) {
					h[curr]=inf+1;
					break;
				}
				int bestScore=inf;
				int bestId=-1;
				for(edge2 e:adj[curr]) {
					if(visitsSet.contains(e.r)) {
						continue;
					}
					int nodeScore=e.cost+h[e.r];
					if(nodeScore<bestScore) {
						bestScore=nodeScore;
						bestId=e.r;
					}else if(nodeScore==bestScore&&e.r<bestId) {
						bestId=e.r;
					}
				}
				if(bestId==-1){
					break;
				}
				if(h[curr]<bestScore) {
					worked=true;
					h[curr]=bestScore;
				}
				curr=bestId;
				visits.add(curr);
				visitsSet.add(curr);
			}
			out.println(visits.toString().replaceAll("[\\[\\]]","").replace(" ",""));
			if(!worked&&visits.equals(pvisits)) {
				break;
			}
			pvisits=visits;
		}
		out.close();
	}
	
}












