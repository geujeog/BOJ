import java.util.*;
import java.io.*;

class B16236 {
    static int N;
    static int[][] arr;
    static int call;
    static int sharkX, sharkY, sharkSize;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        while (true) {
            if (!bfs()) break;
        }
    }

    public static boolean bfs() {
        int eatFish = 0;

        while (eatFish++ < sharkSize) {
            boolean isEat = false;

            PriorityQueue<Tuple> queue = new PriorityQueue<>();
            queue.add(new Tuple(sharkX, sharkY, 0));

            boolean[][] visit = new boolean[N][N];

            while (!queue.isEmpty()) {
                Tuple t = queue.poll();
                int x = t.x;
                int y = t.y;
                int time = t.time;

                if (x < 0 || y < 0 || x >= N || y >= N) continue;
                if (visit[x][y] || arr[x][y] > sharkSize) continue;

                visit[x][y] = true;

                if (arr[x][y] != 0 && arr[x][y] < sharkSize) {
                    isEat = true;
                    arr[x][y] = 0;
                    sharkX = x;
                    sharkY = y;
                    call += time;
                    break;
                }

                queue.add(new Tuple(x-1, y, time+1));
                queue.add(new Tuple(x+1, y, time+1));
                queue.add(new Tuple(x, y-1, time+1));
                queue.add(new Tuple(x, y+1, time+1));
            }

            if (!isEat) return false;
        }

        sharkSize += 1;

        return true;
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int time;

        public Tuple(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        @Override
        public int compareTo(Tuple t) {
            if (t.time == this.time) {
                if (t.x == this.x) {
                    return this.y - t.y;
                }
                return this.x - t.x;
            }
            return this.time - t.time;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(call+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());

                if (arr[i][j] == 9) {
                    arr[i][j] = 0;
                    sharkX = i;
                    sharkY = j;
                    sharkSize = 2;
                }
            }
        }

        br.close();
    }
}