import java.util.*;
import java.io.*;

public class B1109 {


    static int N;
    static int M;
    static int[][] arr;

    static int islandCnt;

    static int[] parent;
    static int[] height;
    static int maxHeight;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j) == 'x' ? 0 : -1;
            }
        }

        island();

        if (islandCnt != 0) {
            parent = new int[islandCnt+1];
            height = new int[islandCnt+1];
            for (int i = 1; i <= islandCnt; i++) {
                parent[i] = i;
            }

            checkAround();
            traceHeight();

            int[] cnt = new int[maxHeight+1];
            for (int i = 1; i <= islandCnt; i++) {
                cnt[height[i]]++;
            }

            for (int i = 0; i <= maxHeight; i++) {
                bw.write(cnt[i]+" ");
            }
        }
        else bw.write("-1");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void traceHeight() {
        for (int i = 1; i <= islandCnt; i++) {
            trace(i, height[i]);
        }
    }

    public static void trace(int number, int h) {
        maxHeight = Math.max(maxHeight, height[number]);

        if (number == parent[number]) return;

        height[parent[number]] = Math.max(height[parent[number]], h+1);
        trace(parent[number], height[parent[number]]);
    }

    public static void checkAround() {
        for (int k = 1; k <= islandCnt; k++) {
            Queue<Tuple> queue = new LinkedList<>();

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (arr[i][j] == k) {
                        queue.add(new Tuple(i, j));
                    }
                }
            }

            checkAroundBFS(queue, k);
        }
    }

    private static void checkAroundBFS(Queue<Tuple> queue, int number) {
        boolean goOut = false;

        boolean[][] check = new boolean[N][M];
        TreeSet<Integer> set = new TreeSet<>();

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;

            if (x < 0 || y < 0 || x >= N || y >= M) {
                goOut = true;
                break;
            }
            if (check[x][y]) continue;
            if (arr[x][y] != -1 && arr[x][y] != number) {
                set.add(arr[x][y]);
                continue;
            }

            check[x][y] = true;

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));
        }

        // 안쪽 섬일 경우 parent 갱신
        if (!goOut) {
            parent[number] = set.first();
        }
    }

    public static void island() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] == 0) islandLabeling(i, j, ++islandCnt);
            }
        }
    }

    public static void islandLabeling(int i, int j, int number) {
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j));

        boolean[][] check = new boolean[N][M];

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (arr[x][y] == -1) continue;
            if (check[x][y]) continue;

            arr[x][y] = number;
            check[x][y] = true;

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));
            queue.add(new Tuple(x-1, y-1));
            queue.add(new Tuple(x-1, y+1));
            queue.add(new Tuple(x+1, y-1));
            queue.add(new Tuple(x+1, y+1));
        }
    }

    public static class Tuple {
        int x;
        int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}