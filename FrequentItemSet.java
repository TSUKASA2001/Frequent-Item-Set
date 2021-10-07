import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FrequentItemSet {
	public static int length = 4;// 商品番号の最大値

	static int returnCount(String fileName) throws IOException {// ファイルの行数 つまり客数を返す
		int i = 0;
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		while ((br.readLine()) != null) {
			i++;
		}
		br.close();
		return i;
	}

	static int[][] readData(String fileName, int n) throws IOException {// ファイルからメモリに格納するメソッド
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String buffer;
		int[][] elements = new int[n][];
		int i = 0;

		while ((buffer = br.readLine()) != null) {
			String[] s = buffer.split(" ");
			elements[i] = new int[s.length];
			for (int j = 0; j < s.length; j++) {
				elements[i][j] = Integer.parseInt(s[j]);
				length = Math.max(length, elements[i][j]);
			}
			i++;
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
		for(int i=0;i<elements.length;i++){
			if(elements[i] == x){
				return true;
			}
		}
		return false;
	}
	
	static boolean discrimination2(int[] elements, int[] x) {// numはelementsの行数 elementsのnum行目にxが含まれていればtrue
		//long start1=System.currentTimeMillis();														// 含まれていなければfalse
		int i, count = 0;

		for (i = 0; i < x.length; i++) {
			if (contains(elements,x[i])) {
				count++;
			}
		}
		//time1+=System.currentTimeMillis()-start1;
		if (count == x.length) {
			return true;
		} else {
			return false;
		}
	}

	static int[] arrayAdd(int[] x, int a) {// 配列xにaを追加する
		int[] newx = new int[x.length + 1];
		System.arraycopy(x, 0, newx, 0, x.length);
		newx[x.length] = a;

		return newx;
	}

	static void mine(int[][] elements, int[] x, int min) {
		for (int i = tail(x) + 1; i <= length + 1; i++) {

			int[] newx = arrayAdd(x, i);

			int count = 0;

			for (int j = 0; j < elements.length; j++) {
				if (discrimination2(elements[j], newx)) {
					count++;
				}
			}
			if (count >= min) {// 閾値を超えているとき
				System.out.println(Arrays.toString(newx) + (System.currentTimeMillis()-start));
				mine(elements, newx, min);
			}
		}
	}
	
	static long start;

	public static void main(String[] args) throws IOException {
		
		start=System.currentTimeMillis();

		String fileName="T10I4D100K.dat";
		int min = 1000;

		int[][] elements = readData(fileName, returnCount(fileName));
		int x[] = new int[0];

		mine(elements, x, min);// 閾値の設定
	}
}
