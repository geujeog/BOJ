import java.util.*;
import java.io.*;

public class B11404 {
    static int N;
    static int[][] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        arr = new int[N+1][N+1];

        for (int m = 0; m < M; m++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            if (arr[a][b] == 0) arr[a][b] = w;
            else if (arr[a][b] > w) arr[a][b] = w;
        }

        minCost();

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                bw.write(arr[i][j]+" ");
            }
            bw.write("\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void minCost () {

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

    }
}