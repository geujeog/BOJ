import java.util.*;
import java.io.*;

class B10986 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int sum = 0;
        long result = 0;
        int[] dp = new int[M];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            sum = (sum + Integer.parseInt(st.nextToken())) % M;

            if (sum == 0) result++;

            result += dp[sum];
            dp[sum]++;
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }
}