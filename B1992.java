import java.util.*;
import java.io.*;

class B1992 {
    static char[][] arr;
    static BufferedWriter bw;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        arr = new char[N][N];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        // bw.write("(");
        div(0, N-1, 0, N-1, N);
        // bw.write(")");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void div(int fromX, int toX, int fromY, int toY, int N) throws IOException {
        if (isSame(fromX, toX, fromY, toY)) {
            bw.write(arr[fromX][fromY]+"");
        }
        else {
            int len = N / 2;
            bw.write("(");
            div(fromX, fromX+len-1, fromY, fromY+len-1, N/2);
            div(fromX, fromX+len-1, toY-len+1, toY, N/2);
            div(toX-len+1, toX, fromY, fromY+len-1, N/2);
            div(toX-len+1, toX, toY-len+1, toY, N/2);
            bw.write(")");
        }
    }

    public static boolean isSame(int fromX, int toX, int fromY, int toY) {
        boolean same = true;

        char standard = arr[fromX][fromY];
        for (int i = fromX; i <= toX; i++) {
            for (int j = fromY; j <= toY; j++) {
                if (standard != arr[i][j]) {
                    same = false;
                    break;
                }
            }
        }

        return same;
    }
}