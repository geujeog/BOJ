import java.util.*;
import java.io.*;

class B2186 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int N, M, K;
    static char[][] arr;
    static String end;
    static Integer[][][] dp;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        initDP();
        fillDP();
    }

    public static void fillDP() {
        int len = end.length() - 1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                result += dfs(len, i, j);
            }
        }
    }

    public static int dfs(int len, int i, int j) {
        if (dp[len][i][j] == null) {
            dp[len][i][j] = 0;

            if (arr[i][j] == end.charAt(len)) {
                for (int k = 1; k <= K; k++) {
                    for (int d = 0; d < dx.length; d++) {
                        int nx = i + dx[d] * k;
                        int ny = j + dy[d] * k;

                        if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

                        dp[len][i][j] += dfs(len - 1, nx, ny);
                    }
                }
            }
        }

        return dp[len][i][j];
    }

    public static void initDP() {
        char s = end.charAt(0);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == s) {
                    dp[0][i][j] = 1;
                }
                else {
                    dp[0][i][j] = 0;
                }
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new char[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        end = br.readLine();
        dp = new Integer[end.length()][N][M];

        br.close();
    }
}