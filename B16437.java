import java.util.*;
import java.io.*;

class B16437 {
    static int N;
    static int[][] parent;
    static int[] count;
    static long[] move;
    static boolean[] visit;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // bfs
        // 위상정렬
        for (int i = 1; i <= N; i++) {
            if (count[i] == 0 && !visit[i]) {
                visit[i] = true;
                bfs(i);
            }
        }
    }

    public static void bfs(int s) {
        Queue<Integer> queue = new ArrayDeque<>();

        move[s] = Math.max(0l, parent[s][1]);
        queue.add(s);

        while (!queue.isEmpty()) {
            int q = queue.poll();

            if (q == 1) break;

            int p = parent[q][0];
            count[p]--;
            visit[p] = true;
            move[p] = Math.max(0l, move[p] + move[q]);

            if (count[p] == 0) {
                move[p] = Math.max(0l, move[p] + (long) parent[p][1]);
                queue.add(p);
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(move[1]+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        parent = new int[N+1][2];
        count = new int[N+1];
        move = new long[N+1];
        visit = new boolean[N+1];

        for (int i = 2; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            String command = st.nextToken();

            int share = ("W".equals(command)) ? -1 : 1;
            int num = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            parent[i][0] = p;
            parent[i][1] = num * share;
            count[p]++;
        }

        br.close();
    }
}