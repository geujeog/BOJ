import java.util.*;
import java.io.*;

class B1348 {
    static int N, M;
    static char[][] arr;
    static int[][] carNum, parkNum;
    static List<int[]> car, park;
    static int[][] distance;
    static List<Integer>[] link;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // bfs
        // 이분탐색 + 이분매칭

        result = -1;
        if (car.size() > park.size()) return;
        if (car.size() == 0) {
            result = 0;
            return;
        }

        getDistance();
        parking();
    }

    public static void parking() {
        int s, e, mid;
        s = 0;
        e = N*M;

        while (s <= e) {
            mid = (s + e) / 2;

            if (canGo(mid)) {
                e = mid - 1;
                result = mid;
            }
            else {
                s = mid + 1;
            }
        }
    }

    public static boolean canGo(int maxDistance) {
        int cnt = 0;
        int[] parkOwner = new int[park.size()+1]; // 주차장 주인
        boolean[] visit = new boolean[park.size()+1];

        for (int i = 1; i <= car.size(); i++) {
            Arrays.fill(visit, false);
            if (dfs(i, maxDistance, parkOwner, visit)) {
                cnt++;
            }
        }

        return cnt == car.size();
    }

    public static boolean dfs(int v, int maxDistance, int[] parkOwner, boolean[] visit) {
        for (int next: link[v]) {
            if (visit[next] || distance[v][next] > maxDistance) continue;
            visit[next] = true;

            if (parkOwner[next] == 0 || dfs(parkOwner[next], maxDistance, parkOwner, visit)) {
                parkOwner[next] = v;
                return true;
            }
        }

        return false;
    }

    public static void getDistance() {
        link = new ArrayList[car.size()+1];
        distance = new int[car.size()+1][park.size()+1];

        for (int i = 1; i <= car.size(); i++) {
            link[i] = new ArrayList<>();
        }

        for (int[] c: car) {
            bfs(c[0], c[1]);
        }
    }

    public static void bfs(int sx, int sy) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][M];
        int[] q;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        int nx, ny;

        queue.add(new int[]{sx, sy, 0});
        visit[sx][sy] = true;

        while (!queue.isEmpty()) {
            q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                nx = q[0] + dx[d];
                ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (visit[nx][ny] || arr[nx][ny] == 'X') continue;

                if (arr[nx][ny] == 'P') {
                    link[carNum[sx][sy]].add(parkNum[nx][ny]);
                    distance[carNum[sx][sy]][parkNum[nx][ny]] = q[2] + 1;
                }

                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny, q[2] + 1});
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
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        car = new ArrayList<>();
        park = new ArrayList<>();
        carNum = new int[N][M];
        parkNum = new int[N][M];
        String line;

        for (int i = 0; i < N; i++) {
            line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'C') {
                    car.add(new int[]{i, j});
                    carNum[i][j] = car.size();
                }
                else if (arr[i][j] == 'P') {
                    park.add(new int[]{i, j});
                    parkNum[i][j] = park.size();
                }
            }
        }

        br.close();
    }
}