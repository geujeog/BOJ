import java.util.*;
import java.io.*;

class B11281 {
    static int N;
    static List<Integer>[] list;
    static int[] order, scc;
    static int orderIdx, sccCnt;
    static Stack<Integer> stack;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        bfs();
        getResult();
    }

    public static void getResult() {
        result = 1;

        for (int i = 1; i <= N; i++) {
            if (scc[i] == scc[i+N]) {
                result = 0;
                break;
            }
        }
    }

    public static void bfs() {
        order = new int[2*N+1];
        scc = new int[2*N+1];
        orderIdx = sccCnt = 0;
        stack = new Stack<>();

        for (int i = 1; i <= 2*N; i++) {
            if (order[i] == 0) {
                scc(i);
            }
        }
    }

    public static int scc(int v) {
        order[v] = ++orderIdx;
        stack.push(v);

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
                int p = stack.pop();
                scc[p] = sccCnt + 1;
                if (p == v) break;
            }
            sccCnt++;
        }

        return root;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"\n");

        if (result == 1) {
            for (int i = 1; i <= N; i++) {
                if (scc[i] < scc[i+N]) bw.write(1+" ");
                else bw.write(0+" ");
            }
        }

        bw.flush();
        bw.close();
    }

    public static int minus(int x) {
        return (x > N) ? x-N : x+N;
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        list = new ArrayList[2*N+1];
        int a, b;

        for (int i = 1; i <= 2*N; i++) {
            list[i] = new ArrayList<>();
        }

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            if (a < 0) a = Math.abs(a) + N;
            if (b < 0) b = Math.abs(b) + N;
            list[minus(a)].add(b);
            list[minus(b)].add(a);
        }

        br.close();
    }
}