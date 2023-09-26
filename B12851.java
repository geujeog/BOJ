import java.util.*;
import java.io.*;

class B12851 {
    static int N;
    static int K;
    static int[] time;
    static int method;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        if (N == K) {
            time[K] = 0;
            method = 1;
        }
        else {
            Queue<Tuple> queue = new LinkedList<>();
            queue.add(new Tuple(N, 0));

            Arrays.fill(time, Integer.MAX_VALUE);

            while (!queue.isEmpty()) {
                Tuple tuple = queue.poll();

                for (int i = 0; i < 3; i++) {
                    int dx = tuple.x;

                    if (i == 0) dx -= 1;
                    else if (i == 1) dx += 1;
                    else dx *= 2;

                    if (dx < 0 || dx > 100000 || tuple.time + 1 > time[dx]) continue;

                    if (dx == K) {
                        if (tuple.time + 1 == time[dx]) method++;
                        else {
                            method = 1;
                            time[dx] = tuple.time + 1;
                        }
                    }
                    else {
                        time[dx] = tuple.time + 1;
                        queue.add(new Tuple(dx, tuple.time + 1));
                    }
                }
            }
        }
    }

    public static class Tuple{
        int x;
        int time;

        public Tuple(int x, int time) {
            this.x = x;
            this.time = time;
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(time[K]+"\n");
        bw.write(method+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        time = new int[100001];

        br.close();
    }
}