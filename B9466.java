import java.util.*;
import java.io.*;

class B9466 {
    static BufferedReader br;
    static BufferedWriter bw;
    static int N;
    static int[] parent;
    static boolean[] isVisit;
    static boolean[] isDone;
    static int result;

    public static void main (String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
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
            if (!isDone[i]) {
                dfs(i);
            }
        }
    }

    public static void dfs(int v) {
        if (!isVisit[v]) {
            isVisit[v] = true;
        }
        else {
            isDone[v] = true;
            result++;
        }

        if (!isDone[parent[v]]) {
            dfs(parent[v]);
        }

        isVisit[v] = false;
        isDone[v] = true;
    }

    public static void output() throws IOException {
        bw.write(N - result + "\n");
    }

    public static void input() throws IOException {
        N = Integer.parseInt(br.readLine());
        parent = new int[N+1];
        isVisit = new boolean[N+1];
        isDone = new boolean[N+1];
        result = 0;

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            parent[i] = Integer.parseInt(st.nextToken());
        }
    }
}