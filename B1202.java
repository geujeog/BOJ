import java.util.*;
import java.io.*;

class B1202 {
    static int N;
    static int K;
    static int[][] jewelry;
    static int[] weight;
    static long result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);

        int k = 0;
        for (int i = 0; i < K; i++) {
            for (int j = k; j < N; j++, k++) {
                if (jewelry[j][0] > weight[i]) break;
                queue.add(jewelry[j][1]);
            }

            if (!queue.isEmpty()) {
                result += queue.poll();
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int bag;
        int value;

        public Tuple(int bag, int value) {
            this.bag = bag;
            this.value = value;
        }

        @Override
        public int compareTo(Tuple t) {
            return t.value - this.value;
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
        K = Integer.parseInt(st.nextToken());
        jewelry = new int[N][2];
        weight = new int[K];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            jewelry[i][0] = Integer.parseInt(st.nextToken());
            jewelry[i][1] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < K; i++) {
            weight[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(jewelry, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) return b[1] - a[1];
                return a[0] - b[0];
            }
        });
        Arrays.sort(weight);

        br.close();
    }
}