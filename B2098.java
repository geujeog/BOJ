import java.util.*;
import java.io.*;

class B2098 {
    static int N;
    static int[][] weight;
    static Integer[][] dp;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        result = dfs(0, 1);
    }

    public static int dfs(int v, int visit) {
        if (visit == (1 << N) - 1) {
            if (weight[v][0] == 0) return Integer.MAX_VALUE;
            return weight[v][0];
        }

        if (dp[v][visit] == null) {
            dp[v][visit] = Integer.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                int next = visit | (1 << i);

                if (weight[v][i] == 0 || (visit & (1 << i)) != 0) continue;
                dp[v][visit] = Math.min(dp[v][visit], dfs(i, next) + weight[v][i]);
            }
        }

        return dp[v][visit];
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        weight = new int[N][N];
        dp = new Integer[N][(1 << N) - 1];
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                int w = Integer.parseInt(st.nextToken());
                weight[i][j] = w;
            }
        }

        br.close();
    }
}