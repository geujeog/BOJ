import java.util.*;
import java.io.*;

class B9576 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int N, M;
    static int[][] arr;
    static int result;

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
        int[] book = new int[N+1];
        boolean[] visit = new boolean[N+1];

        for (int i = 1; i <= M; i++) {
            Arrays.fill(visit, false);
            if (dfs(i, book, visit)) {
                result++;
            }
        }
    }

    public static boolean dfs(int v, int[] book, boolean[] visit) {
        for (int i = arr[v][0]; i <= arr[v][1]; i++) {
            if (visit[i]) continue;
            visit[i] = true;

            if (book[i] == 0 || dfs(book[i], book, visit)) {
                book[i] = v;
                return true;
            }
        }

        return false;
    }

    public static void output() throws IOException {
        bw.write(result+"\n");
    }

    public static void input() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M+1][2];
        result = 0;

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }
    }
}