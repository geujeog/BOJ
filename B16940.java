import java.util.*;
import java.io.*;

class B16940 {
    static int N;
    static List<Integer>[] list;
    static int[] arr;
    static int[] depth, sumDepth;
    static int[] range;
    static int[] countPerDepth;
    static int isRight = 1;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        range[1] = 1;

        if (arr[1] != 1 || !judgeBFS(1)) {
            isRight = 0;
        }
    }

    public static boolean judgeBFS(int idx) {
        int v = arr[idx];
        int dep = depth[v];
        int nextDep = depth[v] + 1;

        for (Integer i : list[v]) {
            if (range[i] == 0) {
                range[i] = sumDepth[dep] + countPerDepth[nextDep];
            }
        }

        countPerDepth[nextDep] += (list[v].size() - ((v == 1) ? 0 : 1));

        if (idx + 1 < N) {
            int next = arr[idx + 1];

            if (range[next] > 0 && range[next] <= idx) {
                return judgeBFS(idx + 1);
            }
            else return false;
        }
        return true;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(isRight+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N+1];
        arr = new int[N+1];
        depth = new int[N+1];
        sumDepth = new int[N+1];
        range = new int[N+1];
        countPerDepth = new int[N+1];

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

        bfs();

        br.close();
    }

    public static void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();

        int maxDepth = 1;
        depth[1] = 1;
        sumDepth[1] = 1;
        queue.add(1);

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (Integer i : list[v]) {
                if (depth[i] == 0) {
                    depth[i] = depth[v] + 1;
                    sumDepth[depth[i]]++;
                    maxDepth = Math.max(maxDepth, depth[i]);
                    queue.add(i);
                }
            }
        }

        for (int i = 2; i <= maxDepth; i++) {
            sumDepth[i] += sumDepth[i-1];
        }
    }
}