import java.util.*;
import java.io.*;

class B1726 {
    static int N;
    static int M;
    static int[][] arr;
    static int sx, sy, sd;
    static int ex, ey, ed;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(sx, sy, sd, 0));

        boolean[][][] visit = new boolean[N][M][5];

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int d = t.d;
            int cnt = t.cnt;

            if (visit[x][y][d]) continue;
            else visit[x][y][d] = true;

            if (x == ex && y == ey && d == ed) {
                result = cnt;
                break;
            }

            // 회전
            turn(x, y, d, cnt+1, queue);

            // 직진
            straight(x, y, d, cnt+1, queue);
        }
    }

    public static void turn(int x, int y, int d, int cnt, Queue<Tuple> queue) {
        if (d == 1 || d == 2) {
            queue.add(new Tuple(x, y, 3, cnt));
            queue.add(new Tuple(x, y, 4, cnt));
        }
        else {
            queue.add(new Tuple(x, y, 1, cnt));
            queue.add(new Tuple(x, y, 2, cnt));
        }
    }

    public static void straight(int x, int y, int d, int cnt, Queue<Tuple> queue) {
        int[] dx = new int[]{0, 0, 0, 1, -1};
        int[] dy = new int[]{0, 1, -1, 0, 0};

        for (int i = 1; i <= 3; i++) {
            int tmpX = x + dx[d] * i;
            int tmpY = y + dy[d] * i;

            if (tmpX < 0 || tmpY < 0 || tmpX >= N || tmpY >= M || arr[tmpX][tmpY] == 1) break;
            queue.add(new Tuple(tmpX, tmpY, d, cnt));
        }
    }

    public static class Tuple{
        int x;
        int y;
        int d;
        int cnt;

        public Tuple(int x, int y, int d, int cnt) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.cnt = cnt;
        }
    }

    public static void print() throws IOException {
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
        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken()) - 1;
        sy = Integer.parseInt(st.nextToken()) - 1;
        sd = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        ex = Integer.parseInt(st.nextToken()) - 1;
        ey = Integer.parseInt(st.nextToken()) - 1;
        ed = Integer.parseInt(st.nextToken());

        br.close();
    }
}