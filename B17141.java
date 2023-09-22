import java.util.*;
import java.io.*;

class B17141 {
    static int N;
    static int M;
    static int[][] arr;
    static List<Tuple> virus;
    static int[][][] virusTime;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        for (int i = 0; i < virus.size(); i++) {
            Tuple tuple = virus.get(i);
            bfs(tuple.x, tuple.y, i);
        }

        result = Integer.MAX_VALUE;

        int[][] tmp  = initMAX();

        choice(0, tmp, 0);
    }

    public static void choice(int idx, int[][] total, int cnt) {
        if (cnt == M) {

            int maxTime = getMaxTime(total);
            if (maxTime != -1) {
                result = Math.min(result, maxTime);
            }
            return;
        }
        if (idx == virus.size()) return;

        choice(idx+1, copy(total), cnt);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                total[i][j] = Math.min(total[i][j], virusTime[idx][i][j]);
            }
        }

        choice(idx+1, copy(total), cnt+1);
    }

    public static int getMaxTime(int[][] total) {
        int max = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == 1) continue;

                if (total[i][j] == Integer.MAX_VALUE) return -1;
                else max = Math.max(max, total[i][j]);
            }
        }

        return max;
    }

    public static int[][] copy(int[][] time) {
        int[][] copyTime = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copyTime[i][j] = time[i][j];
            }
        }

        return copyTime;
    }

    public static void bfs(int i, int j, int virusIdx) {
        boolean[][] visit = new boolean[N][N];

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j, 0));

        virusTime[virusIdx] = initMAX();

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int cnt = tuple.cnt;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (visit[x][y] || arr[x][y] == 1) continue;

            visit[x][y] = true;
            virusTime[virusIdx][x][y] = cnt;

            queue.add(new Tuple(x-1, y, cnt+1));
            queue.add(new Tuple(x+1, y, cnt+1));
            queue.add(new Tuple(x, y-1, cnt+1));
            queue.add(new Tuple(x, y+1, cnt+1));
        }

        virusTime[virusIdx][i][j] = -1;
    }

    public static int[][] initMAX() {
        int[][] tmp = new int[N][N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(tmp[i], Integer.MAX_VALUE);
        }

        return tmp;
    }

    public static class Tuple{
        int x;
        int y;
        int cnt;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Tuple(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (result == Integer.MAX_VALUE) bw.write(-1+"");
        else bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
        virus = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());

                if (arr[i][j] == 2) virus.add(new Tuple(i, j));
            }
        }

        virusTime = new int[virus.size()][N][N];

        br.close();
    }
}