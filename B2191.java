import java.util.*;
import java.io.*;

class B2191 {
    static int N, M;
    static double[][] mouse;
    static List<Integer>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int[] owner = new int[M+1];
        boolean[] visit = new boolean[M+1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(visit, false);
            if (dfs(i, owner, visit)) {
                result++;
            }
        }
    }

    public static boolean dfs(int v, int[] owner, boolean[] visit) {
        for (int next: list[v]) {
            if (visit[next]) continue;

            visit[next] = true;
            if (owner[next] == 0 || dfs(owner[next], owner, visit)) {
                owner[next] = v;
                return true;
            }
        }
        return false;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(N - result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        double S = Double.parseDouble(st.nextToken());
        double V = Double.parseDouble(st.nextToken());
        mouse = new double[N+1][2];
        list = new ArrayList[N+1];
        double x, y, distance;

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            mouse[i][0] = Double.parseDouble(st.nextToken());
            mouse[i][1] = Double.parseDouble(st.nextToken());
            list[i] = new ArrayList<>();
        }

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            x = Double.parseDouble(st.nextToken());
            y = Double.parseDouble(st.nextToken());
            for (int j = 1; j <= N; j++) {
                distance = Math.sqrt(Math.pow(x-mouse[j][0], 2)+Math.pow(y-mouse[j][1], 2));

                if (distance / V <= S) {
                    list[j].add(i);
                }
            }
        }

        br.close();
    }
}