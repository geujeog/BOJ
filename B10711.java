import java.util.*;
import java.io.*;

class B10711 {
    static int N, M;
    static int[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 강도 <= 주변
        // 모래일 경우, 큐에 넣기
        int[] dx = {-1, 0, 1, 0, -1, -1, 1, 1};
        int[] dy = {0, -1, 0, 1, -1, 1, -1, 1};
        Queue<int[]> timeQueue = initQueue();
        Queue<int[]> queue = new ArrayDeque<>();
        int[][] brokenAround = new int[N][M];

        while (!timeQueue.isEmpty()) {
            queue.addAll(timeQueue);
            timeQueue.clear();

            while (!queue.isEmpty()) {
                int[] q = queue.poll();

                for (int d = 0; d < dx.length; d++) {
                    int nx = q[0] + dx[d];
                    int ny = q[1] + dy[d];

                    if (nx < 0 || ny < 0 || nx >= N || ny >= M || arr[nx][ny] == 0) continue;
                    brokenAround[nx][ny]++;

                    if (arr[nx][ny] <= brokenAround[nx][ny]) {
                        arr[nx][ny] = 0;
                        timeQueue.add(new int[]{nx, ny});
                    }
                }
            }

            if (!timeQueue.isEmpty()) {
                result++;
            }
        }
    }

    public static Queue<int[]> initQueue() {
        Queue<int[]> tmp = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 0) {
                    tmp.add(new int[]{i, j});
                }
            }
        }
        return tmp;
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
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                char input = line.charAt(j);
                if (input == '.') {
                    arr[i][j] = 0;
                }
                else {
                    arr[i][j] = input - '0';
                }
            }
        }

        br.close();
    }
}