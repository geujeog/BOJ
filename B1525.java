import java.util.*;
import java.io.*;

class B1525 {
    static int N;
    static String startNum;
    static Set<String> visit;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};
    static String makeMapArr = "123456780";
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        queue.add(new Tuple(startNum, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            String num = t.num;
            int cnt = t.cnt;

            if (visit.contains(num)) continue;
            if (num.equals(makeMapArr)) {
                result = cnt;
                break;
            }

            visit.add(num);

            int zeroIndex = num.indexOf("0");
            int x = zeroIndex / N;
            int y = zeroIndex % N;

            for (int i = 0; i < 4; i++) {
                int nextX = x + dx[i];
                int nextY = y + dy[i];

                if (nextX < 0 || nextY < 0 || nextX >= N || nextY >= N) continue;

                int nextIndex = nextX * N + nextY;
                StringBuilder sb = new StringBuilder(num);
                sb.setCharAt(zeroIndex, num.charAt(nextIndex));
                sb.setCharAt(nextIndex, num.charAt(zeroIndex));

                queue.add(new Tuple(sb.toString(), cnt+1));
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        String num;
        int cnt;

        public Tuple(String num, int cnt) {
            this.num = num;
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

        N = 3;
        startNum = "";
        visit = new HashSet<>();
        result = -1;

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                startNum += st.nextToken();
            }
        }

        br.close();
    }
}