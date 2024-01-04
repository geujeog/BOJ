import java.util.*;
import java.io.*;

class B2638 {
    static int N, M;
    static int[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        while (melt()) {
            result++;
        }
    }

    public static boolean melt() {
        Queue<Tuple> queue = new LinkedList<>();
        boolean[][] visit = new boolean[N][M];
        int[][] around = new int[N][M];
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        queue.add(new Tuple(0, 0));
        visit[0][0] = true;

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;

            for (int i = 0; i < dx.length; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                around[nx][ny]++;
                if (visit[nx][ny] || arr[nx][ny] == 1) continue;

                visit[nx][ny] = true;
                queue.add(new Tuple(nx, ny));
            }
        }

        boolean isMelt = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 1 && around[i][j] >= 2) {
                    isMelt = true;
                    arr[i][j] = 0;
                }
            }
        }

        return isMelt;
    }

    public static class Tuple {
        int x;
        int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
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
        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}