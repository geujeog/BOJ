import java.util.*;
import java.io.*;

class B16137 {
    static int N, M;
    static int[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 1 - 그냥 이동 가능
        // 0 - 주기에 맞춰 오작교 건너기 (반드시 하나)
        // 2 이상 - 주기에 맞춰 오작교 건너기

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        Queue<Tuple> queue = new ArrayDeque<>();
        Integer[][][] visit = new Integer[2][N][N];

        visit[0][0][0] = 0;
        queue.add(new Tuple(0, 0, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = t.x + dx[d];
                int ny = t.y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (!(arr[t.x][t.y] == 1 || arr[nx][ny] == 1)) continue;

                int nextBuild = t.build + ((arr[nx][ny] == 0) ? 1 : 0);
                if (nextBuild == 2) continue;

                int moveTime = getMoveTime(nx, ny, visit[t.build][t.x][t.y]);

                if (visit[nextBuild][nx][ny] == null || moveTime < visit[nextBuild][nx][ny]) {
                    visit[nextBuild][nx][ny] = moveTime;

                    if (nx == N-1 && ny == N-1) {
                        result = Math.min(result, visit[nextBuild][nx][ny]);
                        continue;
                    }

                    queue.add(new Tuple(nx, ny, nextBuild));
                }
            }
        }
    }

    public static int getMoveTime(int nx, int ny, int nowTime) {
        if (arr[nx][ny] == 1) return nowTime + 1;

        int shareTmp = (arr[nx][ny] == 0) ? M : arr[nx][ny];
        int remain = nowTime / shareTmp;
        return shareTmp * (remain + 1);
    }

    public static class Tuple {
        int x;
        int y;
        int build;

        public Tuple(int x, int y, int build) {
            this.x = x;
            this.y = y;
            this.build = build;
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
        arr = new int[N][N];
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}