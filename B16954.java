import java.util.*;
import java.io.*;

class B16954 {
    static int N = 8;
    static int sx, sy;
    static char[][] arr;
    static int possible;
    static int[] dx = {0, -1, 1, 0, 0, -1, -1, 1, 1};
    static int[] dy = {0, 0, 0, -1, 1, -1, 1, -1, 1};

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{sx, sy});

        while (possible == 0 && !queue.isEmpty()) {
            queue = moveCharacter(queue);
            moveChess();
        }
    }

    public static void moveChess() {
        for (int i = N-1; i >= 0; i--) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == '#') {
                    arr[i][j] = '.';
                    if (i != N-1) arr[i+1][j] = '#';
                }
            }
        }
    }

    public static Queue<int[]> moveCharacter(Queue<int[]> queue) {
        Queue<int[]> res = new ArrayDeque<>();

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int x = q[0];
            int y = q[1];

            if (arr[x][y] == '#') continue;

            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (arr[nx][ny] == '#') continue;

                if (nx == 0 && ny == N-1) {
                    possible = 1;
                    break;
                }

                res.add(new int[]{nx, ny});
            }
        }

        return res;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(possible+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sx = N-1;
        sy = 0;
        arr = new char[N][N];
        possible = 0;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < N; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}