import java.util.*;
import java.io.*;

class B1726 {
    static int N, M;
    static int[][] arr;
    static int sx, sy, sd; // 0123동서남북
    static int ex, ey, ed;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<int[]> queue = new ArrayDeque<>();
        Integer[][][] visit = new Integer[N][M][4];

        queue.add(new int[]{sx, sy, sd, 0});
        visit[sx][sy][sd] = 0;

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int x = q[0];
            int y = q[1];
            int d = q[2];
            int cnt = q[3];

            if (x == ex && y == ey && d == ed) {
                result = Math.min(result, cnt);
                continue;
            }

            // Go
            for (int i = 1; i <= 3; i++) {
                int nx = x + dx[d] * i;
                int ny = y + dy[d] * i;

                if (!checkRange(nx, ny)) break;
                if (visit[nx][ny][d] != null && visit[nx][ny][d] <= cnt + 1) continue;

                visit[nx][ny][d] = cnt + 1;
                queue.add(new int[]{nx, ny, d, cnt + 1});
            }

            // Turn
            if (d == 0 || d == 1) {
                for (int i = 2; i <= 3; i++) {
                    if (visit[x][y][i] != null && visit[x][y][i] <= cnt + 1) continue;
                    visit[x][y][i] = cnt + 1;
                    queue.add(new int[]{x, y, i, cnt + 1});
                }
            }
            else {
                for (int i = 0; i <= 1; i++) {
                    if (visit[x][y][i] != null && visit[x][y][i] <= cnt + 1) continue;
                    visit[x][y][i] = cnt + 1;
                    queue.add(new int[]{x, y, i, cnt + 1});
                }
            }
        }
    }

    public static boolean checkRange(int x, int y) {
        if (x < 0 || y < 0 || x >= N || y >= M || arr[x][y] == 1) return false;
        return true;
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
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken()) - 1;
        sy = Integer.parseInt(st.nextToken()) - 1;
        sd = Integer.parseInt(st.nextToken()) - 1;

        st = new StringTokenizer(br.readLine());
        ex = Integer.parseInt(st.nextToken()) - 1;
        ey = Integer.parseInt(st.nextToken()) - 1;
        ed = Integer.parseInt(st.nextToken()) - 1;

        br.close();
    }
}