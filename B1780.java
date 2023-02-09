import java.util.*;
import java.io.*;

class B1780 {
    static int N;
    static int[][] arr;

    static int countFirst;
    static int countSecond;
    static int countThird;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        countFirst = 0;
        countSecond = 0;
        countThird = 0;

        div(0, 0, N-1, N-1);

        bw.write(countFirst+"\n");
        bw.write(countSecond+"\n");
        bw.write(countThird+"\n");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void div(int fromX, int fromY, int toX, int toY) {
        if (isSameColor(fromX, fromY, toX, toY)) {
            int standard = arr[fromX][fromY];
            if (standard == -1) countFirst++;
            else if (standard == 0) countSecond++;
            else countThird++;
        }
        else {
            int len = (toX - fromX + 1)/3;

            div(fromX, fromY, fromX+len-1, fromY+len-1);
            div(fromX, fromY+len, fromX+len-1, toY-len);
            div(fromX, toY-len+1, fromX+len-1, toY);

            div(fromX+len, fromY, toX-len, fromY+len-1);
            div(fromX+len, fromY+len, toX-len, toY-len);
            div(fromX+len, toY-len+1, toX-len, toY);

            div(toX-len+1, fromY, toX, fromY+len-1);
            div(toX-len+1, fromY+len, toX, toY-len);
            div(toX-len+1, toY-len+1, toX, toY);
        }
    }

    public static boolean isSameColor(int fromX, int fromY, int toX, int toY) {
        boolean isSame = true;

        int standard = arr[fromX][fromY];
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                if (standard != arr[x][y]) {
                    isSame = false;
                    break;
                }
            }
        }

        return isSame;
    }
}