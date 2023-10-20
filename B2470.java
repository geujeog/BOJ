import java.io.*;
import java.util.*;

public class B2470 {
    static int N;
    static int[] arr;
    static int[] result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        int s = 0;
        int e = N-1;

        int minDiff = Integer.MAX_VALUE;

        while (s < e) {
            int diff = arr[s] + arr[e];

            if (minDiff > Math.abs(diff)) {
                minDiff = Math.abs(diff);

                result[0] = arr[s];
                result[1] = arr[e];

                if (diff == 0) break;
            }

            if (diff < 0) s++;
            else e--;
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result[0]+" "+result[1]);

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        result = new int[2];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        br.close();
    }
}
