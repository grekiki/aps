import java.io.*;
import java.util.*;

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

class Naloga8{
	static PrintWriter out=new PrintWriter(System.out);

	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I8.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		
	}

}
