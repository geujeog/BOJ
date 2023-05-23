import java.util.*;
import java.io.*;

public class B5214 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Integer>[] list = new ArrayList[N+M+1];
        for (int i = 1; i <= N+M; i++) {
            list[i] = new ArrayList<>();
        }

        for (int m = 1; m <= M; m++) {
            st = new StringTokenizer(br.readLine());

            int hyper = N+m;

            for (int k = 0; k < K; k++) {
                int v = Integer.parseInt(st.nextToken());

                list[v].add(hyper);
                list[hyper].add(v);
            }
        }

        bw.write(bfs(list, N)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int bfs (List<Integer>[] list, int N) {
        int result = -1;

        boolean[] check = new boolean[list.length];
        check[1] = true;

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(1, 1));

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int idx = tuple.idx;
            int move = tuple.move;

            if (idx == N) {
                result = move;
                break;
            }

            for (int i : list[idx]) {
                if (!check[i]) {
                    check[i] = true;

                    if (i > N) queue.add(new Tuple(i, move));
                    else queue.add(new Tuple(i, move+1));
                }
            }
        }

        return result;
    }

    public static class Tuple {
        int idx;
        int move;

        public Tuple (int idx, int move) {
            this.idx = idx;
            this.move = move;
        }
    }
}