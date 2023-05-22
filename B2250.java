import java.util.*;
import java.io.*;

public class B2250 {
    static int cnt = 1;
    static int depthMax = 0;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[][] arr = new int[N+1][2];
        boolean[] isRoot = new boolean[N+1];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int v = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            if (left != -1) {
                arr[v][0] = left;
                isRoot[left] = true;
            }

            if (right != -1) {
                arr[v][1] = right;
                isRoot[right] = true;
            }
        }

        int rootIdx = 0;
        for (int i = 1; i <= N; i++) {
            if (!isRoot[i]) rootIdx = i;
        }

        int[][] square = new int[N][2];
        dfs(square, arr, rootIdx, 0);

        int width = 0;
        int widthLine = 0;

        for (int i = 0; i <= depthMax; i++) {
            int minus = square[i][1] - square[i][0] + 1;

            if (width < minus) {
                width = minus;
                widthLine = i + 1;
            }
        }

        bw.write(widthLine+" "+width);

        br.close();
        bw.flush();
        bw.close();
    }

    public static void dfs (int[][] square, int[][] arr, int v, int depth) {
        if (arr[v][0] != 0) {
            dfs(square, arr, arr[v][0], depth+1);
        }

        if (square[depth][0] == 0) square[depth][0] = cnt;
        square[depth][1] = cnt++;

        if (arr[v][1] != 0){
            dfs(square, arr, arr[v][1], depth+1);
        }

        depthMax = Math.max(depthMax, depth);
    }
}