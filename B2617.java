import java.util.*;
import java.io.*;

public class B2617 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] arr = new int[N+1][N+1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());

            arr[v2][v1] = 1;
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (i == j) continue;
                    if (arr[i][k] == 0 || arr[k][j] == 0) continue;

                    if (arr[i][j] == 0 || arr[i][j] > arr[i][k] + arr[k][j]) {
                        arr[i][j] = arr[i][k] + arr[k][j];
                    }
                }
            }
        }

        int[] big = new int[N+1];
        int[] small = new int[N+1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (arr[i][j] == 0) continue;
                big[i]++;
                small[j]++;
            }
        }

        int result = 0;

        int mid = (N + 1) / 2;
        for (int i = 1; i <= N; i++) {
            if (big[i] >= mid) result++;
            else if (small[i] >= mid) result++;
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }
}