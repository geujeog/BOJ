import java.util.*;
import java.io.*;

class B17472 {
    static int N, M;
    static int[][] island;
    static int islandCnt;
    static List<Tuple>[] bridge;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        getIsland();
        linkBridge();
        choiceBridge();
    }

    public static void choiceBridge() {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        boolean[] visit = new boolean[islandCnt];

        queue.add(new Tuple(2, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int v = t.x;
            int w = t.y;

            if (visit[v]) continue;

            visit[v] = true;
            result += w;

            for (Tuple tuple : bridge[v]) {
                queue.add(tuple);
            }
        }

        for (int i = 2; i < islandCnt; i++) {
            if (!visit[i]) {
                result = -1;
                return;
            }
        }
    }

    public static void linkBridge() {
        bridge = new ArrayList[islandCnt];
        for (int i = 2; i < islandCnt; i++) {
            bridge[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (island[i][j] > 1) {
                    linkOneToOne(i, j);
                }
            }
        }
    }

    public static void linkOneToOne(int i, int j) {
        Queue<Tuple> queue = new LinkedList<>();
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        for (int d = 0; d < dx.length; d++) {
            queue.add(new Tuple(i+dx[d], j+dy[d], d, 0));
        }

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int d = t.d;
            int cnt = t.cnt;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (island[i][j] == island[x][y]) continue;


            if (island[x][y] >= 2) {
                if (cnt > 1) {
                    bridge[island[x][y]].add(new Tuple(island[i][j], cnt));
                    bridge[island[i][j]].add(new Tuple(island[x][y], cnt));
                    break;
                }
                continue;
            }

            queue.add(new Tuple(x + dx[d], y + dy[d], d, cnt + 1));
        }
    }

    public static void getIsland() {
        islandCnt = 2;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (island[i][j] == 1) {
                    getIslandBFS(islandCnt++, i, j);
                }
            }
        }
    }

    public static void getIslandBFS(int islandIdx, int i, int j) {
        Queue<Tuple> queue = new LinkedList<>();
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        queue.add(new Tuple(i, j));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (island[x][y] != 1) continue;

            island[x][y] = islandIdx;

            for (int d = 0; d < dx.length; d++) {
                queue.add(new Tuple(x + dx[d], y + dy[d]));
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int d;
        int cnt;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Tuple(int x, int y, int d, int cnt) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.y - t.y;
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
        M = Integer.parseInt(st.nextToken());
        island = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                island[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}