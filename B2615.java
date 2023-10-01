import java.util.*;
import java.io.*;

class B2615 {
    static int N = 19;
    static int[][] arr;
    static int win;
    static int[] winSite;
    static boolean[][][] visit;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (arr[i][j] != 0) bfs(i, j);
            }
        }
    }

    public static void bfs(int i, int j) {
        Queue<Tuple> queue = new LinkedList<>();
        for (int dir = 0; dir < 8; dir++) {
            queue.add(new Tuple(i, j, dir));
        }

        PriorityQueue<Tuple>[] cntArr = new PriorityQueue[4];
        for (int q = 0; q < 4; q++) {
            cntArr[q] = new PriorityQueue<>();
            cntArr[q].add(new Tuple(i, j, q));
        }

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int direction = tuple.direction;
            int oneDirection = getOneDirection(direction);

            if (x <= 0 || y <= 0 || x > N || y > N) continue;
            if (visit[x][y][oneDirection]) continue;
            if (arr[i][j] != arr[x][y]) continue;

            if (x != i || y != j) {
                cntArr[oneDirection].add(new Tuple(x, y, direction));
            }

            queue.add(move(x, y, direction));
        }

        for (PriorityQueue<Tuple> cnt : cntArr) {
            if (cnt.size() == 5) {
                win = arr[i][j];
                Tuple first = cnt.poll();
                winSite[0] = first.x;
                winSite[1] = first.y;
                visit[first.x][first.y][getOneDirection(first.direction)] = true;
            }

            for (Tuple t : cnt) {
                visit[t.x][t.y][getOneDirection(t.direction)] = true;
            }
        }
    }

    public static Tuple move(int x, int y, int direction) {
        if (direction == 0) return new Tuple(x-1, y, direction);
        else if (direction == 1) return new Tuple(x-1, y+1, direction);
        else if (direction == 2) return new Tuple(x, y+1, direction);
        else if (direction == 3) return new Tuple(x+1, y+1, direction);
        else if (direction == 4) return new Tuple(x+1, y, direction);
        else if (direction == 5) return new Tuple(x+1, y-1, direction);
        else if (direction == 6) return new Tuple(x, y-1, direction);
        else return new Tuple(x-1, y-1, direction);
    }

    public static int getOneDirection(int direction) {
        if (direction == 0 || direction == 4) return 0;
        else if (direction == 1 || direction == 5) return 1;
        else if (direction == 2 || direction == 6) return 2;
        else return 3;
    }

    public static class Tuple implements Comparable<Tuple>{
        int x;
        int y;
        int direction;

        public Tuple(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public int compareTo(Tuple t) {
            if (this.y == t.y) {
                return this.x - t.x;
            }
            return this.y - t.y;
        }
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(win+"\n");
        if (win != 0) {
            bw.write(winSite[0]+" "+winSite[1]);
        }

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        arr = new int[N+1][N+1];
        visit = new boolean[N+1][N+1][4];
        winSite = new int[2];

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 1; j <= N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}