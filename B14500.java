import java.util.*;
import java.io.*;

public class B14500 {
    static int N;
    static int M;
    static int[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] check = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                check[i][j] = true;
                bfs(check, i, j, 1, arr[i][j]);
                check[i][j] = false;

                shapeFuck(i, j);
            }
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void shapeFuck (int i, int j) {
        // i-1 / i+1 / j-1 / j+1
        if (i-1 >= 0 && i+1 < N && j-1 >= 0) result = Math.max(result, arr[i][j]+arr[i-1][j]+arr[i+1][j]+arr[i][j-1]);
        if (i-1 >= 0 && i+1 < N && j+1 < M) result = Math.max(result, arr[i][j]+arr[i-1][j]+arr[i+1][j]+arr[i][j+1]);
        if (i-1 >= 0 && j-1 >= 0 && j+1 < M) result = Math.max(result, arr[i][j]+arr[i-1][j]+arr[i][j-1]+arr[i][j+1]);
        if (i+1 < N && j-1 >= 0 && j+1 < M) result = Math.max(result, arr[i][j]+arr[i+1][j]+arr[i][j-1]+arr[i][j+1]);
    }

    public static void bfs (boolean[][] check, int i, int j, int depth, int sum) {
        if (depth == 4) {
            result = Math.max(result, sum);
            return;
        }

        if (i-1 >= 0 && !check[i-1][j]) {
            check[i-1][j] = true;
            bfs(check, i-1, j, depth+1, sum+arr[i-1][j]);
            check[i-1][j] = false;
        }

        if (i+1 < N && !check[i+1][j]) {
            check[i+1][j] = true;
            bfs(check, i+1, j, depth+1, sum+arr[i+1][j]);
            check[i+1][j] = false;
        }

        if (j-1 >= 0 && !check[i][j-1]) {
            check[i][j-1] = true;
            bfs(check, i, j-1, depth+1, sum+arr[i][j-1]);
            check[i][j-1] = false;
        }

        if (j+1 < M && !check[i][j+1]) {
            check[i][j+1] = true;
            bfs(check, i, j+1, depth+1, sum+arr[i][j+1]);
            check[i][j+1] = false;
        }
    }
}