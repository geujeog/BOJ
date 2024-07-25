import java.util.*;
import java.io.*;

class B4013 {
    static int N, S;
    static List<Integer>[] list;
    static int[] money;
    static boolean[] isRestaurant;
    static Stack<Integer> stack;
    static int[] order, scc;
    static int orderIdx, sccCnt;
    static int[] sccMoney;
    static List<Integer>[] sccList;
    static boolean[] isRestaurantInScc;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // scc 별 현금 확인
        // scc in out 확인
        // scc bfs

        for (int i = 1; i <= N; i++) {
            if (order[i] == 0) {
                scc(i);
            }
        }

        getInOut();
        bfs();
    }

    public static void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        int[] visit = new int[sccCnt+1];
        int v, max = 0;

        queue.add(scc[S]);
        visit[scc[S]] = sccMoney[scc[S]];

        while (!queue.isEmpty()) {
            v = queue.poll();

            if (isRestaurantInScc[v]) {
                max = Math.max(max, visit[v]);
            }

            for (int next: sccList[v]) {
                if (visit[next] < visit[v] + sccMoney[next]) {
                    visit[next] = visit[v] + sccMoney[next];
                    queue.add(next);
                }
            }
        }

        result = max;
    }

    public static void getInOut() {
        sccMoney = new int[sccCnt+1];
        sccList = new ArrayList[sccCnt+1];
        isRestaurantInScc = new boolean[sccCnt+1];

        for (int i = 1; i <= sccCnt; i++) {
            sccList[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            sccMoney[scc[i]] += money[i];
            if (isRestaurant[i]) {
                isRestaurantInScc[scc[i]] = true;
            }

            for (int next: list[i]) {
                if (scc[i] != scc[next]) {
                    sccList[scc[i]].add(scc[next]);
                }
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
        int M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
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

        money = new int[N+1];
        for (int i = 1; i <= N; i++) {
            money[i] = Integer.parseInt(br.readLine());
        }

        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        isRestaurant = new boolean[N+1];
        while (P-- > 0) {
            isRestaurant[Integer.parseInt(st.nextToken())] = true;
        }

        stack = new Stack<>();
        order = new int[N+1];
        scc = new int[N+1];

        br.close();
    }
}