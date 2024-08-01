import java.util.*;
import java.io.*;

class B6543 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int N;
    static List<Integer>[] list;
    static int[] order, scc;
    static int orderIdx, sccCnt;
    static Stack<Integer> stack;
    static List<Integer>[] sccGroup;
    static int[] output;
    static List<Integer> result;

    public static void main (String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            if (N == 0) break;

            input(Integer.parseInt(st.nextToken()));
            solution();
            output();
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void solution() {
        // scc output = 0
        bfs();
        sccBFS();
        getResult();
    }

    public static void getResult() {
        for (int i = 1; i <= sccCnt; i++) {
            if (output[i] == 0) {
                result.addAll(sccGroup[i]);
            }
        }
    }

    public static void sccBFS() {
        output = new int[sccCnt + 1];

        for (int i = 1; i <= N; i++) {
            for (int next: list[i]) {
                if (scc[i] != scc[next]) {
                    output[scc[i]]++;
                }
            }
        }
    }

    public static void bfs() {
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
            List<Integer> tmp = new ArrayList<>();
            while (true) {
                int p = stack.pop();
                scc[p] = sccCnt + 1;
                tmp.add(p);
                if (p == v) break;
            }
            sccGroup[sccCnt + 1].addAll(tmp);
            sccCnt++;
        }

        return root;
    }

    public static void output() throws IOException {
        Collections.sort(result);
        for (int res: result) {
            bw.write(res+" ");
        }
        bw.write("\n");
    }

    public static void input(int m) throws IOException {
        list = new ArrayList[N+1];
        sccGroup = new ArrayList[N+1];
        StringTokenizer st;
        int a, b;

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
            sccGroup[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        while (m-- > 0) {
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            list[a].add(b);
        }

        order = new int[N+1];
        scc = new int[N+1];
        stack = new Stack<>();
        orderIdx = sccCnt = 0;
        result = new ArrayList<>();
    }
}