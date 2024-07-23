import java.util.*;
import java.io.*;

class B17616 {
    static int N, X;
    static List<Integer>[] toA;
    static List<Integer>[] toB;
    static int U, V;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // dfs
        U = bfs(toA) + 1;
        V = N - bfs(toB);
    }

    public static int bfs(List<Integer>[] to) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visit = new boolean[N+1];
        int cnt = 0;

        queue.add(X);
        visit[X] = true;

        while (!queue.isEmpty()) {
            int q = queue.poll();

            for (int nx: to[q]) {
                if (!visit[nx]) {
                    cnt++;
                    visit[nx] = true;
                    queue.add(nx);
                }
            }
        }

        return cnt;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(U+" "+V);

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        toA = new ArrayList[N+1];
        toB = new ArrayList[N+1];

        for (int i = 1; i <= N; i++) {
            toA[i] = new ArrayList<>();
            toB[i] = new ArrayList<>();
        }

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            toA[B].add(A);
            toB[A].add(B);
        }

        br.close();
    }
}