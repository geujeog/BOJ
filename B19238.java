import java.util.*;
import java.io.*;

class B19238 {
    static int N, M, S;
    static int[][] map;
    static Tuple driver;
    static Map<Integer, Tuple> destination;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        while (M-- > 0) {
            Tuple customer = getCustomer();
            if (!canArrive(customer)) break;

            Tuple dest = moveDestination(map[customer.x][customer.y]);
            if (!canArrive(dest)) break;

            S += dest.t * 2;
        }
    }

    public static Tuple moveDestination(int idx) {
        boolean[][] visit = new boolean[N][N];
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        int[] dx = {0, -1, 0, 1};
        int[] dy = {-1, 0, 1, 0};
        int ex = destination.get(idx).x;
        int ey = destination.get(idx).y;

        queue.add(driver);
        map[driver.x][driver.y] = 0;

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int time = t.t;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (map[x][y] == 1 || visit[x][y]) continue;

            visit[x][y] = true;

            if (x == ex && y == ey) {
                driver = new Tuple(x, y);
                return t;
            }

            for (int i = 0; i < dx.length; i++) {
                queue.add(new Tuple(x + dx[i], y + dy[i], time + 1));
            }
        }

        S = -1;
        return new Tuple(-1, -1);
    }

    public static boolean canArrive(Tuple dest) {
        S -= dest.t;

        if (dest.x == -1 || dest.y == -1 || S < 0) {
            S = -1;
            return false;
        }
        return true;
    }

    public static Tuple getCustomer() {
        boolean[][] visit = new boolean[N][N];
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        int[] dx = {0, -1, 0, 1};
        int[] dy = {-1, 0, 1, 0};

        queue.add(driver);

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int time = t.t;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (map[x][y] == 1 || visit[x][y]) continue;

            visit[x][y] = true;

            if (map[x][y] > 1) {
                driver = new Tuple(x, y);
                return t;
            }

            for (int i = 0; i < dx.length; i++) {
                queue.add(new Tuple(x + dx[i], y + dy[i], time + 1));
            }
        }

        return new Tuple(-1, -1);
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int t;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Tuple (int x, int y, int t) {
            this.x = x;
            this.y = y;
            this.t = t;
        }

        @Override
        public int compareTo(Tuple t) {
            if (this.t == t.t) {
                if (this.x == t.x) {
                    return this.y - t.y;
                }
                return this.x - t.x;
            }
            return this.t - t.t;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(S+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        destination = new HashMap<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        driver = new Tuple(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        for (int i = 2; i < M+2; i++) {
            st = new StringTokenizer(br.readLine());
            int sx = Integer.parseInt(st.nextToken()) - 1;
            int sy = Integer.parseInt(st.nextToken()) - 1;
            int ex = Integer.parseInt(st.nextToken()) - 1;
            int ey = Integer.parseInt(st.nextToken()) - 1;

            map[sx][sy] = i;
            destination.put(i, new Tuple(ex, ey));
        }

        br.close();
    }
}