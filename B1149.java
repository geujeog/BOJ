import java.util.*;
import java.io.*;

class B1149 {
    static int N;
    static int[][] house;
    static int[][] dp;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        house = new int[N+1][3];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            house[i][0] = Integer.parseInt(st.nextToken());
            house[i][1] = Integer.parseInt(st.nextToken());
            house[i][2] = Integer.parseInt(st.nextToken());
        }

        dp = new int[N+1][3];
        dp[0][0] = house[0][0];
        dp[0][1] = house[0][1];
        dp[0][2] = house[0][2];

        dp[1][0] = Math.min(dp[0][1], dp[0][2]) + house[1][0];
        dp[1][1] = Math.min(dp[0][0], dp[0][2]) + house[1][1];
        dp[1][2] = Math.min(dp[0][0], dp[0][1]) + house[1][2];


        bw.write(draw(N-1)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int draw (int idx) {
        return Math.min(Math.min(min(idx, 0), min(idx, 1)), min(idx, 2));
    }

    public static int min (int idx, int rgb) {
        int minNum = 0;

        if (dp[idx-1][0] == 0) dp[idx-1][0] = min(idx-1, 0);
        if (dp[idx-1][1] == 0) dp[idx-1][1] = min(idx-1, 1);
        if (dp[idx-1][2] == 0) dp[idx-1][2] = min(idx-1, 2);

        if (rgb == 0) {
            minNum = Math.min(dp[idx-1][1], dp[idx-1][2]);
        }
        else if (rgb == 1) {
            minNum = Math.min(dp[idx-1][0], dp[idx-1][2]);
        }
        else if (rgb == 2) {
            minNum = Math.min(dp[idx-1][0], dp[idx-1][1]);
        }

        return minNum + house[idx][rgb];
    }
}