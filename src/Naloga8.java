import java.io.*;
import java.util.*;
//Tole je razred za obdelavo vhodnih podatkov
class I8{
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
class Node{
	char c;
	ArrayList<Node> ch;
	Node(){
		ch=new ArrayList<Node>();
	}
	/**
	 * Ali je "v" v this?
	 */
	public boolean matches(Node v) {
		if(c!=v.c) {
			return false;
		}
		if(v.ch.size()==0) {
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
}
class Naloga8{
	static PrintWriter out=new PrintWriter(System.out);
	public static Node[] parseTree() throws Exception {
		int nV=I.readInt();
		Node[] drevo=new Node[nV];
		for(int i=0;i<nV;i++) {
			drevo[i]=new Node();
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
	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		//Poberemo vzorec in drevo kot seznam vozlisc
		Node[] vzorec=parseTree();
		Node[] drevo=parseTree();
		
		Node root_vzorca=vzorec[0];
		int count=0;
		for(Node n:drevo) {
			if(n.matches(root_vzorca)) {
				count++;
			}
		}
		out.println(count);
		out.close();
	}

}












