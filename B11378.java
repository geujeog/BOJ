import java.util.*;
import java.io.*;

class B11378 {
    static int N, M, K;
    static List<Integer>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 이분매칭
        int[] work = new int[M+1]; // 일하는 사람
        boolean[] visit = new boolean[M+1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(visit, false);

            if (dfs(i, work, visit)) {
                result++;
            }
        }
        for (int i = 1; i <= N; i++) {
            Arrays.fill(visit, false);

            for (int j = 0; j < list[i].size(); j++) {
                if (K > 0 && dfs(i, work, visit)) {
                    K--;
                    result++;
                }
            }
        }
    }

    public static boolean dfs(int v, int[] work, boolean[] visit) {
        for (int next: list[v]) {
            if (visit[next] || work[next] == v) continue;
            visit[next] = true;

            if (work[next] == 0 || dfs(work[next], work, visit)) {
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
        int cnt;

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            cnt = Integer.parseInt(st.nextToken());
            while (cnt-- > 0) {
                list[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        br.close();
    }
}