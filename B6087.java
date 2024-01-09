import java.util.*;
import java.io.*;

class B6087 {
    static int N, M;
    static char[][] arr;
    static int sx, sy;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<int[]> queue = new LinkedList<>(); // x, y, 방향, 거울카운팅
        Integer[][][] visit = new Integer[N][M][4];
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        for (int d = 0; d < dx.length; d++) {
            queue.add(new int[]{sx, sy, d, 0});
            visit[sx][sy][d] = 0;
        }

        while (!queue.isEmpty()) {
            int[] t = queue.poll();
            int x = t[0];
            int y = t[1];
            int d = t[2];
            int cnt = t[3];

            if (arr[x][y] == 'C' && !(x == sx && y == sy)) {
                result = Math.min(result, visit[x][y][d]);
                continue;
            }

            for (int i = 0; i < dx.length; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                int ncnt = (i != d) ? cnt + 1 : cnt;

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (arr[nx][ny] == '*') continue;
                if (visit[nx][ny][i] != null && visit[nx][ny][i] <= ncnt) continue;

                visit[nx][ny][i] = ncnt;
                queue.add(new int[]{nx, ny, i, ncnt});
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
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'C') {
                    sx = i;
                    sy = j;
                }
            }
        }

        br.close();
    }
}