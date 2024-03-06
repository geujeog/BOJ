import java.util.*;
import java.io.*;

class B14466 {
    static int[] dx = {-1, 1, 0, 0}; // 상하좌우
    static int[] dy = {0, 0, -1, 1};
    static int N, K;
    static boolean[][][] road;
    static boolean[][] cow;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 전체 - 길을 건너지 않고 갈 수 있는 경우의 수
        // bfs, 길이 있다면 더이상 진행 X

        getTotal();
        cross();
    }

    public static void cross() {
        Set<Tuple> set = new HashSet<>();

        boolean[][] visit = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (cow[i][j] && !visit[i][j]) {
                    bfs(i, j, visit, set);
                }
            }
        }

        result -= set.size();
    }

    public static void bfs(int i, int j, boolean[][] visit, Set<Tuple> set) {
        List<int[]> list = new ArrayList<>();
        Queue<int[]> queue = new ArrayDeque<>();

        queue.add(new int[]{i, j});
        visit[i][j] = true;

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            if (cow[q[0]][q[1]]) {
                list.add(new int[]{q[0], q[1]});
            }

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visit[nx][ny] || road[d][q[0]][q[1]]) continue;

                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }

        int size = list.size();
        for (int ii = 0; ii < size; ii++) {
            for (int jj = ii+1; jj < size; jj++) {
                int[] a = list.get(ii);
                int[] b = list.get(jj);
                set.add(new Tuple(a[0], a[1], b[0], b[1]));
            }
        }
    }

    public static void getTotal() {
        for (int i = 0; i < K; i++) {
            for (int j = i+1; j < K; j++) {
                result++;
            }
        }
    }

    public static class Tuple {
        int ax;
        int ay;
        int bx;
        int by;

        public Tuple (int ax, int ay, int bx, int by) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
        }

        @Override
        public boolean equals(Object o) {
            Tuple t = (Tuple) o;
            if (t.ax == this.ax && t.ay == this.ay) {
                if (t.bx == this.bx && t.by == this.by) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.ax, this.ay, this.bx, this.by);
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
        K = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        road = new boolean[4][N][N];
        cow = new boolean[N][N];

        while (R-- > 0) {
            st = new StringTokenizer(br.readLine());
            int ax = Integer.parseInt(st.nextToken()) - 1;
            int ay = Integer.parseInt(st.nextToken()) - 1;
            int bx = Integer.parseInt(st.nextToken()) - 1;
            int by = Integer.parseInt(st.nextToken()) - 1;

            if (ax == bx) {
                int a = (ay > by) ? 2 : 3;
                int b = (by > ay) ? 2 : 3;
                road[a][ax][ay] = true;
                road[b][bx][by] = true;
            }
            if (ay == by) {
                int a = (ax > bx) ? 0 : 1;
                int b = (bx > ax) ? 0 : 1;
                road[a][ax][ay] = true;
                road[b][bx][by] = true;
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            cow[x][y] = true;
        }

        br.close();
    }
}