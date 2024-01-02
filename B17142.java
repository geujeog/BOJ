import java.util.*;
import java.io.*;

class B17142 {
    static int N;
    static int M;
    static int[][] arr;
    static Map<Integer, Tuple> map;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        choice(1, new ArrayList<Tuple>());
    }

    public static void choice(int idx, List<Tuple> tmp) {
        if (tmp.size() == M) {
            spread(tmp);
            return;
        }
        if (idx > map.size()) return;

        choice(idx+1, tmp);

        tmp.add(map.get(idx));
        choice(idx+1, tmp);
        tmp.remove(map.get(idx));
    }

    public static void spread(List<Tuple> tmp) {
        Queue<Tuple> queue = new LinkedList<>();
        for (Tuple t : tmp) {
            queue.add(new Tuple(t.a, t.b, 0));
        }

        Integer[][] visit = new Integer[N][N];

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.a;
            int y = t.b;
            int time = t.time;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (arr[x][y] == 1 || visit[x][y] != null) continue;

            visit[x][y] = time;

            queue.add(new Tuple(x-1, y, time + 1));
            queue.add(new Tuple(x+1, y, time + 1));
            queue.add(new Tuple(x, y-1, time + 1));
            queue.add(new Tuple(x, y+1, time + 1));
        }

        int maxTime = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == 1 || arr[i][j] == 2) continue;
                if (visit[i][j] == null) return;
                maxTime = Math.max(maxTime, visit[i][j]);
            }
        }

        result = Math.min(result, maxTime);
    }

    public static class Tuple {
        int a;
        int b;
        int time;

        public Tuple(int a, int b, int time) {
            this.a = a;
            this.b = b;
            this.time = time;
        }

        public Tuple(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (result == Integer.MAX_VALUE) bw.write(-1+"");
        else bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
        map = new HashMap<>();
        result = Integer.MAX_VALUE;

        int cnt = 1;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {

                arr[i][j] = Integer.parseInt(st.nextToken());

                if (arr[i][j] == 2) {
                    map.put(cnt++, new Tuple(i, j));
                }
            }
        }

        br.close();
    }
}