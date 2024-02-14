import java.util.*;
import java.io.*;

class B14867 {
    static int A, B;
    static int endA, endB;
    static Set<Tuple> set;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<Tuple> queue = new ArrayDeque<>();
        queue.add(new Tuple(0, 0, 0));
        set.add(new Tuple(0, 0, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int a = t.a;
            int b = t.b;
            int time = t.time;

            if (a == endA && b == endB) {
                result = time;
                break;
            }

            int atobA = 0, atobB = 0;
            int btoaA = 0, btoaB = 0;
            if (a + b <= B) {
                atobA = 0;
                atobB = a + b;
            }
            else {
                atobA = a + b - B;
                atobB = B;
            }

            if (a + b <= A) {
                btoaA = a + b;
                btoaB = 0;
            }
            else {
                btoaA = A;
                btoaB = a + b - A;
            }

            int[] nextA = {A, a, 0, a, atobA, btoaA};
            int[] nextB = {b, B, b, 0, atobB, btoaB};

            for (int d = 0; d < nextA.length; d++) {
                Tuple nt = new Tuple(nextA[d], nextB[d], time + 1);

                if (!set.contains(nt)) {
                    set.add(nt);
                    queue.add(nt);
                }
            }
        }
    }

    public static class Tuple {
        int a;
        int b;
        int time;

        public Tuple(int a, int b, int time) {
            this.a = a;
            this.b = b;
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            Tuple t = (Tuple) o;
            if (t.a == this.a && t.b == this.b) return true;
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
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
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        endA = Integer.parseInt(st.nextToken());
        endB = Integer.parseInt(st.nextToken());
        set = new HashSet<>();
        result = -1;

        br.close();
    }
}