import java.util.*;
import java.io.*;

class B13459 {
    static int N, M;
    static char[][] arr;
    static int r1, r2;
    static int b1, b2;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static boolean possible;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<Tuple> queue = new ArrayDeque<>();

        queue.add(new Tuple(r1, r2, b1, b2, -1, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int rx = t.rx;
            int ry = t.ry;
            int bx = t.bx;
            int by = t.by;
            int dir = t.dir;
            int cnt = t.cnt;

            for (int d = 0; d < dx.length; d++) {
                if (dir == 0 && d == 1) continue;
                if (dir == 1 && d == 0) continue;
                if (dir == 2 && d == 3) continue;
                if (dir == 3 && d == 2) continue;

                int[] red = new int[2];
                int[] blue = new int[2];

                if ((d == 0 && rx <= bx) || (d == 1 && rx > bx) || (d == 2 && ry <= by) || (d == 3 && ry > by)) {
                    red = move(rx, ry, d, bx, by);
                    blue = move(bx, by, d, red[0], red[1]);
                }
                else {
                    blue = move(bx, by, d, rx, ry);
                    red = move(rx, ry, d, blue[0], blue[1]);
                }

                if ((rx == red[0] && ry == red[1]) && (bx == blue[0] && by == blue[1])) continue;
                if (arr[blue[0]][blue[1]] == 'O' || cnt + 1 > 10) continue;

                if (arr[red[0]][red[1]] == 'O') {
                    possible = true;
                    break;
                }

                queue.add(new Tuple(red[0], red[1], blue[0], blue[1], d, cnt + 1));
            }
        }
    }

    public static int[] move(int x, int y, int dir, int dontX, int dontY) {
        while (arr[x][y] == '.') {
            x += dx[dir];
            y += dy[dir];

            if (arr[x][y] == 'O') return new int[]{x, y};
            if (x == dontX && y == dontY) break;
        }

        x += (dx[dir] * -1);
        y += (dy[dir] * -1);

        return new int[]{x, y};
    }

    public static class Tuple {
        int rx;
        int ry;
        int bx;
        int by;
        int dir;
        int cnt;

        public Tuple(int rx, int ry, int bx, int by, int dir, int cnt) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.dir = dir;
            this.cnt = cnt;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (possible) bw.write("1");
        else bw.write("0");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        possible = false;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'R') {
                    arr[i][j] = '.';
                    r1 = i;
                    r2 = j;
                }
                else if (arr[i][j] == 'B') {
                    arr[i][j] = '.';
                    b1 = i;
                    b2 = j;
                }
            }
        }

        br.close();
    }
}