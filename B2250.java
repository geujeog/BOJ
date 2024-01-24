import java.util.*;
import java.io.*;

class B2250 {
    static int N;
    static int root;
    static int[][] child;
    static int[] count; // 너비인덱스
    static int[][] width;
    static int maxDepth;
    static int resultIdx, resultWidth;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        dfs(root, 0, 1);

        for (int i = 1; i <= maxDepth; i++) {
            int len = width[i][1] - width[i][0] + 1;

            if (resultWidth < len) {
                resultWidth = len;
                resultIdx = i;
            }
        }
    }

    public static int dfs(int v, int standard, int depth) {
        if (v == -1) return standard;

        count[v] = dfs(child[v][0], standard, depth + 1);

        maxDepth = Math.max(depth, maxDepth);
        width[depth][0] = Math.min(width[depth][0], count[v]);
        width[depth][1] = Math.max(width[depth][1], count[v]);

        return dfs(child[v][1] , count[v] + 1, depth + 1);
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(resultIdx+" "+resultWidth);

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        child = new int[N+1][2];
        count = new int[N+1];
        width = new int[N+1][2];

        boolean[] isRoot = new boolean[N+1];
        Arrays.fill(isRoot, true);

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            child[v][0] = Integer.parseInt(st.nextToken());
            child[v][1] = Integer.parseInt(st.nextToken());

            width[i][0] = Integer.MAX_VALUE;
            if (child[v][0] >= 1) isRoot[child[v][0]] = false;
            if (child[v][1] >= 1) isRoot[child[v][1]] = false;
        }

        for (int i = 1; i <= N; i++) {
            if (isRoot[i]) {
                root = i;
                break;
            }
        }

        br.close();
    }
}