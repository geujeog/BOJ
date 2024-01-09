import java.util.*;
import java.io.*;

class B1194 {
    static int N, M;
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
        dfs(sx, sy, 0, 0);
    }

    public static void dfs(int x, int y, int cnt, int key) {
        if (arr[x][y] == '1') {
            result = Math.min(result, cnt);
            return;
        }

        if (Character.isLowerCase(arr[x][y])) {
            if ((key & (1 << arr[x][y] - 'a')) == 0) {
                key |= 1 << arr[x][y] - 'a';
            }
        }

        visit[x][y][key] = cnt;

        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
            if (arr[nx][ny] == '#' || (visit[nx][ny][key] != null && visit[nx][ny][key] <= cnt + 1)) continue;

            if (Character.isUpperCase(arr[nx][ny])) {
                if ((key & (1 << arr[nx][ny] - 'a')) == 0) continue;
            }

            dfs(nx, ny, cnt + 1, key);
        }
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
        M = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        visit = new Integer[N][M][1 << 'f' - 'a' + 1];
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == '0') {
                    arr[i][j] = '.';
                    sx = i;
                    sy = j;
                }
            }
        }

        br.close();
    }
}