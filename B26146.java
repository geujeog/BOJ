import java.util.*;
import java.io.*;

class B26146 {
    static int N;
    static List<Integer>[] list;
    static int[] order, scc;
    static int orderIdx, sccCnt;
    static Stack<Integer> stack;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // scc 하나

        for (int i = 1; i <= N; i++) {
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

        if (sccCnt == 1) bw.write("Yes");
        else bw.write("No");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
        order = new int[N+1];
        scc = new int[N+1];
        stack = new Stack<>();
        int a, b;

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            list[a].add(b);
        }

        br.close();
    }
}