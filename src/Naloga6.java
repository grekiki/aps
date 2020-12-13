import java.io.*;
import java.util.*;
/**
 * Povezava iz s->d dolzine w
 * 
 * @author Gregor
 *
 */
class conn{
	int s;
	int d;
	int w;
	conn(int s,int d,int w){
		this.s=s;
		this.d=d;
		this.w=w;
	}
	@Override public String toString(){
		return s+"->"+d+": "+w;
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
	ArrayList<conn>[] E;
	graf(int n){
		this.n=n;
		E=new ArrayList[n];
		for(int i=0;i<n;i++){
			E[i]=new ArrayList<conn>();
		}
	}
	@Override public String toString(){
		String s="";
		for(var e:E){
			s+=e.toString()+"\n";
		}
		return s;
	}

	public long[] solve(int pos,int range){
		//Dijkstra, nic zanimivega
		long[] dist=new long[n];
		Arrays.fill(dist,Long.MAX_VALUE/2);
		PriorityQueue<Integer> dij=new PriorityQueue<Integer>(new Comparator<Integer>(){
			public int compare(Integer a,Integer b){
				return Long.compare(dist[a],dist[b]);
			}
		});
		for(int i=0;i<range;i++){
			dist[pos+i]=0;
			dij.add(pos+i);
		}
		while(!dij.isEmpty()){
			int p=dij.poll();
			for(conn e:E[p]){
				if(dist[e.d]>dist[p]+e.w){
					dist[e.d]=dist[p]+e.w;
					dij.add(e.d);
				}
			}
		}
		return dist;
	}
}

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

class Naloga6{
	static PrintWriter out=new PrintWriter(System.out);

	public static void main(String[] args) throws Exception{
		if(args.length>0){
			I.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
		int cities=I.readInt();
		int E=I.readInt();
		int S=I.readInt();

		int s=I.readInt();
		int d=I.readInt();

		graf g=new graf(cities);
		for(int i=0;i<E;i++){
			int l=I.readInt();
			int r=I.readInt();
			int w=I.readInt();
			g.E[l].add(new conn(l,r,w));
			g.E[r].add(new conn(r,l,w));
		}
		int[] col=new int[cities];
		for(int i=1;i<=S;i++){
			int t=I.readInt();
			for(int j=0;j<t;j++){
				col[I.readInt()]=i;
			}
		}
//		System.out.println(g);
//		System.out.println(Arrays.toString(col));
		//Do sedaj smo samo pobrali vhodne podatke in jih shranili v navaden graf in v seznam "barv" tock(col). 
		//Za vsako tocko pogledamo iz katerih barv lahko pridemo vanjo. Nato jo razbijemo na vse te moznosti.(to je dobro narisat) 
		//S tem dobimo malo vecji usmerjen graf, na katerem lahko potem brez komplikacij pozenemo Dijkstro

		ArrayList<Integer>[] barve=new ArrayList[cities];
		int size=0;
		int[] pos=new int[cities];//Pozicije originalnih tock v novem grafu
		int[] len=new int[cities];//Na koliko podtock razbijemo eno tocko
		ArrayList<String> names=new ArrayList<String>();//Imena tock, za debugging
		for(int i=0;i<cities;i++){
			HashSet<Integer> b=new HashSet<Integer>();
			for(conn e:g.E[i]){
				b.add(col[e.d]);
			}
			barve[i]=new ArrayList<Integer>();
			barve[i].addAll(b);
			Collections.sort(barve[i]);
			for(int j:barve[i]){
				names.add(j+"^"+i);
			}
			pos[i]=size;
			len[i]=barve[i].size();
			size+=barve[i].size();
//			System.out.println(i+" "+barve[i]);
		}
//		System.out.println(size);
//		System.out.println(names);
//		System.out.println();
		//Naredimo usmerjen graf pravilne velikosti
		graf g2=new graf(size);
		for(int i=0;i<cities;i++){
			int init=pos[i];
			for(int j=0;j<len[i];j++){
				int curr=init+j;
				int prev_col=barve[i].get(j);//Iz tukaj smo prisli
				//Gremo po vseh sosedih
				for(conn c:g.E[i]){
					int r=c.d;
					if(col[r]==prev_col){
						continue;
					}
					for(int k=0;k<len[r];k++){
						int this_col=barve[r].get(k);
						//input_col od soseda mora biti enak nasi barvi
						if(this_col==col[i]){
							g2.E[curr].add(new conn(curr,pos[r]+k,c.w));
						}
					}
				}
			}
		}

		//Natisnemo nov graf
//		for(int i=0;i<size;i++){
//			System.out.print(names.get(i)+": ");
//			for(conn j:g2.E[i]){
//				System.out.print(names.get(j.d)+" "+j.w+", ");
//			}
//			System.out.println();
//		}
		//Vse do tukaj bi moralo biti zelo hitro. Recimo 100ms, ker smo do sedaj le preprocesirali podatke. Naslednja vrstica pozene dijkstro
//		System.out.println(Arrays.toString(g2.solve(pos[s],len[s])));
//		System.out.println(names);
		long[] dist=g2.solve(pos[s],len[s]);
		long best=Long.MAX_VALUE/2;
		for(int i=0;i<len[d];i++){
			best=Math.min(best,dist[pos[d]+i]);
		}
		out.println(best);
		out.close();
	}

}
