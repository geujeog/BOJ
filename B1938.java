import java.util.*;
import java.io.*;

class B1938 {
    static int N, len;
    static char[][] arr;
    static int[][][] visit; // 가운데 통나무 기준
    static int[][] stick;
    static int[] dx = {-1, 0, 1, 0, -1, -1, 1, 1};
    static int[] dy = {0, -1, 0, 1, -1, 1, -1, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int shape = (Math.abs(stick[0][0] - stick[1][0]) == 1) ? 0 : 1;

        visit[shape][stick[1][0]][stick[1][1]] = 1;
        dfs(shape, 1);
    }

    public static void dfs(int shape, int move) {
        if (checkCorrect()) {
            result = move - 1;
            return;
        }

        // 회전
        if (canTurn()) {
            turn();

            int nextShape = (shape + 1) % 2;
            if (!isVisit(nextShape, move + 1)) {
                dfs(nextShape, move + 1);
            }

            turn();
        }

        for (int d = 0; d < 4; d++) {
            if (canMove(d)) {
                move(d);

                if (!isVisit(shape, move + 1)) {
                    dfs(shape, move + 1);
                }

                move((d + 2) % 4);
            }
        }
    }

    public static void move(int d) {
        for (int s = 0; s < len; s++) {
            stick[s][0] = stick[s][0] + dx[d];
            stick[s][1] = stick[s][1] + dy[d];
        }
    }

    public static boolean canMove(int d) {
        for (int s = 0; s < len; s++) {
            int nx = stick[s][0] + dx[d];
            int ny = stick[s][1] + dy[d];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N) return false;
            if (arr[nx][ny] == '1') return false;
        }

        return true;
    }

    public static boolean checkCorrect() {
        for (int s = 0; s < len; s++) {
            if (arr[stick[s][0]][stick[s][1]] != 'E') {
                return false;
            }
        }
        return true;
    }

    public static boolean isVisit(int shape, int movement) {
        int standard = visit[shape][stick[1][0]][stick[1][1]];

        if (standard != 0 && standard <= movement) return true;

        visit[shape][stick[1][0]][stick[1][1]] = movement;
        return false;
    }

    public static void turn() {
        if (stick[0][0] == stick[1][0]) {
            stick[0][1] = stick[2][1] = stick[1][1];
            stick[0][0] = stick[1][0] - 1;
            stick[2][0] = stick[1][0] + 1;
        }
        else {
            stick[0][0] = stick[2][0] = stick[1][0];
            stick[0][1] = stick[1][1] - 1;
            stick[2][1] = stick[1][1] + 1;
        }
    }

    public static boolean canTurn() {
        for (int d = 0; d < dx.length; d++) {
            int nx = stick[1][0] + dx[d];
            int ny = stick[1][1] + dy[d];

            if (nx < 0 || ny < 0 || nx >= N || ny >= N || arr[nx][ny] == '1') {
                return false;
            }
        }

        return true;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        len = 3;
        arr = new char[N][N];
        visit = new int[2][N][N];
        stick = new int[len][2];

        for (int s = 0; s < len; s++) {
            stick[s][0] = -1;
        }

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < N; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'B') {
                    for (int s = 0; s < len; s++) {
                        if (stick[s][0] == -1) {
                            stick[s][0] = i;
                            stick[s][1] = j;
                            break;
                        }
                    }
                }
            }
        }

        br.close();
    }
}