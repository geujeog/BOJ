import java.util.*;
import java.io.*;

class B11400 {
    static int V;
    static List<Integer>[] list;
    static int[] order;
    static int orderCnt;
    static Set<Tuple> set;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 1; i <= V; i++) {
            if (order[i] == 0) {
                dfs(i, 0);
            }
        }
    }

    public static int dfs(int v, int before) {
        order[v] = ++orderCnt;

        int min = order[v];

        for (Integer i : list[v]) {
            if (before == i) continue;

            if (order[i] == 0) {

                int low = dfs(i, v);

                if (low > order[v]) {
                    int a = Math.min(v, i);
                    int b = Math.max(v, i);
                    set.add(new Tuple(a, b));
                }
                min = Math.min(min, low);
            }
            else {
                min = Math.min(min, order[i]);
            }
        }

        return min;
    }

    public static class Tuple implements Comparable<Tuple> {
        int a;
        int b;

        public Tuple(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            Tuple t = (Tuple) o;

            if (this.a == t.a) {
                if (this.b == t.b) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

        @Override
        public int compareTo (Tuple t) {
            if (this.a == t.a) {
                return this.b - t.b;
            }

            return this.a - t.a;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(set.size()+"\n");
        for (Tuple t : set) {
            bw.write(t.a+" "+t.b+"\n");
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        list = new ArrayList[V+1];
        order = new int[V+1];
        set = new TreeSet<>();

        for (int i = 1; i <= V; i++) {
            list[i] = new ArrayList<>();
        }

        while (E-- > 0) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list[a].add(b);
            list[b].add(a);
        }

        br.close();
    }
}