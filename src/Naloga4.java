import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
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
class prazno{
	int pos;
	int len;
	prazno(int a,int b){
		pos=a;
		len=b;
	}
	@Override public String toString(){
		return len+": "+pos+"-"+(pos+len);
	}
}
public class Naloga4{
	static int[] px=new int[100];
	static int[] py=new int[100];

	static PrintWriter out=new PrintWriter(System.out);
	public static void main(String[] args) throws Exception{
//		System.out.println("Working");
		if(args.length>0){
			I.reset(args[0]);
			out=new PrintWriter(new FileWriter(args[1]));
		}
//		I.reset("T1.2\\I2_10.txt");
		int commands=I.readInt()-1;
		int len=Integer.parseInt(I.readLine().substring(2));
		//f(id)-> pos, len
		int[] pos=new int[1000000];
		int[] slen=new int[1000000];
		Arrays.fill(pos,-1);
		Arrays.fill(slen,-1);
		//f(edge)->id
		int[] start=new int[len+1];
		int[] end=new int[len+1];
		Arrays.fill(start,-1);
		Arrays.fill(end,-1);
		ArrayList<prazno> alloc_space=new ArrayList<prazno>();
		alloc_space.add(new prazno(0,len));
		for(int i=0;i<commands;i++){
			String s=I.readLine();
			if(s.charAt(0)=='a'){
				String t=s.substring(2).replace(","," ");
				int alen=Integer.parseInt(t.split(" ")[0]);
				int aid=Integer.parseInt(t.split(" ")[1]);
				if(pos[aid]!=-1){
					continue;
				}
				prazno best=null;
				int bpos=10000000;
				for(prazno p:alloc_space){
					if(p.len>=alen){
						if(p.pos<bpos){
							best=p;
							bpos=p.pos;
						}
					}
				}
				if(best!=null){
					pos[aid]=best.pos;
					slen[aid]=alen;
					start[best.pos]=aid;
					end[best.pos+alen]=aid;
					best.pos+=alen;
					best.len-=alen;
					if(best.len==0) {
						alloc_space.remove(best);
					}
				}
			}else if(s.charAt(0)=='f'){
				int aid=Integer.parseInt(s.substring(2));
				int pl=pos[aid];
				int pr=pos[aid]+slen[aid];
				if(pl==-1){
					continue;
				}
				//brisanje
				pos[aid]=-1;
				slen[aid]=-1;
				start[pl]=-1;
				end[pr]=-1;
				prazno p=null;
				//A je na levi prazno ali blok?
				if(end[pl]!=-1||pl==0){
					//Levo je en blok z id-jem end[pl], ali smo pa na robu
					//pl ostane enak
					p=new prazno(pl,pr-pl);
				}else{
					//gremo po praznih dokler ga ne najdemo
					boolean found=false;
					for(prazno pp:alloc_space){
						if(pp.pos+pp.len==pl){
							found=true;
							pp.len+=pr-pl;
							p=pp;
						}
					}
					alloc_space.remove(p);
					if(!found){
						System.out.println("Napaka2");
					}
				}
				//Prazno morda podaljsamo, ce na desni ni bloka
				if(start[pr]!=-1||pr==len){
					//Na desni je blok ali pa smo na koncu, ne naredimo nicesar
				}else{
					//Na desni je prazno, moramo ga mergati z levim praznim
					boolean found=false;
					for(int j=alloc_space.size()-1;j>=0;j--){
						prazno pp=alloc_space.get(j);
						if(pp.pos==pr){
							found=true;
							p.len+=pp.len;
							alloc_space.remove(pp);
						}
					}
					if(!found){
						System.out.println("Napaka3");
					}
				}
				alloc_space.add(p);
			}else if(s.charAt(0)=='d'){
				int count=Integer.parseInt(s.substring(2));
				//Poiscemo prvo prazno polje
				prazno best=null;
				int bpos=10000000;
				for(prazno p:alloc_space){
					if(p.pos<bpos){
						best=p;
						bpos=p.pos;
					}
				}
				//ce jih ni potem nimamo dela
				if(best==null){
					continue;
				}
				//ce je na koncu potem nimamo dela
				if(best.pos+best.len==len){
					continue;
				}
				//naredimo n korakov defragmentacije, belezimo prazne bloke za merge
				int p0=best.pos;
				int sumlen=best.len;
				for(int j=0;j<count;j++){
					int p=best.pos+sumlen;
					//ali smo konec?
					if(p==len){
						break;
					}
					//id bloka
					int id=start[p];
					int blen=slen[id];
					//premaknemo blok za sumlen
					start[p]=-1;
					end[p+blen]=-1;
					start[p-sumlen]=id;
					end[p-sumlen+blen]=id;
					pos[id]-=sumlen;
					//pogledamo ce smo naleteli na blok
					if(start[p+blen]!=-1|| p+blen==len){
						//ne mergamo
					}else{
						prazno del=null;
						for(prazno pp:alloc_space){
							if(pp.pos==p+blen){
								del=pp;
							}
						}
						if(del==null){
							System.out.println("Napaka3");
						}else{
							alloc_space.remove(del);
							sumlen+=del.len;
						}
					}
					best.pos+=blen;
				}
				best.len=sumlen;
			}
		}
		for(int i=0;i<len;i++){
			if(start[i]!=-1){
				int id=start[i];
				int pl=i;
				int pr=pl+slen[id];
				out.println(id+","+pl+","+(pr-1));
			}
		}
		out.close();
//		System.out.println("Prazna mesta");
//		System.out.println(alloc_space);
	}

}
