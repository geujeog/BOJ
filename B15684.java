import java.util.*;
import java.io.*;

class B15684 {
    static int N;
    static int M;
    static int H;
    static boolean[][] line;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        result = Integer.MAX_VALUE;

        choice(line, 1, 1, 0);
    }

    public static void choice(boolean[][] arr, int x, int y, int cnt) {
        if (result <= cnt || cnt > 3) return;

        if (check(arr)) {
            result = Math.min(result, cnt);
            return;
        }

        for (int i = x; i <= H; i++) {
            for (int j = 1; j < N; j++) {
                if (i == x && j < y) continue;

                if (!arr[i][j]) {
                    if (j-1 > 0 && arr[i][j-1]) continue;
                    if (j+1 < N && arr[i][j+1]) continue;

                    arr[i][j] = true;
                    choice(arr, i, j, cnt+1);
                    arr[i][j] = false;
                }
            }
        }
    }

    public static boolean check(boolean[][] arr) {
        for (int j = 1; j <= N+1; j++) {
            int li = j;

            for (int i = 1; i <= H; i++) {
                if (li-1 > 0 && arr[i][li-1]) li -= 1;
                else if (li <= N && arr[i][li]) li += 1;
            }

            if (li != j) return false;
        }
        return true;
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if (result == Integer.MAX_VALUE) bw.write(-1+"");
        else bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        line = new boolean[H+1][N+1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            line[a][b] = true;
        }

        br.close();
    }
}