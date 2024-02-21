import java.util.*;
import java.io.*;

class B1113 {
    static int N, M;
    static int[][] arr;
    static int[][] waterHeight;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 물 높이만큼 채우기
        // 존재하는 최대 물 양

        for (int k = 2; k <= 9; k++) {
            boolean[][] visit = new boolean[N][M];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (arr[i][j] < k && !visit[i][j]) {
                        fillWater(i, j, k, visit);
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (waterHeight[i][j] > 0) {
                    result += (waterHeight[i][j] - arr[i][j]);
                }
            }
        }
    }

    public static void fillWater(int i, int j, int height, boolean[][] visit) {
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        Queue<int[]> queue = new ArrayDeque<>();
        Queue<int[]> range = new ArrayDeque<>();
        boolean canFill = true;

        visit[i][j] = true;
        queue.add(new int[]{i, j});
        range.add(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
                    canFill = false;
                    continue;
                }

                if (!visit[nx][ny] && arr[nx][ny] < height) {
                    queue.add(new int[]{nx, ny});
                    range.add(new int[]{nx, ny});
                }

                visit[nx][ny] = true;
            }
        }

        if (canFill) {
            while (!range.isEmpty()) {
                int[] q = range.poll();
                waterHeight[q[0]][q[1]] = height;
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
        arr = new int[N][M];
        waterHeight = new int[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j) - '0';
            }
        }

        br.close();
    }
}