import java.util.*;
import java.io.*;

class B23288 {
    static int[] dx = {0, -1, 0, 1};
    static int[] dy = {-1, 0, 1, 0};

    static int N, M, K;
    static int[][] arr;
    static int sx, sy, dir;
    static int[][] dice;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        while (K-- > 0) {
            roll();
            getScore();
            calculateDir();
        }
    }

    public static void calculateDir() {
        int A = dice[3][1];
        int B = arr[sx][sy];

        if (A > B) {
            dir = (dir + 1) % 4;
        }
        else if (A < B) {
            dir = (dir + 3) % 4;
        }
    }

    public static void getScore() {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][M];

        int cnt = 1;
        visit[sx][sy] = true;
        queue.add(new int[]{sx, sy});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int x = q[0];
            int y = q[1];

            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (visit[nx][ny] || arr[nx][ny] != arr[sx][sy]) continue;

                cnt++;
                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }

        result += (cnt * arr[sx][sy]);
    }

    public static void roll() {
        int nx = sx + dx[dir];
        int ny = sy + dy[dir];

        if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
            dir = (dir + 2) % 4;
            nx = sx + dx[dir];
            ny = sy + dy[dir];
        }

        sx = nx;
        sy = ny;
        rollDice();
    }

    public static void rollDice() {
        if (dir == 0) {
            int tmp = dice[1][1];
            dice[1][1] = dice[1][2];
            dice[1][2] = dice[3][1];
            dice[3][1] = dice[1][0];
            dice[1][0] = tmp;
        }
        else if (dir == 1) {
            int tmp = dice[0][1];
            dice[0][1] = dice[1][1];
            dice[1][1] = dice[2][1];
            dice[2][1] = dice[3][1];
            dice[3][1] = tmp;
        }
        else if (dir == 2) {
            int tmp = dice[1][1];
            dice[1][1] = dice[1][0];
            dice[1][0] = dice[3][1];
            dice[3][1] = dice[1][2];
            dice[1][2] = tmp;
        }
        else {
            int tmp = dice[3][1];
            dice[3][1] = dice[2][1];
            dice[2][1] = dice[1][1];
            dice[1][1] = dice[0][1];
            dice[0][1] = tmp;
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
        arr = new int[N][M];
        sx = sy = 0;
        dir = 2;
        dice = new int[4][3];

        dice[1][1] = 1;
        dice[0][1] = 2;
        dice[1][0] = 4;
        dice[1][2] = 3;
        dice[2][1] = 5;
        dice[3][1] = 6;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}