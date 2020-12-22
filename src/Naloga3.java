import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Naloga3 {
	static PrintWriter out;

	public static void main(String[] args) throws Exception {
		String in = "C:\\Users\\Matevz\\Desktop\\APS1\\VhodNaloga3.txt";
		if (args.length > 0) {
			in = args[0];
		}
		I3.in = new BufferedReader(new FileReader(new File(in)));
		out = new PrintWriter(System.out);
		if (args.length > 1) {
			out = new PrintWriter(new FileWriter(args[1]));

		}
		int n, v, k, p;
		int count = 0;// steje kul stevilk je vseskup
		// Premebr string in pretvor v inte za te got ^
		// FIXI PRED ODDAJO
		n = I3.readInt();// stevilo vrstic
		v = I3.readInt();// zadna vrsta kamor smo dodal znak
		k = I3.readInt();// stevilo premikov pri kodiranju
		p = I3.readInt();// premik do nove vrstice

		Queue[] tabelaVrst = new Queue[n];
		for (int i = 0; i < n; i++) {
			tabelaVrst[i] = new Queue();
			String vrstica = I3.readLine();
			if(vrstica.equals("")) {
				continue;
			}
			String[] bibi = vrstica.split(",");
			// REVERSAJ V FOR ZANKI DA NEBO TREBA POL OBRACAT TABELE
			for (int j = bibi.length - 1; j >= 0; j--) {
				tabelaVrst[i].enqueue(Integer.parseInt(bibi[j]));
				count++;
			}
			//out.println(tabelaVrst[i]);

		}

		// treba napovnt tabelo s podatki
	//	out.println("n:" + n + " v:" + v + " k:" + k + " p:" + p);

		for (int i = 0; i < k; i++) {
			int temp = tabelaVrst[v].front();
			tabelaVrst[v].dequeue();
			int exLine = (v - temp) % n;
			if (exLine < 0) {
				exLine += n;
			}
			tabelaVrst[exLine].enqueue(temp);
			v = (exLine - p) % n;
			if(v<0) {
				v+=n;
			}
		}
	/*	for (int i = 0; i < n; i++) {
			out.println(tabelaVrst[i]);
		}*/
		Queue rezultat = new Queue();
		/*
		 * int zacetek; if (count % n == 0) { zacetek = n - 1; } else { zacetek = (count
		 * % n) - 1; }
		 */
		int[] elementiPerVrsta=new int[n];
		for(int i=0;i<n;i++) {
			elementiPerVrsta[i]=tabelaVrst[i].steviloElementov;
		}
		while(count!=0) {
			int max=-1;
			int vrsta=-1;
			for(int i=0;i<n;i++) {
				if(elementiPerVrsta[i]>=max) {
					max=elementiPerVrsta[i];
					vrsta=i;
				}
				
			}
			count--;
			rezultat.enqueue(tabelaVrst[vrsta].front());
			tabelaVrst[vrsta].dequeue();
			elementiPerVrsta[vrsta]=elementiPerVrsta[vrsta]-1;
		}

		//out.println(count);
	//	out.println(rezultat);
		// rezultat = rezultat.reverse();
	//	out.println(rezultat);
		// ^SAM SE OBRNT+DEKODERAT V CRKE

		// sam se dekoderat znake
		int stevilka;
		String dekoderan = "";
		while (!rezultat.empty()) {
			stevilka = rezultat.front();
			rezultat.dequeue();
			switch (stevilka) {
			case 0:
				dekoderan += 'A';
				break;
			case 1:
				dekoderan += 'B';
				break;
			case 2:
				dekoderan += 'C';
				break;
			case 3:
				dekoderan += 'È';
				break;
			case 4:
				dekoderan += 'D';
				break;
			case 5:
				dekoderan += 'E';
				break;
			case 6:
				dekoderan += 'F';
				break;
			case 7:
				dekoderan += 'G';
				break;
			case 8:
				dekoderan += 'H';
				break;
			case 9:
				dekoderan += 'I';
				break;
			case 10:
				dekoderan += 'J';
				break;
			case 11:
				dekoderan += 'K';
				break;
			case 12:
				dekoderan += 'L';
				break;
			case 13:
				dekoderan += 'M';
				break;
			case 14:
				dekoderan += 'N';
				break;
			case 15:
				dekoderan += 'O';
				break;
			case 16:
				dekoderan += 'P';
				break;
			case 17:
				dekoderan += 'R';
				break;
			case 18:
				dekoderan += 'S';
				break;
			case 19:
				dekoderan += 'Š';
				break;
			case 20:
				dekoderan += 'T';
				break;
			case 21:
				dekoderan += 'U';
				break;
			case 22:
				dekoderan += 'V';
				break;
			case 23:
				dekoderan += 'Z';
				break;
			case 24:
				dekoderan += 'Ž';
				break;
			case 25:
				dekoderan += 'a';
				break;
			case 26:
				dekoderan += 'b';
				break;
			case 27:
				dekoderan += 'c';
				break;
			case 28:
				dekoderan += 'è';
				break;
			case 29:
				dekoderan += 'd';
				break;
			case 30:
				dekoderan += 'e';
				break;
			case 31:
				dekoderan += 'f';
				break;
			case 32:
				dekoderan += 'g';
				break;
			case 33:
				dekoderan += 'h';
				break;
			case 34:
				dekoderan += 'i';
				break;
			case 35:
				dekoderan += 'j';
				break;
			case 36:
				dekoderan += 'k';
				break;
			case 37:
				dekoderan += 'l';
				break;
			case 38:
				dekoderan += 'm';
				break;
			case 39:
				dekoderan += 'n';
				break;
			case 40:
				dekoderan += 'o';
				break;
			case 41:
				dekoderan += 'p';
				break;
			case 42:
				dekoderan += 'r';
				break;
			case 43:
				dekoderan += 's';
				break;
			case 44:
				dekoderan += 'š';
				break;
			case 45:
				dekoderan += 't';
				break;
			case 46:
				dekoderan += 'u';
				break;
			case 47:
				dekoderan += 'v';
				break;
			case 48:
				dekoderan += 'z';
				break;
			case 49:
				dekoderan += 'ž';
				break;
			case 50:
				dekoderan += ' ';
				break;
			}
		}
	//	out.println(dekoderan);
		String pravilnaResitev="";
		for(int i=dekoderan.length()-1;i>=0;i--) {
			pravilnaResitev+=dekoderan.charAt(i);
		}
		out.println(pravilnaResitev);
		//out.println((0-p)%3);
		out.close();
	}

}

