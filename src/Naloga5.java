import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

class I5{
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
class stanje{
	public String[] q;
	int visina;
	stanje(String[] s,int hh){
		q=s;
		visina=hh;
	}
	@Override public int hashCode(){
		final int prime=31;
		int result=1;
		result=prime*result+Arrays.hashCode(q);
		return result;
	}
	@Override public boolean equals(Object obj){
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		stanje other=(stanje)obj;
		return Arrays.equals(q,other.q);
	}
	@Override public String toString(){
		StringBuilder sb=new StringBuilder("\n");
		for(int i=0;i<visina;i++){
			for(String s:q){
				sb.append(visina-1-i<s.length()?s.charAt(visina-1-i):" ");
			}
			sb.append("\n");
		}
		sb.append("-".repeat(q.length));
		sb.append("\n");
		return sb.toString();
	}

}
public class Naloga5{
	static PrintWriter out=new PrintWriter(System.out);
	static int sirina;
	static int visina;
	static stanje cilj;
	static HashMap<stanje,Integer> dp=new HashMap<stanje,Integer>();
	static int best=100000;
	static int checks=0;
	static ArrayList<stanje> path;
	public static int solve(stanje start,char prev_moved,int rem){
		if(start.equals(cilj)){
			return 0;
		}
		if(rem==0){
			return best;
		}
		if(rem<0){
			System.out.println("Napaka");
			return best;
		}
		if(dp.containsKey(start)){
			return dp.get(start);
		}
		if(path.contains(start)){
			return 10000000;
		}
		checks++;
		System.out.println("Starting");
		System.out.println(start);
		for(int a=0;a<sirina;a++){
			if(start.q[a].length()>0){
				char option=start.q[a].charAt(start.q[a].length()-1);
				for(int b=0;b<sirina;b++){
					if(b!=a&&cilj.q[b].contains(option+"")&&cilj.q[b].indexOf(option)==start.q[b].length()&&start.q[b].equals(cilj.q[b].substring(0,start.q[b].length()))){
						String[] q2=start.q.clone();
						q2[b]+=start.q[a].charAt(start.q[a].length()-1);
						q2[a]=start.q[a].substring(0,start.q[a].length()-1);
						stanje s=new stanje(q2,visina);
						path.add(start);
						System.out.println("Cutting");
						int t=1+solve(s,q2[b].charAt(q2[b].length()-1),rem-1);
						path.remove(path.size()-1);
						dp.put(start,t);
						System.out.println("Shortcutting with score: "+t);
						System.out.println(start);
						return t;
					}
				}
			}
		}
		int len=rem;
		boolean found=false;
		for(int a=0;a<sirina;a++){
			for(int b=0;b<sirina;b++){
				if(a==b){
					continue;
				}
				if(start.q[b].length()==visina){
					continue;
				}
				if(start.q[a].length()==0){
					continue;
				}
				//Ne premakni ce je polje ze na cilju
				int ph=start.q[a].length()-1;
				if(cilj.q[a].length()>ph&&cilj.q[a].charAt(ph)==start.q[a].charAt(ph)){
					continue;
				}
				String[] q2=start.q.clone();
				q2[b]+=start.q[a].charAt(start.q[a].length()-1);
				q2[a]=start.q[a].substring(0,start.q[a].length()-1);
				stanje s=new stanje(q2,visina);
				path.add(start);
				int t=1+solve(s,q2[b].charAt(q2[b].length()-1),len-1);
				path.remove(path.size()-1);
				if(t<=len){
					len=Math.min(len,t);
					found=true;
				}
			}
		}
		if(!found) {
			len=best;
		}
		dp.put(start,len);
		System.out.println("Finishing with score: "+len);
		System.out.println(start);

		return len;
	}
	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I5.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		sirina=I5.readInt();
		visina=I5.readInt();
		stanje start=parse_stanje(sirina);
		cilj=parse_stanje(sirina);
		System.out.println(start);
		System.out.println(cilj);
		path=new ArrayList<stanje>();
		int t=solve(start,' ',1000000);
		System.out.println(t);
		System.out.println(checks);

	}
	private static stanje parse_stanje(int sirina) throws Exception{
		String[] q=new String[sirina];
		for(int i=0;i<sirina;i++){
			q[i]=I5.readLine().substring(2).replace(",","");
//			q[i]+=" ".repeat(visina-q[i].length());
		}
		return new stanje(q,visina);
	}

}
