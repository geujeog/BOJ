import java.util.*;
import java.io.*;

class B16985 {
    static int N = 5;
    static int D = 4;
    static int[][][][] arr;
    static int[] dx = {-1, 1, 0, 0, 0, 0};
    static int[] dy = {0, 0, -1, 1, 0, 0};
    static int[] dz = {0, 0, 0, 0, -1, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 회전
        // 조합

        turn();
        combination();
    }

    public static void combination() {
        Tuple t;
        for (int k = 0; k < N; k++) {
            for (int d = 0; d < D; d++) {
                t = new Tuple(k, d);
                if (canFirst(t)) {
                    List<Tuple> order = new ArrayList<>();
                    order.add(t);
                    trace(order);
                }
            }
        }
    }

    public static void trace(List<Tuple> order) {
        if (order.size() == N) {
            if (canLast(order.get(order.size()-1))) {
                result = Math.min(result, bfs(order));
            }
            return;
        }

        for (int k = 0; k < N; k++) {
            for (int d = 0; d < D; d++) {
                Tuple t = new Tuple(k, d);
                if (!order.contains(t)) {
                    order.add(t);
                    trace(order);
                    order.remove(t);
                }
            }
        }
    }

    public static int bfs(List<Tuple> order) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][][] visit = new boolean[N][N][N];
        int[] q;
        int nx, ny, nz;
        int[][][] floor = new int[N][N][N];
        Tuple t;

        // 층수 쌓기
        for (int k = 0; k < order.size(); k++) {
            t = order.get(k);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    floor[k][i][j] = arr[t.k][t.d][i][j];
                }
            }
        }

        queue.add(new int[]{0, 0, 0, 0}); // 층수, x, y, 이동횟수
        visit[0][0][0] = true;

        while (!queue.isEmpty()) {
            q = queue.poll();

            for (int dd = 0; dd < dx.length; dd++) {
                nx = q[1] + dx[dd];
                ny = q[2] + dy[dd];
                nz = q[0] + dz[dd];

                if (!isIn(nx, ny, nz) || floor[nz][nx][ny] == 0 || visit[nz][nx][ny]) continue;

                if (nz == N-1 && nx == N-1 && ny == N-1) {
                    return q[3] + 1;
                }

                visit[nz][nx][ny] = true;
                queue.add(new int[]{nz, nx, ny, q[3] + 1});
            }
        }

        return Integer.MAX_VALUE;
    }

    public static boolean isIn(int x, int y, int z) {
        if (x < 0 || y < 0 || x >= N || y >= N || z < 0 || z >= N) return false;
        return true;
    }

    public static boolean canLast(Tuple t) {
        if (arr[t.k][t.d][N-1][N-1] == 1) return true;
        return false;
    }

    public static boolean canFirst(Tuple t) {
        if (arr[t.k][t.d][0][0] == 1) return true;
        return false;
    }

    public static void turn() {
        for (int k = 0; k < N; k++) {
            for (int d = 0; d < D-1; d++) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        arr[k][d+1][j][N-i-1] = arr[k][d][i][j];
                    }
                }
            }
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
        StringTokenizer st;
        arr = new int[N][D][N][N];
        result = Integer.MAX_VALUE;

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    arr[k][0][i][j] = Integer.parseInt(st.nextToken());
                }
            }
        }

        br.close();
    }

    public static class Tuple {
        int k;
        int d;

        public Tuple(int k, int d) {
            this.k = k;
            this.d = d;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.k);
        }

        @Override
        public boolean equals(Object o) {
            Tuple t = (Tuple) o;
            if (t.k == this.k) return true;
            return false;
        }
    }
}