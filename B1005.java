import java.io.*;
import java.util.*;

public class B1005 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine()); // 테스트케이스 개수

        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()); // 건물 개수
            int K = Integer.parseInt(st.nextToken()); // 규칙 개수

            int[] cost = new int[N+1]; // 건물 당 드는 비용(시간)
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; i++) {
                cost[i] = Integer.parseInt(st.nextToken());
            }

            List<Integer>[] list = new ArrayList[N+1]; // 연결된 건물
            for (int i = 1; i <= N; i++) {
                list[i] = new ArrayList<>();
            }

            for (int k = 0; k < K; k++) {
                st = new StringTokenizer(br.readLine());
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());

                list[v2].add(v1);
            }

            int W = Integer.parseInt(br.readLine()); // 최종 건물

            bw.write(bfs(list, cost, W)+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static int bfs (List<Integer>[] list, int[] cost, int W) {
        int result = 0;

        int[] checkLast = new int[list.length+1]; // 0일 경우, 선수 건물 짓기 (= 무한루프 피하기)
        int[] countMax = new int[list.length+1]; // 현재 건물을 짓기까지 걸리는 최고 시간 비용

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(W, cost[W]));
        checkLast[W]++;

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int idx = tuple.idx;
            int count = tuple.count;

            checkLast[idx]--;
            countMax[idx] = Math.max(countMax[idx], count);

            if (checkLast[idx] != 0) continue;

            for (Integer i : list[idx]) {
                checkLast[i]++;
                queue.add(new Tuple(i, countMax[idx] + cost[i]));
            }

            if (list[idx].size() == 0) result = Math.max(result, countMax[idx]);
        }

        return result;
    }

    public static class Tuple {
        int idx;
        int count;

        public Tuple (int idx, int count) {
            this.idx = idx;
            this.count = count;
        }
    }
}