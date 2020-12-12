import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

class I{
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
	char[][] q;
	int[] h;
	static int visina;
	stanje(String[] qq){
		h=new int[qq.length];
		q=new char[qq.length][visina];
		for(int i=0;i<h.length;i++){
			h[i]=qq[i].length();
			for(int j=0;j<h[i];j++){
				q[i][j]=qq[i].charAt(j);
			}
			for(int j=h[i];j<visina;j++){
				q[i][j]=' ';
			}
		}
	}
	stanje(char[][] qq,int[] hh){
		q=qq;
		h=hh;
	}
	@Override public String toString(){
		StringBuilder sb=new StringBuilder("\n");
		for(int i=0;i<visina;i++){
			for(int j=0;j<h.length;j++){
				sb.append(q[j][visina-1-i]);
			}
			sb.append("\n");
		}
		sb.append("-".repeat(h.length));
		sb.append("\n");
		return sb.toString();
	}
	@Override public int hashCode(){
		final int prime=31;
		int result=1;
		result=prime*result+Arrays.hashCode(h);
		result=prime*result+Arrays.deepHashCode(q);
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
		return Arrays.equals(h,other.h)&&Arrays.deepEquals(q,other.q);
	}
	stanje move(int l,int r){
		if(h[l]==0||h[r]==visina){
			return null;
		}
		char[][] s2=new char[q.length][visina];
		for(int i=0;i<q.length;i++){
			for(int j=0;j<visina;j++){
				s2[i][j]=q[i][j];
			}
		}
		int[] h2=new int[h.length];
		for(int i=0;i<h.length;i++){
			h2[i]=h[i];
		}
		s2[r][h[r]]=s2[l][h[l]-1];
		s2[l][h[l]-1]=' ';
		h2[r]++;
		h2[l]--;
		return new stanje(s2,h2);
	}

}
public class Naloga5{
	static PrintWriter out=new PrintWriter(System.out);
	final static int inf=100000000;

	static int sirina;
	static int visina;
	static stanje cilj;
	static HashMap<stanje,Integer> dp=new HashMap<stanje,Integer>();
	static HashSet<stanje> searching=new HashSet<stanje>();
	static ArrayList<stanje> root_path=new ArrayList<stanje>();
	public static int length(stanje s){
		if(s.equals(cilj)){
			return 0;
		}
		if(dp.containsKey(s)){
			return dp.get(s);
		}
		int depth=root_path.size();
		if(depth>10) {
			return inf;
		}
		root_path.add(s);
		tocka t=find_shortcut(s);
		if(t!=null){
//			System.out.println("shortcut iz ");
//			System.out.println(s);
//			System.out.println("v opcijo");
			stanje s2=s.move(t.x,t.y);
//			System.out.println(s2);
			if(root_path.contains(s2)){
//				System.out.println("Cutting already been to ");
//				System.out.println(s2);
				root_path.remove(root_path.size()-1);
				return inf;
			}
			int score=1+length(s2);
			dp.put(s,score);
			root_path.remove(root_path.size()-1);
			return score;
		}
//		System.out.println("Searching "+root_path.size());
//		System.out.println(s);
		int best=inf;
		for(int a=0;a<sirina;a++){
			for(int b=0;b<sirina;b++){
				if(a==b){
					continue;
				}
				stanje s2=s.move(a,b);
				if(s2==null){
					continue;
				}
				int sc=inf;
				if(dp.containsKey(s2)){
					sc=1+dp.get(s2);
				}else if(root_path.contains(s2)){
//					System.out.println("Cutting2 already seen");
//					System.out.println(s2);
					continue;
				}else{
					sc=1+length(s2);
				}
				if(sc<best){
					best=sc;
				}
			}
		}
		System.out.println("Got score "+best);
		System.out.println(s);
		if(best>=inf){
			System.out.print("");
			root_path.remove(root_path.size()-1);
			return inf;
		}
		if(best==8){
			System.out.print("");
		}
		dp.put(s,best);
		root_path.remove(root_path.size()-1);
		return best;
	}
	private static tocka find_shortcut(stanje s){
		tocka ans=null;
		for(int a=0;a<sirina;a++){
			if(s.h[a]>0){
				char option=s.q[a][s.h[a]-1];
				for(int b=0;b<sirina;b++){
					//Cilj mora biti enak nasi ideji, in cilj do tam se mora ujemati
					if(b!=a&&s.h[b]<visina&&cilj.q[b][s.h[b]]==option){
						boolean ok=true;
						for(int i=0;i<s.h[b];i++){
							if(s.q[b][i]!=cilj.q[b][i]){
								ok=false;
								break;
							}
						}
						if(ok){
							return new tocka(a,b);
						}
					}
				}
			}
		}
		return ans;
	}
	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		sirina=I.readInt();
		visina=I.readInt();
		stanje.visina=visina;
		stanje start=parse_stanje(sirina);
		cilj=parse_stanje(sirina);
		System.out.println(start);
		System.out.println(cilj);
		System.out.println("Starting to work");
		int t=length(start);
		System.out.println(t);

	}
	private static stanje parse_stanje(int sirina) throws Exception{
		String[] q=new String[sirina];
		for(int i=0;i<sirina;i++){
			q[i]=I.readLine().substring(2).replace(",","");
		}
		return new stanje(q);
	}

}
