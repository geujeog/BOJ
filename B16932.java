import java.util.*;
import java.io.*;

class B16932 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    static int N, M;
    static int[][] arr;
    static Map<Integer, Integer> island;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // island 별 개수
        // 0 -> 1 변경 후 연결된 island 개수 카운팅

        getIsland();
        changeIsland();
    }

    public static void changeIsland() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 0) {
                    result = Math.max(result, getLinkIsland(i, j));
                }
            }
        }
    }

    public static int getLinkIsland(int x, int y) {
        Set<Integer> set = new HashSet<>();
        for (int d = 0; d < dx.length; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx < 0 || ny < 0 || nx >= N || ny >= M || arr[nx][ny] == 0) continue;

            set.add(arr[nx][ny]);
        }

        int sum = 0;
        for (Integer linkedIsland : set) {
            sum += island.get(linkedIsland);
        }

        return sum + 1;
    }

    public static void getIsland() {
        int islandCnt = 2;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 1) {
                    getIslandBFS(i, j, islandCnt++);
                }
            }
        }
    }

    public static void getIslandBFS(int i, int j, int islandNumber) {
        Queue<int[]> queue = new ArrayDeque<>();
        int cnt = 1;

        arr[i][j] = islandNumber;
        queue.add(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M || arr[nx][ny] != 1) continue;

                cnt++;
                arr[nx][ny] = islandNumber;
                queue.add(new int[]{nx, ny});
            }
        }

        island.put(islandNumber, cnt);
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
        arr = new int[N][M];
        island = new HashMap<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}