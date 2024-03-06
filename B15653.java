import java.util.*;
import java.io.*;

class B15653 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int N, M;
    static char[][] arr;
    static int srx, sry, sbx, sby;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // dfs
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][][][] visit = new boolean[N][M][N][M];

        queue.add(new int[]{srx, sry, sbx, sby, 0});
        visit[srx][sry][sbx][sby] = true;

        int[] red = new int[2];
        int[] blue = new int[2];
        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int rx = q[0];
            int ry = q[1];
            int bx = q[2];
            int by = q[3];
            int t = q[4];

            for (int d = 0; d < dx.length; d++) {
                if ((d == 0 && rx <= bx) || (d == 1 && ry <= by) || (d == 2 && rx >= bx) || (d == 3 && ry >= by)) {
                    red = move(rx, ry, d, bx, by);
                    blue = move(bx, by, d, red[0], red[1]);
                }
                else {
                    blue = move(bx, by, d, rx, ry);
                    red = move(rx, ry, d, blue[0], blue[1]);
                }

                if (arr[blue[0]][blue[1]] == 'O') continue;
                if (arr[red[0]][red[1]] == 'O') {
                    result = t + 1;
                    return;
                }
                if (!visit[red[0]][red[1]][blue[0]][blue[1]]) {
                    visit[red[0]][red[1]][blue[0]][blue[1]] = true;
                    queue.add(new int[]{red[0], red[1], blue[0], blue[1], t + 1});
                }
            }
        }

        result = -1;
    }

    public static int[] move(int x, int y, int d, int dontX, int dontY) {
        while (true) {
            x += dx[d];
            y += dy[d];

            if (arr[x][y] == 'O') return new int[]{x, y};
            if (arr[x][y] == '#') break;
            if (x == dontX && y == dontY) break;
        }

        x += dx[d] * -1;
        y += dy[d] * -1;
        return new int[]{x, y};
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
        arr = new char[N][M];
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'R') {
                    srx = i;
                    sry = j;
                    arr[i][j] = '.';
                }
                else if (arr[i][j] == 'B') {
                    sbx = i;
                    sby = j;
                    arr[i][j] = '.';
                }
            }
        }

        br.close();
    }
}