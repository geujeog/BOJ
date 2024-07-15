import java.util.*;
import java.io.*;

class B5547 {
    static int N, M;
    static int[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // BFS로 건물 외벽 확인
        // 빈공간이 건물로 둘러쌓여 있을 경우에는 조명 X
        // (0,0)을 시작으로 빈 건물만 이동해 외벽 확인
        boolean[][] visit = new boolean[N+2][M+2];

        for (int i = 0; i < N+2; i++) {
            for (int j = 0; j < M+2; j++) {
                if (i == 0 || j == 0 || i == N+1 || j == M+1) {
                    if (!visit[i][j] && arr[i][j] == 0) {
                        visit[i][j] = true;
                        bfs(i, j, visit);
                    }
                }
            }
        }
    }

    public static void bfs(int i, int j, boolean[][] visit) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{i, j});

        int[] q = new int[2];
        int x, y;
        int[] dx = {0, 0, -1, -1, +1, +1};
        int[][] dy = {{-1, +1, -1, 0, -1, 0}, {-1, +1, 0, +1, 0, +1}};

        while (!queue.isEmpty()) {
            q = queue.poll();
            x = q[0];
            y = q[1];

            int d = x % 2;
            for (int dd = 0; dd < dx.length; dd++) {
                int nx = x + dx[dd];
                int ny = y + dy[d][dd];

                if (nx < 0 || ny < 0 || nx >= N+2 || ny >= M+2) continue;
                if (arr[nx][ny] == 0) {
                    if (!visit[nx][ny]) {
                        visit[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                    }
                }
                else {
                    result++;
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
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        arr = new int[N+2][M+2];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}