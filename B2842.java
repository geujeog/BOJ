import java.util.*;
import java.io.*;

class B2842 {
    static int N, K;
    static int sx, sy;
    static char[][] arr;
    static int[][] stress;
    static List<Integer> list;
    static int minStress, maxStress;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Collections.sort(list);

        int s = 0;
        int e = list.indexOf(maxStress);

        while (s <= e && s < list.indexOf(minStress) && e < list.size()) {
            int min = list.get(s);
            int max = list.get(e);
            boolean flag = false;

            if (maxStress - minStress <= max - min) {
                if (bfs(min, max)) {
                    result = Math.min(result, max - min);
                    s++;
                    flag = true;
                }
            }

            if (!flag) e++;
        }
    }

    public static boolean bfs(int min, int max) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[N][N];
        int kCnt = 0;
        int[] dx = {-1, 0, 1, 0, -1, -1, 1, 1};
        int[] dy = {0, -1, 0, 1, -1, 1, -1, 1};

        visit[sx][sy] = true;
        queue.add(new int[]{sx, sy});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                int nx = q[0] + dx[d];
                int ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visit[nx][ny] || stress[nx][ny] < min || stress[nx][ny] > max) continue;

                visit[nx][ny] = true;

                if (arr[nx][ny] == 'K') kCnt++;

                queue.add(new int[]{nx, ny});
            }
        }

        if (kCnt == K) return true;
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
        arr = new char[N][N];
        stress = new int[N][N];
        list = new ArrayList<>();
        minStress = Integer.MAX_VALUE;
        maxStress = 0;
        result = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                stress[i][j] = Integer.parseInt(st.nextToken());

                if (!list.contains(stress[i][j])) {
                    list.add(stress[i][j]);
                }

                if (arr[i][j] != '.') {
                    minStress = Math.min(minStress, stress[i][j]);
                    maxStress = Math.max(maxStress, stress[i][j]);

                    if (arr[i][j] == 'K') K++;
                    else {
                        sx = i;
                        sy = j;
                    }
                }
            }
        }

        br.close();
    }
}