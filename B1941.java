import java.util.*;
import java.io.*;

class B1941 {
    static int N;
    static char[][] arr;
    static char[] arrLine;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 0; i < N*N; i++) {
            int sCnt = (arrLine[i] == 'S') ? 1 : 0;
            int yCnt = (arrLine[i] == 'Y') ? 1 : 0;

            boolean[][] contains = new boolean[N][N];

            contains[i / N][i % N] = true;
            choice(i, sCnt, yCnt, contains);
            contains[i / N][i % N] = false;
        }
    }

    public static void choice(int idx, int sCnt, int yCnt, boolean[][] contains) {
        if (yCnt > 3) return;
        if (sCnt + yCnt == 7) {
            if (isPossible(contains)) {
                result++;
            }
            return;
        }

        for (int i = idx+1; i < N*N; i++) {
            int sp = (arrLine[i] == 'S') ? 1 : 0;
            int yp = (arrLine[i] == 'Y') ? 1 : 0;

            contains[i / N][i % N] = true;
            choice(i, sCnt + sp, yCnt + yp, contains);
            contains[i / N][i % N] = false;
        }
    }

    public static boolean isPossible(boolean[][] contains) {
        int[] start = getStart(contains);

        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        int cnt = 1;

        visit[start[0]][start[1]] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (!contains[nx][ny] || visit[nx][ny]) continue;

                cnt++;
                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }

        if (cnt == 7) return true;
        return false;
    }

    public static int[] getStart(boolean[][] contains) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (contains[i][j]) {
                    return new int[] {i, j};
                }
            }
        }

        return new int[2];
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = 5;
        arr = new char[N][N];
        arrLine = new char[N*N];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                arr[i][j] = line.charAt(j);
                arrLine[i * N + j] = arr[i][j];
            }
        }

        br.close();
    }
}