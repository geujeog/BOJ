import java.util.*;
import java.io.*;

public class B11657 {
    class Main {
        static int N;
        static int M;
        static List<Tuple>[] list;
        static long[] time;

        public static void main(String[] args) throws IOException {
            input();
            print();
        }

        public static boolean solution() {
            time[1] = 0;

            boolean update = false;
            for (int i = 1; i < N; i++) {
                update = false;

                for (int j = 1; j <= N; j++) {
                    for (Tuple t : list[j]) {
                        if (time[j] == Integer.MAX_VALUE) break;

                        if (time[t.v] > time[j] + t.w) {
                            time[t.v] = time[j] + t.w;
                            update = true;
                        }
                    }
                }

                // 바뀌는게 더이상 없으면 빠져나옴
                if (!update) break;
            }

            if (update) {
                for (int i = 1; i <= N; i++) {
                    for (Tuple t : list[i]) {
                        if (time[i] == Integer.MAX_VALUE) break;

                        if (time[t.v] > time[i] + t.w) return true;
                    }
                }
            }

            return false;
        }

        public static class Tuple {
            int v;
            int w;

            public Tuple(int v, int w) {
                this.v = v;
                this.w = w;
            }
        }

        public static void print() throws IOException {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

            if (solution()) {
                bw.write(-1 + "");
            } else {
                for (int i = 2; i <= N; i++) {
                    if (time[i] == Integer.MAX_VALUE) bw.write(-1 + "\n");
                    else bw.write(time[i] + "\n");
                }
            }

            bw.flush();
            bw.close();
        }

        public static void input() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            list = new ArrayList[N + 1];
            time = new long[N + 1];

            Arrays.fill(time, Integer.MAX_VALUE);

            for (int i = 1; i <= N; i++) {
                list[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                list[Integer.parseInt(st.nextToken())].add(new Tuple(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
            }

            br.close();
        }
    }
}