import java.util.*;
import java.io.*;

class B16928 {
    static int LEN = 10;
    static int[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        result = Integer.MAX_VALUE;

        play();
    }

    public static void play() {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        queue.add(new Tuple(1, 0));

        boolean[][] visit = new boolean[LEN][LEN+1];

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int v = tuple.v;
            int x = v / LEN;
            int y = v % LEN + 1;
            int cnt = tuple.cnt;

            if (v == LEN*LEN) {
                result = cnt;
                break;
            }

            if (v > LEN*LEN || visit[x][y]) continue;
            else visit[x][y] = true;

            // 사다리, 뱀 없음
            if (arr[x][y] == 0) {
                for (int i = 1; i <= 6; i++) {
                    queue.add(new Tuple(v + i, cnt+1));
                }
            }
            else {
                queue.add(new Tuple(arr[x][y], cnt));
            }
        }
    }

    public static class Tuple implements Comparable<Tuple>{
        int v;
        int cnt;

        public Tuple(int v, int cnt) {
            this.v = v;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.cnt - t.cnt;
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

        arr = new int[LEN][LEN+1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[a/LEN][a%LEN+1] = b;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[a/LEN][a%LEN+1] = b;
        }

        br.close();
    }
}