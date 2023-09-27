import java.util.*;
import java.io.*;

class B2011 {
    static String num;
    static int[] dp;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        decode();
    }

    public static void decode() {
        if (num.startsWith("0")) return;

        dp[0] = 1;

        for (int i = 1; i < num.length(); i++) {
            char now = num.charAt(i);
            char prev = num.charAt(i-1);

            // 0일 경우, 10 혹은 20만 가능
            if (now == '0') {
                if (prev == '1' || prev == '2') dp[i] = ((i-2 >= 0) ? dp[i-2] : 1) % 1000000;
                else break;
            }
            else {
                dp[i] = dp[i-1] % 1000000;

                // 이전 문자와 결합이 가능할 경우
                if (prev == '1' || (prev == '2' && now <= '6')) dp[i] = (i-2 >= 0) ? (dp[i] + dp[i-2]) % 1000000 : dp[i] + 1 % 1000000;
            }
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(dp[num.length()-1]+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        num = br.readLine();

        dp = new int[num.length()];

        br.close();
    }
}