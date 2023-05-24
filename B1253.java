import java.util.*;
import java.io.*;

public class B1253 {
    static int N;
    static int[] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        int result = 0;
        for (int i = 0; i < N; i++) {
            result += good(i);
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int good (int i) {
        int result = 0;
        int search = arr[i];

        int startIdx = 0;
        int endIdx = N-1;

        while (startIdx < endIdx) {
            if (startIdx == i) startIdx++;
            else if (endIdx == i) endIdx--;
            else {
                int sum = arr[startIdx] + arr[endIdx];

                if (search > sum) startIdx++;
                else if (search < sum) endIdx--;
                else {
                    result = 1;
                    break;
                }
            }
        }

        return result;
    }
}