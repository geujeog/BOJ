import java.util.*;
import java.io.*;

class B16933 {
    static int N, M, K;
    static char[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<Tuple> queue = new LinkedList<>();
        Integer[][] visit = new Integer[N][M];
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        queue.add(new Tuple(0, 0, 1, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int moveCnt = t.moveCnt; // 홀:낮, 짝:밤
            int brokenCnt = t.brokenCnt;

            if (x == N-1 && y == M-1) {
                result = moveCnt;
                break;
            }

            for (int i = 0; i < dx.length; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || ny < 0 || nx == N || ny == M) continue;

                if (arr[nx][ny] == '1') {
                    if (brokenCnt == K) continue;

                    if (moveCnt % 2 == 0) { // 현재는 밤, 이동 불가능
                        queue.add(new Tuple(x, y, moveCnt + 1, brokenCnt));
                    }
                    else { // 현재는 낮, 이동 가능
                        if (visit[nx][ny] != null && visit[nx][ny] <= brokenCnt + 1) continue;

                        visit[nx][ny] = brokenCnt + 1;

                        queue.add(new Tuple(nx, ny, moveCnt + 1, brokenCnt+1));
                    }
                }
                else {
                    if (visit[nx][ny] != null && visit[nx][ny] <= brokenCnt) continue;

                    visit[nx][ny] = brokenCnt;

                    queue.add(new Tuple(nx, ny,moveCnt + 1, brokenCnt));
                }
            }
        }
    }

    public static class Tuple {
        int x;
        int y;
        int moveCnt;
        int brokenCnt;

        public Tuple(int x, int y, int moveCnt, int brokenCnt) {
            this.x = x;
            this.y = y;
            this.moveCnt = moveCnt;
            this.brokenCnt = brokenCnt;
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
        result = -1;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}