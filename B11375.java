import java.util.*;
import java.io.*;

class B11375 {
    static int N, M;
    static List<Integer>[] connect;
    static boolean[] visit;
    static int[] parent;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 이분 매칭
        // 사람 - 일

        for (int i = 1; i <= N; i++) {
            Arrays.fill(visit, false);
            if (dfs(i)) {
                result++;
            }
        }
    }

    public static boolean dfs(int v) {
        for (Integer i : connect[v]) {
            if (!visit[i]) {
                visit[i] = true;
                if (parent[i] == 0 || dfs(parent[i])) {
                    parent[i] = v;
                    return true;
                }
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
        connect = new ArrayList[N+1];
        visit = new boolean[M+1];
        parent = new int[M+1];

        // 사람 - 일
        for (int i = 1; i <= N; i++) {
            connect[i] = new ArrayList<>();

            st = new StringTokenizer(br.readLine());
            int workCnt = Integer.parseInt(st.nextToken());
            while (workCnt-- > 0) {
                connect[i].add(Integer.parseInt(st.nextToken()));
            }
        }


        br.close();
    }
}