import java.util.*;
import java.io.*;

class B1867 {
    static int N;
    static List<Integer>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int[] owner = new int[N+1];
        boolean[] visit = new boolean[N+1];

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

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
        int a, b;

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        while (K-- > 0) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            list[a].add(b);
        }

        br.close();
    }
}