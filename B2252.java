import java.util.*;
import java.io.*;

class B2252 {
    static int N;
    static List<Integer>[] children;
    static boolean[] isRoot;
    static int[] depth;
    static List<Integer>[] order;
    static StringBuilder result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 1; i <= N; i++) {
            if (isRoot[i]) {
                order[1].add(i);
                depth[i] = 1;
                dfs(i, 1);
            }
        }

        getResult();
    }

    public static void getResult() {
        for (int i = 1; i <= N; i++) {
            for (Integer o : order[i]) {
                result.insert(0, o+" ");
            }
        }
    }

    public static void dfs(int v, int d) {
        for (Integer i : children[v]) {
            if (depth[i] < d + 1) {
                if (depth[i] != 0) {
                    order[depth[i]].remove((Integer) i);
                }
                order[d + 1].add(i);
                depth[i] = d + 1;
                dfs(i, d + 1);
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result.toString());

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        children = new ArrayList[N+1];
        order = new ArrayList[N+1];
        isRoot = new boolean[N+1];
        depth = new int[N+1];
        result = new StringBuilder();

        Arrays.fill(isRoot, true);
        for (int i = 1; i <= N; i++) {
            children[i] = new ArrayList<>();
            order[i] = new ArrayList<>();
        }

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            children[b].add(a);
            isRoot[a] = false;
        }

        br.close();
    }
}