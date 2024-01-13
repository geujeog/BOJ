import java.util.*;
import java.io.*;

class B2151 {
    static int N;
    static char[][] arr;
    static Integer[][][] visit;
    static int sx, sy;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int d = 0; d < dx.length; d++) {
            dfs(sx, sy, d, 0);
        }
    }

    public static void dfs(int x, int y, int dir, int cnt) {
        if (cnt > result) return;
        if (visit[x][y][dir] != null && visit[x][y][dir] <= cnt) return;

        visit[x][y][dir] = cnt;

        int nx = x + dx[dir];
        int ny = y + dy[dir];

        if (nx < 0 || ny < 0 || nx >= N || ny >= N || arr[nx][ny] == '*') return;

        if (arr[nx][ny] == '#') {
            result = Math.min(result, cnt);
            return;
        }

        // 직진
        dfs(nx, ny, dir, cnt);

        // 거울
        if (arr[nx][ny] == '!') {
            dfs(nx, ny, (dir + 1) % dx.length, cnt + 1);
            dfs(nx, ny, (dir + 3) % dx.length, cnt + 1);
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

        N = Integer.parseInt(br.readLine());
        arr = new char[N][N];
        visit = new Integer[N][N][dx.length];
        result = Integer.MAX_VALUE;
        sx = -1;
        sy = -1;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < N; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == '#' && sx == -1 && sy == -1) {
                    sx = i;
                    sy = j;
                    arr[i][j] = '.';
                }
            }
        }

        br.close();
    }
}