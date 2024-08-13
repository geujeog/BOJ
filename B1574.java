import java.util.*;
import java.io.*;

class B1574 {
    static int R, C, N;
    static boolean[][] blank;
    static List<Integer>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 행-열 최대 매칭 개수

        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                if (blank[i][j]) continue;

                list[i].add(j);
            }
        }

        int[] owner = new int[C+1];
        boolean[] visit = new boolean[C+1];

        for (int i = 1; i <= R; i++) {
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
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        blank = new boolean[R+1][C+1];
        list = new ArrayList[R+1];
        int a, b;

        for (int i = 1; i <= R; i++) {
            list[i] = new ArrayList<>();
        }

        while (N-- > 0) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            blank[a][b] = true;
        }

        br.close();
    }
}