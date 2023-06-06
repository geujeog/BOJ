import java.util.*;
import java.io.*;

public class B16234 {
    static int N;
    static int L;
    static int R;
    static int[][] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bw.write(bfs()+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int bfs () {
        int dateCnt = 0;

        while (isOpen()) {
            dateCnt++;
        }

        return dateCnt;
    }

    public static boolean isOpen () {
        boolean open = false;

        int[][] sum = new int[2][N*N]; // [0]행에는 같은 연합 인구수, [1]행에는 같은 연합 나라수
        int[] parent = new int[N*N]; // 같은 연합
        initParent(parent);

        boolean[][] check = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!check[i][j]) {
                    checkCountry(i, j, check, parent);
                }
            }
        }

        // 인구수, 나라수 카운팅
        for (int i = 0; i < N*N; i++) {
            int p = find(parent, i);
            sum[0][p] += arr[i/N][i%N];
            sum[1][p]++;
        }

        // 같은 연합끼리 인구수 나눔
        for (int i = 0; i < N*N; i++) {
            int p = find(parent, i);

            int calculate = (int) (sum[0][p] / sum[1][p]);

            if (calculate != arr[i/N][i%N]) open = true;
            arr[i/N][i%N] = calculate;
        }

        return open;
    }

    public static void checkCountry (int i, int j, boolean[][] check, int[] parent) {
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j, 0));

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int beforePopulation = tuple.population;

            if (x < 0 || y < 0 || x >= N || y >= N) continue;
            if (check[x][y]) continue;

            if (beforePopulation != 0) {
                int diff = Math.abs(beforePopulation - arr[x][y]);

                if (diff >= L && diff <= R) {
                    union(parent, i*N+j, x*N+y);
                }
                else continue;
            }

            check[x][y] = true;

            queue.add(new Tuple(x-1, y, arr[x][y]));
            queue.add(new Tuple(x+1, y, arr[x][y]));
            queue.add(new Tuple(x, y-1, arr[x][y]));
            queue.add(new Tuple(x, y+1, arr[x][y]));
        }
    }

    public static class Tuple {
        int x;
        int y;
        int population;

        public Tuple (int x, int y, int population) {
            this.x = x;
            this.y = y;
            this.population = population;
        }
    }

    public static void initParent (int[] parent) {
        for (int i = 0; i < N*N; i++) {
            parent[i] = i;
        }
    }

    public static int find (int[] parent, int x) {
        if (parent[x] == x) return x;

        return find(parent, parent[x]);
    }

    public static void union (int[] parent, int x, int y) {
        x = find(parent, x);
        y = find(parent, y);

        if (x < y) {
            parent[y] = x;
        }
        else {
            parent[x] = y;
        }
    }
}