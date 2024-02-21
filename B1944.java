import java.util.*;
import java.io.*;

class B1944 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    static int N, M;
    static int[][] arr; // 벽은 -1. 빈공간은 0, S나 K는 1부터
    static List<int[]> copySites;
    static List<int[]>[] distance;
    static long result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // S와 K의 모든 경로 조합
        // 최소 구하기

        if (!canGo()) {
            result = -1;
            return;
        }
        getDistance();
        combination();
    }

    public static void combination() {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        boolean[] visit = new boolean[M + 1];

        queue.add(new Tuple(1, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();

            if (visit[t.v]) continue;
            else visit[t.v] = true;

            result += t.w;

            for (int[] dist : distance[t.v]) {
                if (!visit[dist[0]]) {
                    queue.add(new Tuple(dist[0], dist[1]));
                }
            }
        }
    }

    public static void getDistance() {
        for (int[] site : copySites) {
            getDistanceBFS(site[0], site[1], arr[site[0]][site[1]]);
        }
    }

    public static void getDistanceBFS(int i, int j, int idx) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];

        visit[i][j] = true;
        queue.add(new int[]{i ,j, 0});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visit[nx][ny] || arr[nx][ny] == -1) continue;

                if (arr[nx][ny] != 0) {
                    distance[idx].add(new int[]{arr[nx][ny], q[2] + 1});
                }
                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny, q[2] + 1});
            }
        }
    }

    public static boolean canGo() {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        int cnt = 1;

        int[] start = copySites.get(0);
        visit[start[0]][start[1]] = true;
        queue.add(new int[]{start[0], start[1]});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visit[nx][ny] || arr[nx][ny] == -1) continue;

                if (arr[nx][ny] != 0) {
                    cnt++;
                }
                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }

        if (cnt == M) return true;
        return false;
    }

    public static class Tuple implements Comparable<Tuple> {
        int v;
        int w;

        public Tuple (int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.w - t.w;
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
        copySites = new ArrayList<>();

        int kCnt = 0;
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                char input = line.charAt(j);

                if (input == '0') {
                    arr[i][j] = 0;
                } else if (input == '1') {
                    arr[i][j] = -1;
                }
                else {
                    arr[i][j] = ++kCnt;
                    copySites.add(new int[]{i, j});
                }
            }
        }

        M = copySites.size();
        distance = new ArrayList[M + 1];

        for (int i = 1; i <= M; i++) {
            distance[i] = new ArrayList<>();
        }

        br.close();
    }
}