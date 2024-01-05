import java.util.*;
import java.io.*;

class B12015 {
    static int N;
    static int[] arr, dp;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        int lis = 0;

        for (int i = 0; i < N; i++) {
            int idx = binarySearch(arr[i], 0, lis);

            if (idx == -1) {
                dp[lis] = arr[i];
                lis++;
            }
            else {
                dp[idx] = arr[i];
            }
        }

        result = lis;
    }

    public static int binarySearch(int target, int s, int e) {
        int res = -1;

        while (s <= e) {
            int mid = (s + e) / 2;

            if (target <= dp[mid]) {
                res = mid;
                e = mid - 1;
            }
            else {
                s = mid + 1;
            }
        }

        return res;
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
        arr = new int[N];
        dp = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }
}