import java.util.*;
import java.io.*;

class B2098 {
    static int N;
    static int[][] arr;
    static int[][] dp;
    static int INF = 987654321;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        result = tsp(0, 1);
    }

    public static int tsp(int city, int bit) {
        if (bit == (1 << N) - 1) {
            if (arr[city][0] == 0) return INF;
            else return arr[city][0];
        }

        if (dp[city][bit] != -1) return dp[city][bit];

        dp[city][bit] = INF;

        for (int i = 0; i < N; i++) {
            // 경로가 없거나 이미 방문했을 경우
            if (arr[city][i] == 0 || (bit & (1 << i)) != 0) continue;

            int next = bit | (1 << i);

            dp[city][bit] = Math.min(dp[city][bit], tsp(i, next) + arr[city][i]);
        }

        return dp[city][bit];
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        dp = new int[N][(1 << N) - 1];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}