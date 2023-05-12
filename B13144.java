
import java.util.*;
import java.io.*;


public class B13144 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        boolean[] check = new boolean[100001];

        long result = (long)((long)N * (long)(N+1) / 2l);
        int start = 0;
        int end = 0;
        while (end < N) {
            if (check[arr[end]]) {
                check[arr[start]] = false;
                start++;

                result -= (long)(N - end);
            }
            else {
                check[arr[end]] = true;
                end++;
            }
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }
}