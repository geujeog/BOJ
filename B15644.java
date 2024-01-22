import java.util.*;
import java.io.*;

class B15644 {
    static int N, M;
    static char[][] arr;
    static int s1x, s1y;
    static int s2x, s2y;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static char[] direction = {'U', 'L', 'D', 'R'};
    static String result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<Tuple> queue = new ArrayDeque<>();
        queue.add(new Tuple(s1x, s1y, s2x, s2y, ""));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int rx = t.rx;
            int ry = t.ry;
            int bx = t.bx;
            int by = t.by;
            String road = t.road;

            int len = road.length();

            for (int d = 0; d < dx.length; d++) {
                if (len > 0) {
                    if ((road.charAt(len-1) == direction[0] || road.charAt(len-1) == direction[2])
                            && (d == 0 || d == 2))  continue;
                    if ((road.charAt(len-1) == direction[1] || road.charAt(len-1) == direction[3])
                            && (d == 1 || d == 3)) continue;
                }

                int[] nextR = new int[2];
                int[] nextB = new int[2];

                if ((d == 0 && rx <= bx) || (d == 1 && ry <= by) || (d == 2 && rx >= bx) || (d == 3 && ry >= by)) {
                    nextR = move(rx, ry, d, bx, by);
                    nextB = move(bx, by, d, nextR[0], nextR[1]);
                }
                else {
                    nextB = move(bx, by, d, rx, ry);
                    nextR = move(rx, ry, d, nextB[0], nextB[1]);
                }

                if (arr[nextB[0]][nextB[1]] == 'O' || len == 10) continue;

                StringBuilder sb = new StringBuilder(road);
                sb.append(direction[d]);

                if (arr[nextR[0]][nextR[1]] == 'O') {
                    result = sb.toString();
                    return;
                }

                queue.add(new Tuple(nextR[0], nextR[1], nextB[0], nextB[1], sb.toString()));
            }
        }
    }

    public static int[] move(int x, int y, int d, int dontX, int dontY) {
        while (arr[x][y] == '.') {
            x += dx[d];
            y += dy[d];

            if (arr[x][y] == 'O') return new int[] {x, y};
            if (x == dontX && y == dontY) break;
        }

        x += dx[(d + 2) % dx.length];
        y += dy[(d + 2) % dx.length];
        return new int[] {x, y};
    }

    public static class Tuple {
        int rx;
        int ry;
        int bx;
        int by;
        String road;

        public Tuple(int rx, int ry, int bx, int by, String road) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.road = road;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (result.length() == 0) {
            bw.write(-1+"");
        }
        else {
            bw.write(result.length()+"\n");
            bw.write(result);
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        result = "";

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'R') {
                    s1x = i;
                    s1y = j;
                    arr[i][j] = '.';
                }
                else if (arr[i][j] == 'B') {
                    s2x = i;
                    s2y = j;
                    arr[i][j] = '.';
                }
            }
        }

        br.close();
    }
}