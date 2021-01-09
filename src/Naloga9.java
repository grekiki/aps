import java.io.*;
import java.util.*;
//Tole je razred za obdelavo vhodnih podatkov
class I{
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
/**
 * Node v drevesu
 *
 */
class Node9{
	public static String subx="X";
	char c;
	ArrayList<Node9> ch;
	Node9(){
		ch=new ArrayList<Node9>();
	}
	public static ArrayList<Node9> match;
	/**
	 * Ali je "v" v this?
	 */
	public boolean matches(Node9 v) {
		if(c!=v.c && v.c!='X') {
			return false;
		}
		if(v.ch.size()==0) {
			if(v.c=='X') {
				match.add(this);
			}
			return true;
		}
		if(ch.size()!=v.ch.size()) {
			return false;
		}
		for(int i=0;i<ch.size();i++) {
			if(!ch.get(i).matches(v.ch.get(i))) {
				return false;
			}
		}
		return true;
	}
	public String toString() {
		String s=c+"";
		for(var c:ch) {
			if(c.c=='X') {
				s+=","+subx;
			}else {
				s+=","+c.toString();
			}
		}
		return s;
	}
}
class Naloga9{
	static PrintWriter out=new PrintWriter(System.out);
	public static Node9[] parseTree() throws Exception {
		int nV=I.readInt();
		Node9[] drevo=new Node9[nV];
		for(int i=0;i<nV;i++) {
			drevo[i]=new Node9();
		}
		for(int i=0;i<nV;i++) {
			int id=I.readInt()-1;
			char c=I.readString().charAt(0);
			drevo[id].c=c;
			while(I.st.hasMoreElements()) {
				int childId=I.readInt()-1;
				drevo[id].ch.add(drevo[childId]);
			}
		}
		return drevo;
	}
	/**
	 * Poiscimo najvecji replacement za X
	 */
	public static Node9 findLargest(ArrayList<Node9> a) {
		if(a.size()==0) {
			return null;
		}
		char c=a.get(0).c;
		int min=a.get(0).ch.size();
		for(Node9 n:a) {
			if(n.c!=c) {
				return null;
			}
		}
		for(Node9 n:a) {
			if(n.ch.size()!=min) {
				Node9 nn=new Node9();
				nn.c=c;
				return nn;
			}
		}
		Node9 n=new Node9();
		n.c=c;
		for(int i=0;i<min;i++) {
			ArrayList<Node9> b=new ArrayList<Node9>();
			for(Node9 nn:a) {
				b.add(nn.ch.get(i));
			}
			Node9 nn=findLargest(b);
			if(nn==null) {
				Node9 nnn=new Node9();
				nnn.c=c;
				return nnn;
			}
			n.ch.add(nn);
		}
		return n;
	}
	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		//Poberemo vzorec in drevo kot seznam vozlisc
		Node9[] vzorec=parseTree();
		Node9[] drevo=parseTree();
		String longest="";
		Node9 root_vzorca=vzorec[0];
		for(Node9 n:drevo) {
			Node9.match=new ArrayList<Node9>();
			if(n.matches(root_vzorca)) {
				ArrayList<Node9> nodes = Node9.match;
				Node9 ans=findLargest(nodes);
				if(ans==null) {
					continue;
				}
				Node9.subx=ans.toString();
				if(root_vzorca.toString().length()>longest.length()) {
					longest=root_vzorca.toString();
				}
			}
		}
		out.println(longest);
		out.close();
	}

}












