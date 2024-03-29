import java.util.*;
import java.io.*;

class B11967 {
    static int N;
    static List<int[]>[][] light;
    static boolean[][] on;
    static boolean[][] visited;
    static boolean[][] stop;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 현재 위치에서 스위치 on
        // 불 켜진 곳에서 bfs를 통해 현재 위치까지 도달할 수 있으면 큐에 넣기

        Queue<int[]> queue = new ArrayDeque<>();

        result = 1;
        on[1][1] = true;
        visited[1][1] = true;
        queue.add(new int[]{1, 1});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int x = q[0];
            int y = q[1];

            // turn on
            for (int[] l : light[x][y]) {
                if (!on[l[0]][l[1]]) {
                    on[l[0]][l[1]] = true;
                    result++;

                    if (stop[l[0]][l[1]]) {
                        queue.add(new int[]{l[0], l[1]});
                    }
                }
            }

            //
            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx == 0 || ny == 0 || nx > N || ny > N) continue;
                if (visited[nx][ny]) continue;

                if (on[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
                else {
                    stop[nx][ny] = true;
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
        int M = Integer.parseInt(st.nextToken());
        light = new List[N+1][N+1];
        on = new boolean[N+1][N+1];
        visited = new boolean[N+1][N+1];
        stop = new boolean[N+1][N+1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                light[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            light[x][y].add(new int[]{a, b});
        }

        br.close();
    }
}