class Queue {
	QueueElement prvi;
	QueueElement zadn;
	int steviloElementov;

	@Override
	public String toString() {
		if (this.prvi == null) {
			return "queue je prazn";
		}
		QueueElement i = prvi;
		String izpis = "";
		do {
			if (i.next == null) {
				izpis += i.stevilka;
				return izpis;
			}
			izpis += i.stevilka + ",";
			i = i.next;
		} while (i != null);

		return izpis;
		// return "prvi:"+this.prvi.stevilka+" zadn"+this.zadn.stevilka;
	}

	public Queue() {
		prvi = null;
		zadn = null;
		this.steviloElementov = 0;

	}

	public boolean empty() {
		if (prvi == null) {
			return true;
		}
		return false;
	}

	public int front() {
		if(this.prvi==null) {
			return -1;
		}
		return prvi.stevilka;
	}

	public void enqueue(int a) {
		QueueElement nou = new QueueElement(a);
		if (prvi == null) {
			prvi = nou;
			zadn = nou;
			this.steviloElementov = 1;

			return;
		}
		zadn.next = nou;
		// nou.previous = zadn;
		zadn = nou;
		this.steviloElementov += 1;
	}

	public void dequeue() {
		if(prvi==null) {
			System.out.println("error");
			return;
		}
		prvi = prvi.next;
		this.steviloElementov -= 1;
		 //prvi.previous=null;
	}

}

class QueueElement {
	QueueElement next;
	QueueElement previous;
	int stevilka;

	public QueueElement(int a) {
		this.stevilka = a;
		this.next = null;
		// this.previous = null;
	}
}

class I3 {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static String nextToken() throws Exception {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(in.readLine(), "[ ,:]");
		}
		return st.nextToken();
	}

	public static int readInt() throws Exception {
		return Integer.parseInt(nextToken());
	}

	public static long readLong() throws Exception {
		return Long.parseLong(nextToken());
	}

	public static double readDouble() throws Exception {
		return Double.parseDouble(nextToken());
	}

	public static String readString() throws Exception {
		return nextToken();
	}

	public static String readLine() throws Exception {
		return in.readLine();
	}
}