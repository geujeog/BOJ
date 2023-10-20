import java.util.*;
import java.io.*;

class B20056 {
    static int N;
    static int M;
    static int K;
    static List<Tuple>[][] arr;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        for (int k = 0; k < K; k++) {
            move();
            combination();
        }

        result = getResult();
    }

    public static int getResult() {
        int sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (Tuple t : arr[i][j]) {
                    sum += t.m;
                }
            }
        }

        return sum;
    }

    public static void combination() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j].size() <= 1) continue;

                int mSum = 0;
                int sSum = 0;
                boolean isAllOdd = true;
                boolean isAllEven = true;

                for (Tuple t : arr[i][j]) {
                    mSum += t.m;
                    sSum += t.s;

                    if (isAllOdd) isAllOdd = t.d % 2 == 1 ? true : false;
                    if (isAllEven) isAllEven = t.d % 2 == 0 ? true : false;
                }

                mSum /= 5;
                sSum /= arr[i][j].size();

                arr[i][j].clear();

                if (mSum == 0) continue;

                if (isAllOdd ^ isAllEven) {
                    arr[i][j].add(new Tuple(mSum, sSum, 0));
                    arr[i][j].add(new Tuple(mSum, sSum, 2));
                    arr[i][j].add(new Tuple(mSum, sSum, 4));
                    arr[i][j].add(new Tuple(mSum, sSum, 6));
                }
                else {
                    arr[i][j].add(new Tuple(mSum, sSum, 1));
                    arr[i][j].add(new Tuple(mSum, sSum, 3));
                    arr[i][j].add(new Tuple(mSum, sSum, 5));
                    arr[i][j].add(new Tuple(mSum, sSum, 7));
                }
            }
        }
    }

    public static void move() {
        List<Tuple>[][] tmp = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (Tuple t : arr[i][j]) {
                    int[] next = moveOne(i, j, t.m, t.s, t.d);

                    tmp[next[0]][next[1]].add(new Tuple(t.m, t.s, t.d));
                }
            }
        }

        arr = tmp;
    }

    public static int[] moveOne(int x, int y, int m, int s, int d) {

        for (int i = 0; i < s; i++) {
            if (d == 0) { x--; }
            else if (d == 1) { x--; y++; }
            else if (d == 2) { y++; }
            else if (d == 3) { x++; y++; }
            else if (d == 4) { x++; }
            else if (d == 5) { x++; y--; }
            else if (d == 6) { y--;}
            else { x--; y--; }

            if (x == -1) x = N-1;
            if (x == N) x = 0;

            if (y == -1) y = N-1;
            if (y == N) y = 0;
        }

        return new int[]{x, y};
    }

    public static class Tuple {
        int m; // 질량
        int s; // 속도
        int d; // 방향

        public Tuple(int m, int s, int d) {
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }

    public static void print() throws IOException {
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
        arr = new ArrayList[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            arr[r][c].add(new Tuple(m, s, d));
        }

        br.close();
    }
}