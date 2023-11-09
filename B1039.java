import java.util.*;
import java.io.*;

class B1039 {
    static int N;
    static int K;
    static int result;
    static boolean[][] visit;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        dfs(N, 0);
    }

    public static void dfs(int num, int cnt) {
        if (String.valueOf(num).length() != String.valueOf(N).length()) return;
        if (visit[num][cnt]) return;
        if (cnt == K) {
            result = Math.max(result, num);
            return;
        }

        visit[num][cnt] = true;

        for (int i = 0; i < String.valueOf(N).length(); i++) {
            for (int j = i+1; j < String.valueOf(N).length(); j++) {
                // i와 j 위치 바꾸기
                StringBuilder sb = new StringBuilder(String.valueOf(num));
                sb.setCharAt(i, String.valueOf(num).charAt(j));
                sb.setCharAt(j, String.valueOf(num).charAt(i));

                dfs(Integer.parseInt(sb.toString()), cnt+1);
            }
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        result = -1;

        visit = new boolean[1000001][K+1];

        br.close();
    }
}