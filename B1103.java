import java.util.*;
import java.io.*;

class B1103 {
    static int N;
    static int M;
    static char[][] arr;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        boolean[][][] visit = new boolean[N][M][4];
        int[][][] dp = new int[N][M][4];

        for (int i = 0; i < 4; i++) {
            dfs(0, 0, i, dp, visit, 1);
        }
    }

    // i, j 위치에서 direction 방향으로 이동
    public static void dfs(int i, int j, int direction, int[][][] dp, boolean[][][] visit, int moveCnt) {
        if (result == -1) return;

        // 현재 위치가 구멍인지
        if (i < 0 || j < 0 || i >= N || j >= M || arr[i][j] == 'H') {
            result = Math.max(result, moveCnt-1);
            return;
        }

        // 이미 이동했던 위치인지
        if (visit[i][j][direction]) {
            result = -1;
            return;
        }

        if (dp[i][j][direction] >= moveCnt) return;

        dp[i][j][direction] = moveCnt;
        visit[i][j][direction] = true;

        // 이동 후 위치
        int[] nextSite = getSite(i, j, direction, arr[i][j]-'0');

        for (int d = 0; d < 4; d++) {
            dfs(nextSite[0], nextSite[1], d, dp, visit, moveCnt+1);
        }

        visit[i][j][direction] = false;
    }

    // direction : 0-위, 1-오, 2-아래, 3-왼
    public static int[] getSite(int i, int j, int direction, int move) {
        if (direction == 0) return new int[]{i-move, j};
        else if (direction == 1) return new int[]{i, j+move};
        else if (direction == 2) return new int[]{i+move, j};
        else return new int[]{i, j-move};
    }

    public static void print() throws IOException {
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
        arr = new char[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}