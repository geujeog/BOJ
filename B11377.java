import java.util.*;
import java.io.*;

class B11377 {
    static int N, M, K;
    static List<Integer>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int[] work = new int[M+1]; // 일을 담당하는 사람
        boolean[] visit = new boolean[M+1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(visit, false);

            if (dfs(i, work, visit)) {
                result++;
            }
        }

        for (int i = 1; i <= N; i++) {
            Arrays.fill(visit, false);

            if (dfs(i, work, visit) && K > 0) {
                K--;
                result++;
            }
        }
    }

    public static boolean dfs(int v, int[] work, boolean[] visit) {
        for (int next: list[v]) {
            if (visit[next]) continue;
            visit[next] = true;

            if (work[next] == 0 || dfs(work[next], work ,visit)) {
                work[next] = v;
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
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
        result = 0;
        int m;

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            m = Integer.parseInt(st.nextToken());

            while (m-- > 0) {
                list[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        br.close();
    }
}