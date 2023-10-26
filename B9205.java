import java.util.*;
import java.io.*;
import java.util.List;

class B9205 {
    static int MAX_DISTANCE = 50;
    static int MAX_BOX = 20;
    static List<Integer>[] list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(br.readLine());

            int[][] point = new int[N+2][2];
            list = new ArrayList[N + 2];

            // 상근이네
            // 편의점
            // 페스티벌
            for (int n = 0; n < N + 2; n++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                point[n][0] = x;
                point[n][1] = y;

                list[n] = new ArrayList<>();
            }

            for (int i = 0; i < N+2; i++) {
                for (int j = i+1; j < N+2; j++) {
                    int distance = Math.abs(point[i][0] - point[j][0]) + Math.abs(point[i][1] - point[j][1]);

                    if (distance <= MAX_DISTANCE * MAX_BOX) {
                        list[i].add(j);
                        list[j].add(i);
                    }
                }
            }

            if (canGo(point, N+2)) bw.write("happy" + "\n");
            else bw.write("sad" + "\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static boolean canGo(int[][] point, int n) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        boolean[] visit = new boolean[n];

        while (!queue.isEmpty()) {
            Integer idx = queue.poll();

            if (visit[idx]) continue;
            else visit[idx] = true;

            if (idx == n-1) return true;

            for (Integer i : list[idx]) {
                queue.add(i);
            }
        }

        return false;
    }
}