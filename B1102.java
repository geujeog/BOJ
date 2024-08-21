import java.util.*;
import java.io.*;

class B1102 {
    static int N, P;
    static int[][] cost;
    static Integer[][] dp; // [켜진발전기개수][켜진발전기Bit]
    static int initBit, initCnt;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        if (initCnt >= P) {
            result = 0;
            return;
        }

        dp = new Integer[N+1][(1 << N+1) + 1];
        result = dfs(initCnt, initBit);
    }

    public static int dfs(int cnt, int bit) {
        if (cnt == P) return 0;

        if (dp[cnt][bit] == null) {
            dp[cnt][bit] = Integer.MAX_VALUE;

            for (int i = 1; i <= N; i++) {
                // i가 켜져있으면
                if ((bit & (1 << i)) != (1 << i)) continue;

                for (int j = 1; j <= N; j++) {
                    // j가 안켜져 있으면
                    if (i == j || (bit & (1 << j)) == (1 << j)) continue;

                    dp[cnt][bit] = Math.min(dp[cnt][bit], dfs(cnt + 1, bit | (1 << j)) + cost[i][j]);
                }
            }
        }

        return dp[cnt][bit];
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (result == Integer.MAX_VALUE) bw.write(-1+"");
        else bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        cost = new int[N+1][N+1];
        result = Integer.MAX_VALUE;

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        String on = br.readLine();
        for (int i = 1; i <= N; i++) {
            if (on.charAt(i-1) == 'Y') {
                initBit |= (1 << i);
                initCnt++;
            }
        }

        P = Integer.parseInt(br.readLine());

        br.close();
    }
}