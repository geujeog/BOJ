import java.util.*;
import java.io.*;

public class B2295 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        int[] sum = new int[N*N];

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(arr);

        int cnt = 0;
        for (int x = 0; x < N; x++) {
            for (int y = x; y < N; y++) {
                sum[cnt++] = arr[x] + arr[y];
            }
        }
        Arrays.sort(sum, 0, cnt-1);

        int result = 0;
        for (int x = N-1; x >= 0; x--) {
            for (int y = x; y >= 0; y--) {
                if (search(cnt-1, sum, arr[x] - arr[y]) != -1) {
                    result = arr[x];
                    break;
                }
            }
            if (result != 0) break;
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int search (int N, int[] arr, int sum) {
        int result = -1;

        int start = 0;
        int end = N-1;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (arr[mid] < sum) {
                start = mid + 1;
            }
            else if (arr[mid] > sum) {
                end = mid - 1;
            }
            else {
                result = mid;
                break;
            }
        }

        return result;
    }
}