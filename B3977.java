import java.util.*;
import java.io.*;

class B3977 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int N;
    static List<Integer>[] list;
    static List<Integer> result;

    static int[] order, scc;
    static int orderIdx, sccCnt;
    static Stack<Integer> stack;
    static List<Integer>[] sccGroup;
    static int[] sccInput;

    public static void main (String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            if (i > 0) br.readLine();
            input();
            solution();
            output();
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void solution() {
        // scc
        // scc bfs, 모든 scc 노드로 갈 수 있는 시작점 찾기

        bfs();
        getSccList();
        sccBFS();
    }

    public static void sccBFS() {
        for (int i = 1; i <= sccCnt; i++) {
            if (sccInput[i] == 0) {
                if (result.size() == 0) {
                    result.addAll(sccGroup[i]);
                }
                else {
                    result.clear();
                    break;
                }
            }
        }
    }

    public static void getSccList() {
        sccGroup = new ArrayList[sccCnt + 1];
        sccInput = new int[sccCnt + 1];
        for (int i = 1; i <= sccCnt; i++) {
            sccGroup[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            sccGroup[scc[i]].add(i);
            for (int next: list[i]) {
                if (scc[i] != scc[next]) {
                    sccInput[scc[next]]++;
                }
            }
        }
    }

    public static void bfs() {
        order = new int[N];
        scc = new int[N];
        orderIdx = sccCnt = 0;
        stack = new Stack<>();

        for (int i = 0; i < N; i++) {
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
                int p = stack.pop();
                scc[p] = sccCnt + 1;
                if (p == v) break;
            }
            sccCnt++;
        }

        return root;
    }

    public static void output() throws IOException {
        if (result.size() == 0) {
            bw.write("Confused\n");
        }
        else {
            Collections.sort(result);
            for (int res: result) {
                bw.write(res+"\n");
            }
        }
        bw.write("\n");
    }

    public static void input() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N];
        result = new ArrayList<>();
        int a, b;

        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            list[a].add(b);
        }
    }
}