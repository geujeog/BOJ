import java.util.*;
import java.io.*;

public class B1520 {
    static int sero;
    static int garo;
    static int[][] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        sero = Integer.parseInt(st.nextToken());
        garo = Integer.parseInt(st.nextToken());

        arr = new int[sero][garo];

        for (int i = 0; i < sero; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < garo; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] check = new boolean[sero][garo];

        Integer[][] dp = new Integer[sero][garo];
        dp[0][0] = 1;

        bw.write(dfs(check, dp, sero-1, garo-1)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int dfs (boolean[][] check, Integer[][] dp, int x, int y) {
        check[x][y] = true;

        if (dp[x][y] == null) {
            int cnt = 0;

            if (x > 0) {
                if (arr[x][y] < arr[x-1][y] && !check[x-1][y]) {
                    cnt += dfs(check, dp, x - 1, y);
                }
            }
            if (y > 0) {
                if (arr[x][y] < arr[x][y-1] && !check[x][y-1]) {
                    cnt += dfs(check, dp, x, y - 1);
                }
            }
            if (x < sero-1) {
                if (arr[x][y] < arr[x+1][y] && !check[x+1][y]) {
                    cnt += dfs(check, dp, x + 1, y);
                }
            }
            if (y < garo-1) {
                if (arr[x][y] < arr[x][y+1] && !check[x][y+1]) {
                    cnt += dfs(check, dp, x, y + 1);
                }
            }

            dp[x][y] = cnt;
        }

        check[x][y] = false;

        return dp[x][y];
    }
}