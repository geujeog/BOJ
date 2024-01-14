import java.util.*;
import java.io.*;

class B16724 {
    static int N, M;
    static Integer[][] arr;
    static boolean[][] loop;
    static boolean[][] visit;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 루프 찾기
        // 이전에 찾았던 루프인지, 혹은 새로운 루프인지 고려하기
        // 새로운 루프일 경우 최소 safe zone 개수 카운팅

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!loop[i][j]) {
                    visit[i][j] = true;
                    dfs(i, j);
                    visit[i][j] = false;
                    loop[i][j] = true;
                }
            }
        }
    }

    public static void dfs(int x, int y) {
        int nx = x + dx[arr[x][y]];
        int ny = y + dy[arr[x][y]];

        if (loop[nx][ny]) return;
        if (visit[nx][ny]) {
            result++;
            return;
        }

        visit[nx][ny] = true;

        dfs(nx, ny);

        visit[nx][ny] = false;
        loop[nx][ny] = true;
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
        arr = new Integer[N][M];
        loop = new boolean[N][M];
        visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                char c = line.charAt(j);

                if (c == 'D') arr[i][j] = 2;
                else if (c == 'R') arr[i][j] = 3;
                else if (c == 'L') arr[i][j] = 1;
                else arr[i][j] = 0;
            }
        }

        br.close();
    }
}