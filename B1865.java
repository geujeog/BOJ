import java.util.*;
import java.io.*;

class B1865 {
    static int N;
    static List<Tuple>[] list;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            list = new ArrayList[N+1];
            for (int i = 1; i <= N; i++) {
                list[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                list[a].add(new Tuple(b, w));
                list[b].add(new Tuple(a, w));
            }
            for (int i = 0; i < W; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                list[a].add(new Tuple(b, (-1) * w));
            }

            boolean canMinus = false;
            for (int i = 1; i <= N; i++) {
                if (bellmanford(i)) {
                    canMinus = true;
                    break;
                }
            }

            if (canMinus) bw.write("YES"+"\n");
            else bw.write("NO"+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static boolean bellmanford(int s) {
        int[] dist = new int[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[s] = 0;

        boolean isChange = false;
        for (int n = 1; n < N; n++) {
            isChange = false;

            for (int i = 1; i <= N; i++) {
                for (Tuple t : list[i]) {
                    int v = t.v;
                    int w = t.w;

                    if (dist[i] != Integer.MAX_VALUE && dist[v] > dist[i] + w) {
                        dist[v] = dist[i] + w;
                        isChange = true;
                    }
                }
            }

            if (!isChange) break;
        }

        if (isChange) {
            for (int i = 1; i <= N; i++) {
                for (Tuple t : list[i]) {
                    int v = t.v;
                    int w = t.w;

                    // 갱신됨
                    if (dist[i] != Integer.MAX_VALUE && dist[v] > dist[i] + w) {
                        return true;
                    }
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
}