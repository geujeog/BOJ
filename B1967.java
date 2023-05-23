import java.util.*;
import java.io.*;

public class B1967 {
    static int result;
    static int[] dp;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        List<Tuple>[] list = new ArrayList[N+1];
        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<Tuple>();
        }

        for (int i = 1; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            list[v1].add(new Tuple(v2, value));
        }

        dp = new int[N+1];

        getLen(list, 1);

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void getLen (List<Tuple>[] list, int v) {
        int maxFirst = 0;
        int maxSecond = 0;

        for (Tuple tuple : list[v]) {
            int i = tuple.child;
            int value = tuple.value;

            getLen(list, i);

            if (maxFirst < dp[i] + value) {
                maxSecond = maxFirst;
                maxFirst = dp[i] + value;
            }
            else {
                maxSecond = Math.max(maxSecond, dp[i] + value);
            }
        }
        dp[v] = maxFirst;

        // 자식 노드 중 가장 큰 값 vs 현재 노드를 기준으로 꺽인 경로
        result = Math.max(result, Math.max(dp[v], (maxFirst + maxSecond)));
    }

    public static class Tuple {
        int child;
        int value;

        public Tuple (int child, int value) {
            this.child = child;
            this.value = value;
        }
    }
}