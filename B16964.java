import java.util.*;
import java.io.*;

class B16964 {
    static int N;
    static List<Integer>[] list;
    static int[] arr;
    static int[] parent;
    static boolean[] visit;
    static int isPossible;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        getParent();

        visit[1] = true;
        if (dfs(1, 1) == N) {
            isPossible = 1;
        }
    }

    public static int dfs(int node, int idx) {
        if (node != arr[idx]) return -1;

        int minus = (node == 1) ? 0 : 1;
        for (int i = 0; i < list[node].size() - minus; i++) {
            int nextNode = arr[idx + 1];

            if (!visit[nextNode] && parent[nextNode] == node) {
                visit[nextNode] = true;
                idx = dfs(nextNode, idx + 1);

                if (idx == -1) return -1;
            }
            else return -1;
        }

        return idx;
    }

    public static void getParent() {
        Queue<Integer> queue = new ArrayDeque<>();
        parent[1] = 1;
        queue.add(1);

        while (!queue.isEmpty()) {
            int q = queue.poll();

            for (Integer i : list[q]) {
                if (parent[i] == 0) {
                    parent[i] = q;
                    queue.add(i);
                }
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(isPossible+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N+1];
        arr = new int[N+1];
        parent = new int[N+1];
        visit = new boolean[N+1];

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 1; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list[a].add(b);
            list[b].add(a);
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }
}