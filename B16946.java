import java.util.*;
import java.io.*;

class B16946 {
    static int N, M;
    static int[][] arr, cnt;
    static boolean[][] visit;
    static int islandCnt;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        islandCnt = 2;

        // 1은 벽, 2 이상은 이동 가능한 동일 island
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 0 && !visit[i][j]) {
                    bfs(i, j);
                    islandCnt++;
                }
            }
        }
    }

    public static void bfs(int i, int j) {
        Queue<int[]> queue = new ArrayDeque<>();
        Queue<int[]> countingQueue = new ArrayDeque<>();
        int islandArrCnt = 0;

        queue.add(new int[]{i, j});
        arr[i][j] = islandCnt;

        while (!queue.isEmpty()) {
            int[] t = queue.poll();
            int x = t[0];
            int y = t[1];

            islandArrCnt++;

            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M || visit[nx][ny]) continue;

                visit[nx][ny] = true;

                if (arr[nx][ny] != 0) {
                    if (arr[nx][ny] == 1) {
                        countingQueue.add(new int[]{nx, ny});
                    }
                    continue;
                }

                arr[nx][ny] = islandCnt;
                queue.add(new int[]{nx, ny});
            }
        }

        while (!countingQueue.isEmpty()) {
            int[] t = countingQueue.poll();
            cnt[t[0]][t[1]] += islandArrCnt;
            cnt[t[0]][t[1]] %= 10;
            visit[t[0]][t[1]] = false;
        }
    }

    public static void output() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 1) {
                    sb.append(cnt[i][j] % 10);
                }
                else {
                    sb.append(cnt[i][j]);
                }
            }
            sb.append("\n");
        }

        System.out.print(sb.toString());
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];
        cnt = new int[N][M];
        visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                if (line.charAt(j) == '1') {
                    arr[i][j] = 1;
                    cnt[i][j] = 1;
                }
                else {
                    arr[i][j] = 0;
                }
            }
        }

        br.close();
    }
}