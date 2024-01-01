import java.util.*;
import java.io.*;

class B1167 {
    static int N;
    static List<Tuple>[] list;
    static int farV;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        result = Math.max(result, bfs(1));
        result = Math.max(result, bfs(farV));
    }

    public static int bfs(int s) {
        int max = 0;

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(s, 0));

        boolean[] visit = new boolean[N+1];

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int v = t.v;
            int w = t.w;

            if (visit[v]) continue;
            else visit[v] = true;

            if (max < w) {
                max = w;
                farV = v;
            }

            for (Tuple tuple : list[v]) {
                queue.add(new Tuple(tuple.v, w + tuple.w));
            }
        }

        return max;
    }

    public static class Tuple {
        int v;
        int w;

        public Tuple(int v, int w) {
            this.v = v;
            this.w = w;
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

        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N+1];

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int v1 = Integer.parseInt(st.nextToken());

            while (true) {
                int v2 = Integer.parseInt(st.nextToken());

                if (v2 == -1) break;
                else {
                    int w = Integer.parseInt(st.nextToken());
                    list[v1].add(new Tuple(v2, w));
                    list[v2].add(new Tuple(v1, w));
                }
            }
        }

        br.close();
    }
}