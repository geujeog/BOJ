import java.util.*;
import java.io.*;

class B10159 {
    static int N;
    static boolean[][] arr;
    static boolean[][] reverse;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        arr = new boolean[N+1][N+1];
        reverse = new boolean[N+1][N+1];

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[a][b] = true;
            reverse[b][a] = true;
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (arr[i][k] && arr[k][j]) arr[i][j] = true;
                    if (reverse[i][k] && reverse[k][j]) reverse[i][j] = true;
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                arr[i][j] |= reverse[i][j];
            }
        }

        for (int i = 1; i <= N; i++) {
            int cnt = 0;

            for (int j = 1; j <= N; j++) {
                if (i == j) continue;

                if (!arr[i][j]) cnt++;
            }

            bw.write(cnt+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}