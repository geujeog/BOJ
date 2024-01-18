import java.util.*;
import java.io.*;

class B9376 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int R, C;
    static char[][] arr;
    static Integer[][][] visit;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int[][] start;
    static int result;

    public static void main (String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            input();
            solution();
            output();
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void solution() {
        for (int s = 0; s < start.length; s++){
            bfs(s, start[s][0], start[s][1]);
        }

        for (int i = 1; i < R-1; i++) {
            for (int j = 1; j < C-1; j++) {
                if (arr[i][j] != '*') {
                    Integer sum = 0;
                    for (int s = 0; s < start.length; s++) {
                        if (visit[s][i][j] == null) {
                            sum = null;
                            break;
                        }
                        sum += visit[s][i][j];
                    }

                    if (sum != null) {
                        if (arr[i][j] == '#') sum -= 2;
                        result = Math.min(result, sum);
                    }
                }
            }
        }
    }

    public static void bfs(int idx, int i, int j) {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();

        visit[idx][i][j] = 0;
        queue.add(new Tuple(i, j, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int cnt = t.cnt;

            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= R || ny >= C) continue;
                if (visit[idx][nx][ny] != null || arr[nx][ny] == '*') continue;

                int p = (arr[nx][ny] == '#') ? 1 : 0;
                visit[idx][nx][ny] = cnt + p;
                queue.add(new Tuple(nx, ny, cnt + p));
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int cnt;

        public Tuple(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.cnt - t.cnt;
        }
    }

    public static void output() throws IOException {
        bw.write(result+"\n");
    }

    public static void input() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken()) + 2;
        C = Integer.parseInt(st.nextToken()) + 2;
        arr = new char[R][C];
        visit = new Integer[3][R][C];
        result = Integer.MAX_VALUE;
        start = new int[3][2];

        for (int i = 0; i < R; i++) {
            if (i == 0 || i == R-1) {
                Arrays.fill(arr[i], '.');
                continue;
            }

            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                if (j == 0 || j == C-1) {
                    arr[i][j] = '.';
                    continue;
                }
                arr[i][j] = line.charAt(j-1);

                if (arr[i][j] == '$') {
                    if (start[1][0] == 0 && start[1][1] == 0) {
                        start[1][0] = i;
                        start[1][1] = j;
                    }
                    else {
                        start[2][0] = i;
                        start[2][1] = j;
                    }
                }
            }
        }
    }
}