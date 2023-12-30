import java.util.*;
import java.io.*;

class B1600 {
    static int K;
    static int W, H;
    static int[][] arr;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int[] dx = {0, -1, 0, 1};
        int[] dy = {-1, 0, 1, 0};
        int[] ddx = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] ddy = {-1, 1, -2, 2, -2, 2, -1, 1};

        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        queue.add(new Tuple(0, 0, 0, 0));

        boolean[][][] visit = new boolean[K+1][H][W];

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int kCnt = t.kCnt;
            int moveCnt = t.moveCnt;

            if (x < 0 || y < 0 || x >= H || y >= W) continue;
            if (arr[x][y] == 1 || visit[kCnt][x][y]) continue;

            visit[kCnt][x][y] = true;

            if (x == H-1 && y == W-1) {
                result = moveCnt;
                break;
            }

            if (kCnt < K) {
                for (int i = 0; i < 8; i++) {
                    queue.add(new Tuple(x+ddx[i], y+ddy[i], kCnt+1, moveCnt+1));
                }
            }

            for (int i = 0; i < 4; i++) {
                queue.add(new Tuple(x+dx[i], y+dy[i], kCnt, moveCnt+1));
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int kCnt;
        int moveCnt;

        public Tuple(int x, int y, int kCnt, int moveCnt) {
            this.x = x;
            this.y = y;
            this.kCnt = kCnt;
            this.moveCnt = moveCnt;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.moveCnt - t.moveCnt;
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

        K = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        arr = new int[H][W];
        result = -1;

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < W; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}