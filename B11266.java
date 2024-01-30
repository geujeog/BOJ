import java.util.*;
import java.io.*;

class B11266 {
    static int V;
    static List<Integer>[] list;
    static int[] distance;
    static boolean[] cut;
    static int order;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 1; i <= V; i++) {
            if (distance[i] == 0) {
                dfs(i, true);
            }
        }

        for (int i = 1; i <= V; i++) {
            if (cut[i]) {
                result++;
            }
        }
    }

    public static int dfs(int v, boolean isRoot) {
        distance[v] = ++order;

        int minOrder = distance[v];
        int child = 0;

        for (Integer i : list[v]) {
            if (distance[i] == 0) {
                child++;

                int low = dfs(i, false);

                if (!isRoot && low >= distance[v]) {
                    cut[v] = true;
                }
                minOrder = Math.min(minOrder, low);
            }
            else {
                minOrder = Math.min(minOrder, distance[i]);
            }
        }

        if (isRoot && child >= 2) {
            cut[v] = true;
        }

        return minOrder;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"\n");
        for (int i = 1; i <= V; i++) {
            if (cut[i]) bw.write(i+" ");
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        list = new ArrayList[V+1];
        distance = new int[V+1];
        cut = new boolean[V+1];

        for (int i = 1; i <= V; i++) {
            list[i] = new ArrayList<>();
        }

        while (E-- > 0) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list[a].add(b);
            list[b].add(a);
        }

        br.close();
    }
}