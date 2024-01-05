import java.util.*;
import java.io.*;

class B14442 {
    static int N, M, K;
    static char[][] arr;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        Queue<Tuple> queue = new LinkedList<>();
        boolean[][][] visit = new boolean[K+1][N][M];
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        queue.add(new Tuple(0, 0, 1, 0));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;
            int move = t.move;
            int broken = t.broken;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (broken > K || visit[broken][x][y]) continue;
            if (x == N-1 && y == M-1) {
                result = move;
                break;
            }

            visit[broken][x][y] = true;

            int cnt = (arr[x][y] == '1') ? 1 : 0;
            for (int i = 0; i < dx.length; i++) {
                queue.add(new Tuple(x+dx[i], y+dy[i], move+1, broken+cnt));
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int move;
        int broken;

        public Tuple (int x, int y, int move, int broken) {
            this.x = x;
            this.y = y;
            this.move = move;
            this.broken = broken;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.move - t.move;
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
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new char[N][M];
        result = -1;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}