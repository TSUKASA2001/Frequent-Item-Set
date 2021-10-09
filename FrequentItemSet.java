import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class FrequentItemSet {
	public static int length = 4;// 商品番号の最大値
	
	static BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(System.out));

	static ArrayList<int[]>readData(String fileName) throws IOException {// ファイルからメモリに格納するメソッド
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String buffer;
		ArrayList<int[]> elements = new ArrayList<int[]>();

		while ((buffer = br.readLine()) != null) {
			String[] s = buffer.split(" ");
			int[] element = new int[s.length];
			elements.add(element);
			for (int j = 0; j < s.length; j++) {
				element[j] = Integer.parseInt(s[j]);
				length = Math.max(length, element[j]);
			}
			Arrays.sort(element);			
		}

		br.close();

		return elements;
	}

	static int tail(int[] x) {
		if (x.length == 0)
			return -1;
		int l = x.length;
		return x[l - 1];
	}
	
	static boolean contains(int[] elements, int x){
		for(int i=0;i<elements.length && elements[i] <= x;i++){
			if(elements[i] == x){
				return true;
			}
		}
		return false;
	}
	
	static int[] arrayAdd(int[] x, int a) {// 配列xにaを追加する
		int[] newx = new int[x.length + 1];
		System.arraycopy(x, 0, newx, 0, x.length);
		newx[x.length] = a;

		return newx;
	}

	static void mine(ArrayList<int[]> elements, int[] x, int min, int [] preCounts) {
		
		int tail=tail(x);
		int [] counts=new int [length+2];
		ArrayList<int[]> [] newElements=new ArrayList[length+2];
		for(int i=0;i<length+1;i++) {
			if( tail<i && (preCounts==null ||preCounts[i]>=min))
				newElements[i]=new ArrayList<int[]>();
		}
		for (int j = 0; j < elements.size(); j++) {
			int[] transaction = elements.get(j);
			for(int k=transaction.length-1;k>=0;k--) {
				if(tail<transaction[k] && (preCounts==null ||preCounts[transaction[k]]>=min)) {
					counts[transaction[k]]++;
					newElements[transaction[k]].add(transaction);
				}else
					break;
			}
		}			
		
		for (int i = tail + 1; i <= length + 1; i++) {

			if (counts[i] >= min) {// 閾値を超えているとき
				int[] newx = arrayAdd(x, i);
				try {
					bout.write(Arrays.toString(newx));
					bout.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mine(newElements[i], newx, min, counts);
			}
		}
	}
	

	public static void main(String[] args) throws IOException {
		
		long start=System.currentTimeMillis();

		String fileName="T10I4D100K.dat";
		int min = 2;

		ArrayList<int[]> elements = readData(fileName);
		int x[] = new int[0];

		mine(elements, x, min, null);// 閾値の設定
		
		bout.close();

		long end=System.currentTimeMillis();
		System.err.println((end-start)+" msec");
	}
}
