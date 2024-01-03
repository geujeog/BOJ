import java.util.*;
import java.io.*;

class B1939 {
    static int N;
    static List<Tuple>[] list;
    static int s, e;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        queue.add(new Tuple(s, Integer.MAX_VALUE));

        int[] dist = new int[N+1];

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int v = t.v;
            int w = t.w;

            if (dist[v] > w) continue;
            if (v == e) {
                result = Math.max(result, w);
                continue;
            }

            for (Tuple tuple : list[v]) {
                if (dist[tuple.v] < Math.min(w, tuple.w)) {
                    dist[tuple.v] = Math.min(w, tuple.w);
                    queue.add(new Tuple(tuple.v, dist[tuple.v]));
                }
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int v;
        int w;

        public Tuple(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Tuple t) {
            return t.w - this.w;
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
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            list[a].add(new Tuple(b, w));
            list[b].add(new Tuple(a, w));
        }

        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        br.close();
    }
}