import java.util.*;
import java.io.*;

class B14868 {
    static int N;
    static int K;
    static int[][] arr;
    static Queue<int[]> queue;
    static int[] parent;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 같은 시간끼리 문명 전파

        int[] q;
        int nx, ny;
        int a, b, cnt;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        Queue<int[]> civil = new ArrayDeque<>();

        while (true) {
            cnt = queue.size();
            for (int i = 0; i < cnt; i++) {
                q = queue.poll();

                for (int d = 0; d < dx.length; d++) {
                    nx = q[0] + dx[d];
                    ny = q[1] + dy[d];

                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                    a = arr[q[0]][q[1]];
                    b = arr[nx][ny];

                    if (b == 0) {
                        queue.add(new int[]{nx, ny, a});
                    }
                    else if (parent[a] != parent[b]) {
                        civil.add(new int[]{a, b});
                    }
                }
            }

            while (!civil.isEmpty()) {
                q = civil.poll();
                if (!union(q[0], q[1]) && K == 1) return;
            }

            result++;
            cnt = queue.size();
            for (int i = 0; i < cnt; i++) {
                q = queue.poll();

                if (arr[q[0]][q[1]] != 0) continue;

                arr[q[0]][q[1]] = q[2];
                queue.add(new int[]{q[0], q[1]});
            }
        }
    }

    public static boolean union(int a, int b) {
        a = getParent(a);
        b = getParent(b);

        if (a < b) {
            K--;
            parent[b] = a;
            return false;
        }
        else if (a > b) {
            K--;
            parent[a] = b;
            return false;
        }
        return true;
    }

    public static int getParent(int v) {
        if (v == parent[v]) return v;
        return getParent(parent[v]);
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
        K = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
        queue = new ArrayDeque<>();
        parent = new int[K+1];
        result = 0;
        int a, b;

        for (int i = 1; i <= K; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken()) - 1;
            b = Integer.parseInt(st.nextToken()) - 1;
            arr[a][b] = i;
            queue.add(new int[]{a, b});
            parent[i] = i;
        }

        br.close();
    }
}