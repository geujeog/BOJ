import java.util.*;
import java.io.*;

class B6086 {
    static int V;
    static int[][] capability, flow;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 네트워크 유량 알고리즘

        bfs(0, 'Z' - 'A');
    }

    public static void bfs(int s, int e) {
        int[] parent = new int[V];
        Queue<Integer> queue = new ArrayDeque<>();

        while (true) {
            Arrays.fill(parent, -1);
            queue = new ArrayDeque<>();

            parent[s] = s;
            queue.add(s);

            while (!queue.isEmpty() && parent[s] != -1) {
                int q = queue.poll();
                for (int i = 0; i < V; i++) {
                    if (capability[q][i] - flow[q][i] > 0 && parent[i] == -1) {
                        parent[i] = q;
                        queue.add(i);
                    }
                }
            }

            if (parent[e] == -1) break;

            int amount = Integer.MAX_VALUE;
            for (int i = e; i != s; i = parent[i]) {
                amount = Math.min(amount, capability[parent[i]][i] - flow[parent[i]][i]);
            }

            for (int i = e; i != s; i = parent[i]) {
                flow[i][parent[i]] -= amount;
                flow[parent[i]][i] += amount;
            }

            result += amount;
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

        V = 'Z'-'A' + 'z'-'a' + 2; // 52, 대문자소문자
        flow = new int[V][V];
        capability = new int[V][V];

        int N = Integer.parseInt(br.readLine());
        while (N-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = atoi(st.nextToken().charAt(0));
            int b = atoi(st.nextToken().charAt(0));
            int w = Integer.parseInt(st.nextToken());

            capability[a][b] += w;
            capability[b][a] += w;
        }

        br.close();
    }

    public static int atoi(char c) {
        if (Character.isUpperCase(c)) {
            return c - 'A';
        }
        return 'Z' - 'A' + c - 'a' + 1;
    }
}