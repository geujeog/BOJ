import java.util.*;
import java.io.*;

class B4179 {
    static int N, M;
    static char[][] arr;
    static int jx, jy;
    static List<int[]> fires;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<int[]> queue = new ArrayDeque<>();
        int[][] time = new int[N][M];
        boolean[][] visit = new boolean[N][M];
        int[] q;
        int nx, ny;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0 ,1};

        // 불
        for (int[] f: fires) {
            time[f[0]][f[1]] = 0;
            queue.add(new int[]{f[0], f[1]});
        }
        while (!queue.isEmpty()) {
            q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                nx = q[0] + dx[d];
                ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

                if (arr[nx][ny] == '.') {
                    arr[nx][ny] = 'F';
                    time[nx][ny] = time[q[0]][q[1]] + 1;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        queue.add(new int[]{jx, jy, 0});
        while (!queue.isEmpty()) {
            q = queue.poll();

            for (int d = 0; d < dx.length; d++) {
                nx = q[0] + dx[d];
                ny = q[1] + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
                    result = q[2] + 1;
                    return;
                }

                if (!visit[nx][ny] && arr[nx][ny] != '#') {
                    if (arr[nx][ny] == 'F' && q[2] + 1 >= time[nx][ny]) continue;

                    visit[nx][ny] = true;
                    queue.add(new int[]{nx, ny, q[2] + 1});
                }
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (result == -1) bw.write("IMPOSSIBLE");
        else bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        fires = new ArrayList<>();
        result = -1;
        String line;

        for (int i = 0; i < N; i++) {
            line = br.readLine();
            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'J') {
                    jx = i;
                    jy = j;
                    arr[i][j] = '.';
                }
                else if (arr[i][j] == 'F') {
                    fires.add(new int[]{i, j});
                }
            }
        }

        br.close();
    }
}