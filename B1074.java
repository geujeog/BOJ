import java.util.*;
import java.io.*;

class B1074 {

    static int findX;
    static int findY;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        findY = Integer.parseInt(st.nextToken());
        findX = Integer.parseInt(st.nextToken());

        bw.write(findFour(0, 0, N)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int findFour(int x, int y, int N) {
        if (N == 0) return 0;

        int halfSize = (int) Math.pow(2, N-1);

        int count = 0;

        // 2사분면
        if (findX < x+halfSize && findY < y+halfSize) {
            count += findFour(x, y, N-1);
        }
        // 1사분면
        else if (findX >= x+halfSize && findY < y+halfSize) {
            count += findFour(x+halfSize, y, N-1) + Math.pow(halfSize, 2);
        }
        // 3사분면
        else if (findX < x+halfSize && findY >= y+halfSize) {
            count += findFour(x, y+halfSize, N-1) + Math.pow(halfSize, 2) * 2;
        }
        // 4사분면
        else {
            count += findFour(x+halfSize, y+halfSize, N-1) + Math.pow(halfSize, 2) * 3;
        }

        return count;
    }
}