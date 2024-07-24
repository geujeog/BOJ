import java.util.*;
import java.io.*;

class B2150 {
    static int N;
    static List<Integer>[] list;
    static boolean[] finished;
    static int[] order;
    static int orderIdx, sccCnt;
    static Stack<Integer> stack;
    static List<List<Integer>> result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 1; i <= N; i++) {
            if (order[i] == 0) {
                dfs(i);
            }
        }

        Collections.sort(result, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o1.get(0) - o2.get(0);
            }
        });
    }

    public static int dfs(int v) {
        order[v] = ++orderIdx;
        stack.push(v);

        int root = order[v];

        for (int next: list[v]) {
            if (order[next] == 0) {
                root = Math.min(root, dfs(next));
            }
            else if (!finished[next]) {
                root = Math.min(root, order[next]);
            }
        }

        if (root == order[v]) {
            List<Integer> tmp = new ArrayList<>();
            while (true) {
                int i = stack.pop();
                finished[i] = true;
                tmp.add(i);
                if (i == v) break;
            }
            Collections.sort(tmp);
            result.add(tmp);
            sccCnt++;
        }

        return root;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result.size()+"\n");

        for (List<Integer> tmp: result) {
            for (int t: tmp) {
                bw.write(t+" ");
            }
            bw.write(-1+"\n");
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
        finished = new boolean[N+1];
        order = new int[N+1];
        stack = new Stack<>();
        result = new ArrayList<>();
        int a, b;

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        while (E-- > 0) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            list[a].add(b);
        }

        br.close();
    }
}