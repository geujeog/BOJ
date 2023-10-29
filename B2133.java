import java.util.*;
import java.io.*;

class B2133 {
    static int N;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        if (N < 2 || N % 2 == 1) return;

        dp[2] = 3;

        for (int i = 4; i <= N; i+=2) {
            dp[i] = dp[i-2] * dp[2] + 2;

            for (int j = 2; j < i-2; j+=2) {
                dp[i] += (dp[j] * 2);
            }
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(dp[N]+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        dp = new int[N+1];

        br.close();
    }
}