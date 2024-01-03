import java.util.*;
import java.io.*;

class B2146 {
    static int N;
    static int[][] arr, island;
    static int islandCnt;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        getIsland();
        getBridge();
    }

    public static void getBridge() {
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == 1) {
                    if (i - 1 >= 0 && arr[i - 1][j] == 0) linkBridge(i, j);
                    else if (i + 1 < N && arr[i + 1][j] == 0) linkBridge(i, j);
                    else if (j - 1 >= 0 && arr[i][j - 1] == 0) linkBridge(i, j);
                    else if (j + 1 < N && arr[i][j + 1] == 0) linkBridge(i, j);
                }
            }
        }
    }

    public static void linkBridge(int i, int j) {
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, -1, 0, 1};

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j, 0));

        boolean[][] visit = new boolean[N][N];

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int cnt = t.cnt;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (result <= cnt || visit[x][y]) continue;

            if (arr[x][y] == 1 && !(x == i && y == j)) {
                if (island[x][y] != island[i][j]) {
                    result = Math.min(result, cnt);
                }
                continue;
            }

            visit[x][y] = true;

            for (int d = 0; d < dx.length; d++) {
                queue.add(new Tuple(x + dx[d], y + dy[d], cnt+1));
            }
        }
    }

    public static void getIsland() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == 1 && island[i][j] == 0) {
                    islandCnt++;
                    getSameIsland(i, j);
                }
            }
        }
    }

    public static void getSameIsland(int i, int j) {
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, -1, 0, 1};

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (arr[x][y] == 0 || island[x][y] != 0) continue;

            island[x][y] = islandCnt;

            for (int d = 0; d < dx.length; d++) {
                queue.add(new Tuple(x + dx[d], y + dy[d]));
            }
        }
    }

    public static class Tuple {
        int x;
        int y;
        int cnt;

        public Tuple (int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Tuple (int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result - 1+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        island = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}