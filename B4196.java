import java.util.*;
import java.io.*;

class B4196 {
    static int N;
    static List<Integer>[] list;
    static int[] order;
    static int[] scc;
    static Stack<Integer> stack;
    static int orderIdx, sccCnt;
    static int[] enter;
    static int result;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int M, a, b;

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            list = new ArrayList[N+1];
            order = new int[N+1];
            scc = new int[N+1];
            stack = new Stack<>();
            orderIdx = 0;
            sccCnt = 0;
            result = 0;
            for (int i = 1; i <= N; i++) {
                list[i] = new ArrayList<>();
            }

            while (M-- > 0) {
                st = new StringTokenizer(br.readLine());
                a = Integer.parseInt(st.nextToken());
                b = Integer.parseInt(st.nextToken());
                list[a].add(b);
            }

            dfs();
            getEnter();
            getResult();

            bw.write(result+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void getResult() {
        for (int i = 1; i <= sccCnt; i++) {
            if (enter[i] == 0) result++;
        }
    }

    public static void getEnter() {
        enter = new int[sccCnt+1];

        for (int i = 1; i <= N; i++) {
            for (int next: list[i]) {
                if (scc[i] != scc[next]) enter[scc[next]]++;
            }
        }
    }

    public static void dfs() {
        for (int i = 1; i <= N; i++) {
            if (order[i] == 0) {
                scc(i);
            }
        }
    }

    public static int scc(int v) {
        order[v] = ++orderIdx;
        stack.add(v);

        int root = order[v];

        for (int next: list[v]) {
            if (order[next] == 0) {
                root = Math.min(root, scc(next));
            }
            else if (scc[next] == 0) {
                root = Math.min(root, order[next]);
            }
        }

        if (root == order[v]) {
            while (true) {
                int i = stack.pop();
                scc[i] = sccCnt + 1;
                if (i == v) break;
            }
            sccCnt++;
        }

        return root;
    }
}