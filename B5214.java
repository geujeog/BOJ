import java.util.*;
import java.io.*;

class B5214 {
    static int N, M;
    static List<Integer>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        if (N == 1) {
            result = 1;
            return;
        }

        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        boolean[] visit = new boolean[N + M + 1];

        queue.add(new Tuple(1, 1));
        visit[1] = true;

        while (!queue.isEmpty() && result == -1) {
            Tuple q = queue.poll();
            int v = q.v;
            int cnt = q.cnt;

            for (Integer i : list[v]) {
                if (!visit[i]) {
                    int p = (i <= N) ? 1 : 0;

                    if (i == N) {
                        result = cnt + p;
                        break;
                    }

                    visit[i] = true;
                    queue.add(new Tuple(i, cnt + p));
                }
            }
        }
    }

    public static class Tuple implements Comparable<Tuple>{
        int v;
        int cnt;

        public Tuple(int v, int cnt) {
            this.v = v;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.cnt - t.cnt;
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
        N = Integer.parseInt(st.nextToken()); // 1 ~ N+M
        int K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        result = -1;

        list = new ArrayList[N + M + 1];
        for (int i = 1; i <= N + M; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 1; i <= M; i++) {
            int idx = N + i;

            st = new StringTokenizer(br.readLine());
            for (int k = 0; k < K; k++) {
                int station = Integer.parseInt(st.nextToken());

                list[station].add(idx);
                list[idx].add(station);
            }
        }

        br.close();
    }
}