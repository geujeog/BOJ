import java.util.*;
import java.io.*;

public class B1245 {
    static int N;
    static int M;
    static int[][] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = 0;

        boolean[][] check = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!check[i][j]) {
                    if (checkMountain(i, j, check)) result++;
                }
            }
        }

        bw.write(result+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static boolean checkMountain (int i, int j, boolean[][] check) {
        Boolean isMountain = null;
        Queue<Tuple> queue = new LinkedList<>();

        for (Tuple tuple : getSameMountain(i, j)) {
            check[tuple.x][tuple.y] = true;
            queue.add(tuple);
        }

        boolean[][] visit = new boolean[N][M];

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (visit[x][y]) continue;

            if (arr[i][j] > arr[x][y]) {
                isMountain = true;
                continue;
            }
            else if (arr[i][j] < arr[x][y]) {
                isMountain = false;
                break;
            }

            visit[x][y] = true;

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));

            queue.add(new Tuple(x-1, y-1));
            queue.add(new Tuple(x-1, y+1));
            queue.add(new Tuple(x+1, y-1));
            queue.add(new Tuple(x+1, y+1));
        }

        return (isMountain == null ? false : isMountain);
    }

    public static List<Tuple> getSameMountain (int i, int j) {
        List<Tuple> mountain = new ArrayList<>();

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j));

        boolean[][] visit = new boolean[N][M];

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (visit[x][y]) continue;
            if (arr[i][j] != arr[x][y]) continue;

            mountain.add(new Tuple(x, y));
            visit[x][y] = true;

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));

            queue.add(new Tuple(x-1, y-1));
            queue.add(new Tuple(x-1, y+1));
            queue.add(new Tuple(x+1, y-1));
            queue.add(new Tuple(x+1, y+1));
        }

        return mountain;
    }

    public static class Tuple {
        int x;
        int y;

        public Tuple (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}