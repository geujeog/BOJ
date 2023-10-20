import java.util.*;
import java.io.*;

public class B10942 {
    static int N;
    static int[] arr;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        arr = new int[N + 1];
        dp = new int[N + 1][N + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            Arrays.fill(dp[i], -1);
        }

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            bw.write(check(s, e) + "\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }

    public static int check(int s, int e) {
        if (s >= e) return 1;

        if (dp[s][e] == -1) {
            if (arr[s] == arr[e]) dp[s][e] = check(s + 1, e - 1);
            else dp[s][e] = 0;
        }

        return dp[s][e];
    }
}