import java.util.*;
import java.io.*;

class B15971 {
    static int N;
    static int a, b;
    static List<Tuple>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<Tuple> queue = new ArrayDeque<>();
        int[] visit = new int[N+1];
        Tuple t;
        int max;

        Arrays.fill(visit, -1);
        queue.add(new Tuple(a, 0, 0));
        visit[a] = 0;

        while (!queue.isEmpty()) {
            t = queue.poll();

            for (Tuple next : list[t.v]) {
                if (visit[next.v] != -1) continue;

                visit[next.v] = visit[t.v] + next.w;
                max = Math.max(t.max, next.w);
                queue.add(new Tuple(next.v, visit[next.v], max));

                if (next.v == b) {
                    result = visit[next.v] - max;
                    return;
                }
            }
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
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
        int s, e, v;

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            list[s].add(new Tuple(e, v));
            list[e].add(new Tuple(s, v));
        }

        br.close();
    }

    public static class Tuple {
        int v;
        int w;
        int max;

        public Tuple (int v, int w) {
            this.v = v;
            this.w = w;
        }

        public Tuple (int v, int w, int max) {
            this.v = v;
            this.w = w;
            this.max = max;
        }
    }
}