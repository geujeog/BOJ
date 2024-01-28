import java.util.*;
import java.io.*;

class B16947 {
    static int N;
    static List<Integer>[] list;
    static int[] distance;
    static boolean[] isVisit;
    static boolean[] isCycle;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 사이클 구하기
        // 사이클까지의 거리 구하기

        dfs(1, 0);

        for (int i = 1; i <= N; i++) {
            if (!isCycle[i]) {
                distance[i] = bfs(i);
            }
        }
    }

    public static int bfs(int s) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{s, 0, 0});

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int v = q[0];
            int prev = q[1];
            int dist = q[2];

            for (Integer i : list[v]) {
                if (i == prev) continue;

                if (!isCycle[i]) {
                    queue.add(new int[]{i, v, dist + 1});
                }
                else return dist + 1;
            }
        }

        return 0;
    }

    public static int dfs(int v, int prev) {
        isVisit[v] = true;

        for (Integer i : list[v]) {
            if (i == prev) continue;

            if (!isVisit[i]) {
                int point = dfs(i, v);

                if (point != 0) {
                    isCycle[i] = true;

                    if (v == point) return 0;
                    else return point;
                }
            }
            else {
                isCycle[i] = true;
                return i;
            }
        }

        isVisit[v] = false;

        return 0;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i = 1; i <= N; i++) {
            bw.write(distance[i]+" ");
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N+1];
        distance = new int[N+1];
        isVisit = new boolean[N+1];
        isCycle = new boolean[N+1];

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list[a].add(b);
            list[b].add(a);
        }

        br.close();
    }
}