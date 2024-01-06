import java.util.*;
import java.io.*;

class B12738 {
    static int N;
    static int[] arr, dp;
    static int lis;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Arrays.fill(dp, -1000000001);
        lis = 0;

        for (int i = 0; i < N; i++) {
            int idx = binarySearch(0, lis, arr[i]);

            if (idx == -1) {
                dp[lis++] = arr[i];
            }
            else {
                dp[idx] = arr[i];
            }
        }
    }

    public static int binarySearch(int s, int e, int target) {
        int res = -1;

        while (s <= e) {
            int mid = (s + e) / 2;

            if (target < dp[mid]) {
                e = mid - 1;
                res = mid;
            }
            else {
                s = mid + 1;
            }
        }

        return res;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(lis+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N+1];
        dp = new int[N+1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }
}