import java.util.*;
import java.io.*;

class B17090 {
    static String direction = "URDL";
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    static int N, M;
    static char[][] arr;
    static Integer[][] isPossible;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        boolean[][] visit = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visit[i][j] = true;
                result += fillDP(i, j, visit);
                visit[i][j] = false;
            }
        }
    }

    public static Integer fillDP(int x, int y, boolean[][] visit) {
        if (isPossible[x][y] == null) {
            int d = direction.indexOf(arr[x][y]);

            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
                isPossible[x][y] = 1;
            }
            else if (visit[nx][ny]) {
                isPossible[x][y] = 0;
            }
            else {
                visit[nx][ny] = true;
                isPossible[x][y] = fillDP(nx, ny, visit);
                visit[nx][ny] = false;
            }
        }

        return isPossible[x][y];
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
        arr = new char[N][M];
        isPossible = new Integer[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}