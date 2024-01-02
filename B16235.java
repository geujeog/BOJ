import java.util.*;
import java.io.*;

class B16235 {
    static int N, M, K;
    static int[][] point;
    static int[][] addPoint;
    static PriorityQueue<Tuple> trees;
    static int[][] diePoint;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 0; i < K; i++) {
            spring();
            summer();
            fall();
            winter();
        }
    }

    public static void winter() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                point[i][j] += addPoint[i][j];
            }
        }

        result = trees.size();
    }

    public static void fall() {
        PriorityQueue<Tuple> newTrees = new PriorityQueue<>();

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        while (!trees.isEmpty()) {
            Tuple t = trees.poll();
            int x = t.x;
            int y = t.y;
            int age = t.age;

            newTrees.add(t);

            if (age % 5 == 0) {
                for (int i = 0; i < 8; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                    newTrees.add(new Tuple(nx, ny, 1));
                }
            }
        }

        trees.addAll(newTrees);
    }

    public static void summer() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                point[i][j] += diePoint[i][j];
            }
        }
    }

    public static void spring() {
        PriorityQueue<Tuple> newTrees = new PriorityQueue<>();
        diePoint = new int[N][N];

        while (!trees.isEmpty()) {
            Tuple t = trees.poll();
            int x = t.x;
            int y = t.y;
            int age = t.age;

            if (point[x][y] >= age) {
                point[x][y] -= age;
                newTrees.add(new Tuple(x, y, age+1));
            }
            else {
                diePoint[x][y] += (int) age / 2;
            }
        }

        trees.addAll(newTrees);
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int age;

        public Tuple (int x, int y, int age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.age - t.age;
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
        point = new int[N][N];
        addPoint = new int[N][N];
        trees = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                point[i][j] = 5;
                addPoint[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int age = Integer.parseInt(st.nextToken());

            trees.add(new Tuple(x, y, age));
        }

        br.close();
    }
}