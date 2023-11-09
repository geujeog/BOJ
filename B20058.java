import java.util.*;
import java.io.*;

class B20058 {
    static int N;
    static int Q;
    static int[][] arr;
    static List<Integer> Ls;
    static int resultSum;
    static int resultCnt;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        for (Integer L : Ls) {
            storm(L);
            melt();
        }
        resultSum = getResultSum();
        resultCnt = getResultCnt();
    }

    public static int getResultCnt() {
        int cnt = 0;

        boolean[][] visit = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visit[i][j] && arr[i][j] > 0) {
                    cnt = Math.max(cnt, bfs(i, j, visit));
                }
            }
        }

        return cnt;
    }

    public static int bfs(int i, int j, boolean[][] visit) {
        int result = 0;

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (visit[x][y] || arr[x][y] == 0) continue;

            visit[x][y] = true;
            result++;

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));
        }

        return result;
    }

    public static int getResultSum() {
        int sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += arr[i][j];
            }
        }

        return sum;
    }

    public static void melt() {
        int[][] cnt = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tmp = 0;

                if (i-1 >= 0 && arr[i-1][j] > 0) tmp++;
                if (i+1 < N && arr[i+1][j] > 0) tmp++;
                if (j-1 >= 0 && arr[i][j-1] > 0) tmp++;
                if (j+1 < N && arr[i][j+1] > 0) tmp++;

                if (tmp < 3) cnt[i][j] -= 1;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = Math.max(0, arr[i][j] + cnt[i][j]);
            }
        }
    }

    public static void storm(int L) {
        int len = (int) Math.pow(2, L);

        for (int i = 0; i < N; i+=len) {
            for (int j = 0; j < N; j+=len) {
                choice(i, j, len);
            }
        }
    }

    public static void choice(int sx, int sy, int len) {
        int[][] tmp = new int[len][len];

        for (int i = sx; i < sx+len; i++) {
            for (int j = sy; j < sy+len; j++) {
                tmp[i-sx][j-sy] = arr[i][j];
            }
        }

        int[][] turnTmp = turn(tmp, len);

        for (int i = sx; i < sx+len; i++) {
            for (int j = sy; j < sy+len; j++) {
                arr[i][j] = turnTmp[i-sx][j-sy];
            }
        }
    }

    public static int[][] turn(int[][] tmp, int len) {
        int[][] map = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                map[i][j] = tmp[len-j-1][i];
            }
        }

        return map;
    }

    public static class Tuple {
        int x;
        int y;

        public Tuple (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(resultSum+"\n");
        bw.write(resultCnt+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        N = (int) Math.pow(2, n);
        Q = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
        Ls = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            Ls.add(Integer.parseInt(st.nextToken()));
        }

        br.close();
    }
}