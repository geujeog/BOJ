import java.util.*;
import java.io.*;

class B18809 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    static int N, M, R, G;
    static int[][] arr;
    static List<int[]> growSites;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 배양액 뿌리는 경우의 수
        // BFS - 배양액 뿌리기
        // 최대 피울 수 있는 꽃 개수

        greenCombinate(0, 0, new boolean[growSites.size()]);
    }

    public static void greenCombinate(int idx, int cnt, boolean[] greenVisit) {
        if (cnt == G) {
            redCombinate(0, 0, new boolean[growSites.size()], greenVisit);
            return;
        }

        for (int i = idx; i < growSites.size(); i++) {
            greenVisit[i] = true;
            greenCombinate(i+1, cnt + 1, greenVisit);
            greenVisit[i] = false;
        }
    }

    public static void redCombinate(int idx, int cnt, boolean[] redVisit, boolean[] greenVisit) {
        if (cnt == R) {
            bfs(extractSites(greenVisit), extractSites(redVisit));
            return;
        }

        for (int i = idx; i < growSites.size(); i++) {
            if (!greenVisit[i]) {
                redVisit[i] = true;
                redCombinate(i + 1, cnt + 1, redVisit, greenVisit);
                redVisit[i] = false;
            }
        }
    }

    public static List<int[]> extractSites(boolean[] visit) {
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < growSites.size(); i++) {
            if (visit[i]) {
                list.add(growSites.get(i));
            }
        }

        return list;
    }

    public static void bfs(List<int[]> greenSites, List<int[]> redSites) {
        Queue<int[]> queue = new ArrayDeque<>();
        char[][] visit = new char[N][M];
        int[][] time = new int[N][M];

        for (int[] green : greenSites) {
            visit[green[0]][green[1]] = 'G';
            time[green[0]][green[1]] = 1;
            queue.add(green);
        }
        for (int[] red : redSites) {
            visit[red[0]][red[1]] = 'R';
            time[red[0]][red[1]] = 1;
            queue.add(red);
        }

        int cnt = 0;

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            if (visit[q[0]][q[1]] == 'S') continue;

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M || arr[nx][ny] == 0) continue;
                if (visit[nx][ny] != 0) {
                    if (visit[nx][ny] == visit[q[0]][q[1]] || visit[nx][ny] == 'S') continue;
                    else {
                        if (time[nx][ny] == time[q[0]][q[1]] + 1) {
                            visit[nx][ny] = 'S';
                            cnt++;
                        }
                        continue;
                    }
                }

                visit[nx][ny] = visit[q[0]][q[1]];
                time[nx][ny] = time[q[0]][q[1]] + 1;
                queue.add(new int[]{nx, ny});
            }
        }

        result = Math.max(result, cnt);
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
        G = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        arr = new int[N][M];
        growSites = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());

                if (arr[i][j] == 2) {
                    growSites.add(new int[]{i, j});
                }
            }
        }

        br.close();
    }
}