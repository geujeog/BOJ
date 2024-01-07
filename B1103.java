import java.util.*;
import java.io.*;

class B1103 {
    static int N, M;
    static int[][] arr;
    static boolean[][] visit;
    static int[][] dp;
    static int result;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        visit[0][0] = true;
        dp[0][0] = 1;

        dfs(0, 0, 1);
    }

    public static void dfs(int x, int y, int moveCnt) {
        if (result == -1) return;

        dp[x][y] = moveCnt;
        result = Math.max(result, moveCnt);

        for (int d = 0; d < dx.length; d++) {
            int nx = x + dx[d] * arr[x][y];
            int ny = y + dy[d] * arr[x][y];

            if (nx < 0 || ny < 0 || nx >= N || ny >= M || arr[nx][ny] == 0 || dp[nx][ny] > moveCnt) continue;
            if (visit[nx][ny]) {
                result = -1;
                return;
            }

            visit[nx][ny] = true;
            dfs(nx, ny, moveCnt+1);
            visit[nx][ny] = false;
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
        arr = new int[N][M];
        visit = new boolean[N][M];
        dp = new int[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                char c = line.charAt(j);
                if (c == 'H') arr[i][j] = 0;
                else arr[i][j] = c - '0';
            }
        }

        br.close();
    }
}