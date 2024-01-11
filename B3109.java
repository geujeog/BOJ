import java.util.*;
import java.io.*;

class B3109 {
    static int R, C;
    static char[][] arr;
    static boolean[][] visit;
    static int[] dx = {-1, 0, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 0; i < R; i++) {
            if (dfs(i, 0)) result++;
        }
    }

    public static boolean dfs(int x, int y) {
        for (int d = 0; d < dx.length; d++) {
            int nx = x + dx[d];
            int ny = y + 1;

            if (nx < 0 || nx >= R || arr[nx][ny] == 'x' || visit[nx][ny]) continue;

            visit[nx][ny] = true;

            if (ny == C-1)  {
                return true;
            }

            if (dfs(nx, ny)) return true;
        }

        return false;
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
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        arr = new char[R][C];
        visit = new boolean[R][C];

        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}