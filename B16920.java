import java.util.*;
import java.io.*;

class B16920 {
    static int N, M, P;
    static int[] movement;
    static char[][] arr;
    static int[] count;
    static Queue<int[]>[] startArr;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        bfs();
    }

    public static void bfs() {
        Queue<int[]> queue = new ArrayDeque<>();

        while (checkLoop()) {
            for (int p = 1; p <= P; p++) {
                while (!startArr[p].isEmpty()) {
                    queue.add(startArr[p].poll());
                }

                while (!queue.isEmpty()) {
                    int[] q = queue.poll();
                    int x = q[0];
                    int y = q[1];
                    int move = q[2];

                    for (int d = 0; d < dx.length; d++) {
                        int nx = x + dx[d];
                        int ny = y + dy[d];

                        if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

                        if (arr[nx][ny] == '.') {
                            int idx = arr[x][y] - '0';
                            arr[nx][ny] = arr[x][y];
                            count[idx]++;

                            if (move - 1 == 0) {
                                startArr[p].add(new int[]{nx, ny, movement[idx]});
                            } else {
                                queue.add(new int[]{nx, ny, move - 1});
                            }
                        }
                    }
                }
            }

        }
    }

    public static boolean checkLoop() {
        for (int p = 1; p <= P; p++) {
            if (startArr[p].size() > 0) {
                return true;
            }
        }
        return false;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i = 1; i <= P; i++) {
            bw.write(count[i]+" ");
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        movement = new int[P+1];
        count = new int[P+1];
        startArr = new ArrayDeque[P+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= P; i++) {
            movement[i] = Integer.parseInt(st.nextToken());
            startArr[i] = new ArrayDeque<>();
        }

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] != '.' && arr[i][j] != '#') {
                    int idx = arr[i][j] - '0';
                    startArr[idx].add(new int[]{i, j, movement[idx]});
                    count[idx]++;
                }
            }
        }

        br.close();
    }
}