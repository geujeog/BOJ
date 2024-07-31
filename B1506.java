import java.util.*;
import java.io.*;

class B1506 {
    static int N;
    static int[] cost;
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
        // 각 scc마다 cost가 가장 작은 경찰서
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
            int min = Integer.MAX_VALUE;
            while (true) {
                int p = stack.pop();
                scc[p] = sccCnt + 1;
                min = Math.min(min, cost[p]);
                if (p == v) break;
            }
            sccCnt++;
            result += min;
        }

        return root;
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
        cost = new int[N+1];
        list = new ArrayList[N+1];
        String line;

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
            cost[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N; i++) {
            line = br.readLine();
            for (int j = 1; j <= N; j++) {
                if (line.charAt(j-1) == '1') {
                    list[i].add(j);
                }
            }
        }

        order = new int[N+1];
        scc = new int[N+1];
        stack = new Stack<>();

        br.close();
    }
}