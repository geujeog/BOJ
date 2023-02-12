import java.util.*;
import java.io.*;

class B1182 {
    static int N;
    static int sum;
    static int[] arr;
    static int result = 0;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        sum = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        back(0, 0);

        if (sum == 0) bw.write(result-1+"");
        else bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void back(int idx, int count) {
        if (idx == N) {
            if (count == sum) {
                result++;
            }
            return;
        }

        back(idx + 1, count + arr[idx]);
        back(idx + 1, count);
    }
}