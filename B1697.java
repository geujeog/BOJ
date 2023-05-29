import java.util.*;
import java.io.*;

public class B1697 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        bw.write(bfs(N, K)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int bfs(int N, int K) {
        int result = 0;

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(N, 0));

        boolean[] check = new boolean[100001];

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int time = tuple.time;

            if (x < 0 || x > 100000) continue;
            if (check[x]) continue;

            if (x == K) {
                result = time;
                break;
            }

            check[x] = true;

            queue.add(new Tuple(x*2, time+1));
            queue.add(new Tuple(x-1, time+1));
            queue.add(new Tuple(x+1, time+1));
        }

        return result;
    }

    public static class Tuple {
        int x;
        int time;

        public Tuple (int x, int time) {
            this.x = x;
            this.time = time;
        }
    }
}