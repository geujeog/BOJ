import java.util.*;
import java.io.*;

class B16930 {
    static int N, M, K;
    static char[][] arr;
    static int[][] visit;
    static int sx, sy;
    static int ex, ey;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // BFS

        Queue<int[]> queue = new ArrayDeque<>();

        visit[sx][sy] = 1;
        queue.add(new int[]{sx, sy, 1});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int move = q[2];

            for (int d = 0; d < dx.length; d++) {
                for (int k = 1; k <= K; k++) {
                    int nx = q[0] + dx[d] * k;
                    int ny = q[1] + dy[d] * k;

                    if (nx < 0 || ny < 0 || nx >= N || ny >= M || arr[nx][ny] == '#') break;

                    if (nx == ex && ny == ey) {
                        result = move;
                        return;
                    }

                    if (visit[nx][ny] == 0) {
                        visit[nx][ny] = move + 1;
                        queue.add(new int[]{nx, ny, move + 1});
                    }
                    else if (visit[nx][ny] == move + 1) continue;
                    else break;
                }
            }
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
        K = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        visit = new int[N][M];
        result = -1;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken()) - 1;
        sy = Integer.parseInt(st.nextToken()) - 1;
        ex = Integer.parseInt(st.nextToken()) - 1;
        ey = Integer.parseInt(st.nextToken()) - 1;

        br.close();
    }
}