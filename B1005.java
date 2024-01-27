import java.util.*;
import java.io.*;

class B1005 {
    static BufferedReader br;
    static BufferedWriter bw;

    static int N;
    static List<Integer>[] road;
    static int[] weight;
    static int[] time;
    static int[] enter;
    static int end;

    public static void main (String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            input();
            solution();
            output();
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void solution() {
        for (int i = 1; i <= N; i++) {
            if (enter[i] == 0) {
                dfs(i);
            }
        }
    }

    public static void dfs(int v) {
        if (v == end) return;

        for (Integer i : road[v]) {
            enter[i]--;
            time[i] = Math.max(time[i], time[v] + weight[i]);

            if (enter[i] == 0) {
                dfs(i);
            }
        }
    }

    public static void output() throws IOException {
        bw.write(time[end]+"\n");
    }

    public static void input() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        road = new ArrayList[N+1];
        weight = new int[N+1];
        time = new int[N+1];
        enter = new int[N+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            time[i] = weight[i] = Integer.parseInt(st.nextToken());
            road[i] = new ArrayList<>();
        }

        while (K-- > 0) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            road[a].add(b);
            enter[b]++;
        }

        end = Integer.parseInt(br.readLine());
    }
}