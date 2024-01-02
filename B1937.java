import java.util.*;
import java.io.*;

class B1937 {
    static int N;
    static int[][] arr;
    static Integer[][] move;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (move[i][j] != null) continue;
                sum(i, j);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result = Math.max(result, move[i][j]);
            }
        }
    }

    public static void sum(int i, int j) {
        move[i][j] = 1;

        if (i-1 >= 0 && arr[i-1][j] > arr[i][j]) {
            if (move[i-1][j] == null) sum(i-1, j);
            move[i][j] = Math.max(move[i][j], move[i-1][j] + 1);
        }
        if (i+1 < N && arr[i+1][j] > arr[i][j]) {
            if (move[i+1][j] == null) sum(i+1, j);
            move[i][j] = Math.max(move[i][j], move[i+1][j] + 1);
        }
        if (j-1 >= 0 && arr[i][j-1] > arr[i][j]) {
            if (move[i][j-1] == null) sum(i, j-1);
            move[i][j] = Math.max(move[i][j], move[i][j-1] + 1);
        }
        if (j+1 < N && arr[i][j+1] > arr[i][j]) {
            if (move[i][j+1] == null) sum(i, j+1);
            move[i][j] = Math.max(move[i][j], move[i][j+1] + 1);
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        move = new Integer[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}