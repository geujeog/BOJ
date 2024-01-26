import java.util.*;
import java.io.*;

class B16940 {
    static int N;
    static List<Integer>[] list;
    static int[] order;

    static int[] parent; // 자신의 부모
    static int[] level; // 자신의 레벨
    static int[] countingLevel; // 레벨 별 노드 개수

    static int[] countingChild; // 레벨 별 카운팅한 자식 값
    static int[][] range;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        range[order[1]][0] = range[order[1]][1] = 1;

        for (int i = 1; i <= N; i++) {
            int v = order[i];
            int lv = level[v];

            for (Integer child : list[v]) {
                if (child == parent[v]) continue;

                range[child][0] = countingLevel[lv] + countingChild[lv + 1] + 1;
                range[child][1] = range[child][0] + list[v].size() - 2;
            }

            countingChild[lv + 1] += list[v].size() - 1;

            if (i < range[v][0] || range[v][1] < i) {
                result = 0;
                break;
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N+1];
        order = new int[N+1];

        parent = new int[N+1];
        level = new int[N+1];
        countingChild = new int[N+2];
        countingLevel = new int[N+1];
        range = new int[N+1][2];
        result = 1;

        for (int i = 0; i <= N; i++) {
            list[i] = new ArrayList<>();
        }
        list[1].add(0);

        for (int i = 0; i < N-1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list[a].add(b);
            list[b].add(a);
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            order[i] = Integer.parseInt(st.nextToken());
        }

        bfs();

        br.close();
    }

    public static void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();

        queue.add(order[1]);
        level[order[1]] = 1;
        countingLevel[1] = 1;

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (Integer i : list[v]) {
                if (i == 0) continue;

                if (level[i] == 0) {
                    parent[i] = v;
                    level[i] = level[v] + 1;
                    countingLevel[level[i]]++;
                    queue.add(i);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            countingLevel[i] += countingLevel[i-1];
        }
    }
}