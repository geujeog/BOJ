import java.util.*;
import java.io.*;

class B2579 {
    static int N;
    static int[] step;
    static int[] count;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        step = new int[N+1];
        step[0] = 0;

        for (int i = 1; i <= N; i++) {
            step[i] = Integer.parseInt(br.readLine());
        }

        count = new int[N+1];
        Arrays.fill(count, -1);
        count[0] = step[0];
        count[1] = step[1];

        if (N >= 2) {
            count[2] = step[1]+step[2];
        }

        bw.write(dp(N)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int dp (int idx) {

        if (count[idx] == -1) {
            System.out.println(idx);
            count[idx] = Math.max(dp(idx-2), dp(idx-3)+step[idx-1]) + step[idx];
        }

        return count[idx];
    }
}