import java.util.*;
import java.io.*;

class B1981 {
    static int N;
    static int[][] arr;
    static List<Integer> list;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int min = Math.min(arr[0][0], arr[N-1][N-1]);
        int max = Math.max(arr[0][0], arr[N-1][N-1]);

        Collections.sort(list);

        int s = 0;
        int e = list.indexOf(max);

        while (s <= e && s <= list.indexOf(min) && e < list.size()) {
            int minStandard = list.get(s);
            int maxStandard = list.get(e);
            int diff = maxStandard - minStandard;

            if (bfs(minStandard, maxStandard)) {
                result = Math.min(result, diff);
                s++;
            }
            else e++;
        }
    }

    public static boolean bfs(int min, int max) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];

        visit[0][0] = true;
        queue.add(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visit[nx][ny] || arr[nx][ny] < min || max < arr[nx][ny]) continue;

                if (nx == N-1 && ny == N-1) {
                    return true;
                }

                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }

        return false;
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
        arr = new int[N][N];
        list = new ArrayList<>();
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());

                if (!list.contains(arr[i][j])) {
                    list.add(arr[i][j]);
                }
            }
        }

        br.close();
    }
}