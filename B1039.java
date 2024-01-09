import java.util.*;
import java.io.*;

class B1039 {
    static int N, M, K;
    static boolean[][] visit;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        visit[N][0] = true;
        dfs(N, 0);
    }

    public static void dfs(int num, int cnt) {
        if (cnt == K) {
            result = Math.max(result, num);
            return;
        }

        for (int i = 0; i < M; i++) {
            for (int j = i+1; j < M; j++) {
                int pi = (int) Math.pow(10, M - i - 1);
                int pj = (int) Math.pow(10, M - j - 1);
                int ri = (num / pi) % 10;
                int rj = (num / pj) % 10;

                int nextNum = num;
                nextNum -= pi * ri + pj * rj;
                nextNum += pi * rj + pj * ri;

                if (nextNum == 0 || (i == 0 && rj == 0)) continue;
                if (visit[nextNum][cnt + 1]) continue;

                visit[nextNum][cnt + 1] = true;
                dfs(nextNum, cnt + 1);
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = String.valueOf(N).length();
        K = Integer.parseInt(st.nextToken());
        visit = new boolean[1000001][K+1];
        result = -1;

        br.close();
    }
}