import java.util.*;
import java.io.*;

public class B22862 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int result = 0;
        int deleteChance = 0;
        int start = 0;
        int end = 0;

        while (end < N) {
            if (arr[end] % 2 == 0) {
                result = Math.max(result, end - start + 1 - deleteChance);
            }
            else {
                deleteChance++;

                while (deleteChance > K) {
                    if (arr[start++] % 2 == 1) deleteChance--;
                }
            }

            end++;
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }
}
