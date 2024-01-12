import java.util.*;
import java.io.*;

class B4991 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    static int N, M;
    static char[][] arr;
    static int[][] dist;
    static List<int[]> dirtyList;

    static int result;

    public static void main (String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());

            if (N == 0 && M == 0) break;

            input();
            getDistance();
            choice(0, 1, 0, new boolean[dirtyList.size()]);
            output();
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void choice(int idx ,int cnt, int move, boolean[] visit) {
        if (cnt == dirtyList.size()) {
            result = Math.min(result, move);
            return;
        }

        for (int i = 1; i < dirtyList.size(); i++) {
            if (i == idx || dist[idx][i] == 0 || visit[i]) continue;

            visit[i] = true;
            choice(i, cnt + 1, move + dist[idx][i], visit);
            visit[i] = false;
        }
    }

    public static void getDistance() {
        for (int i = 0; i < dirtyList.size(); i++) {
            bfs(dirtyList.get(i)[0], dirtyList.get(i)[1]);
        }
    }

    public static void bfs(int i, int j) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][M];

        visit[i][j] = true;
        queue.add(new int[]{i, j, 0});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int x = q[0];
            int y = q[1];
            int m = q[2];

            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (visit[nx][ny] || arr[nx][ny] == 'x') continue;

                visit[nx][ny] = true;

                if (arr[nx][ny] != '.') {
                    dist[arr[i][j]][arr[nx][ny]] = dist[arr[nx][ny]][arr[i][j]] = m + 1;
                }

                queue.add(new int[]{nx, ny, m + 1});
            }
        }
    }

    public static void output() throws IOException {
        if (result == Integer.MAX_VALUE) bw.write(-1+"\n");
        else bw.write(result+"\n");
    }

    public static void input() throws IOException {
        arr = new char[N][M];
        dirtyList = new ArrayList<>();
        result = Integer.MAX_VALUE;

        int dirtyCnt = 0;
        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == '*') {
                    arr[i][j] = (char) ++dirtyCnt;
                    dirtyList.add(new int[]{i, j});
                }
                else if (arr[i][j] == 'o') {
                    arr[i][j] = (char) 0;
                    dirtyList.add(0, new int[]{i, j});
                }
            }
        }

        dist = new int[dirtyList.size()][dirtyList.size()];
    }
}