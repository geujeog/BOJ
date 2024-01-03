import java.util.*;
import java.io.*;

class B17143 {
    static int R, C;
    static int[][] arr;
    static Map<Integer, Tuple> map;
    static int[] directionX = {0, -1, 1, 0, 0};
    static int[] directionY = {0, 0, 0, 1, -1};
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 0; i < C; i++) {
            fishing(i);
            move();
        }
    }

    public static void move() {
        int[][] tmp = new int[R][C];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (arr[i][j] > 0) {
                    moveShark(tmp, i, j);
                }
            }
        }

        arr = tmp;
    }

    public static void moveShark(int[][] tmp, int i, int j) {
        int idx = arr[i][j];
        Tuple tuple = map.get(idx);

        for (int s = 0; s < tuple.s; s++) {
            i += directionX[tuple.d];
            j += directionY[tuple.d];

            if (i < 0 || j < 0 || i >= R || j >= C) {
                s -= 2;
                if (i < 0) tuple.d = 2;
                else if (i >= R) tuple.d = 1;
                else if (j < 0) tuple.d = 3;
                else if (j >= C) tuple.d = 4;
            }
        }

        if (tmp[i][j] > 0) {
            Tuple diffTuple = map.get(tmp[i][j]);

            if (diffTuple.z > tuple.z) {
                map.remove(idx);
            }
            else {
                map.remove(tmp[i][j]);
                tmp[i][j] = idx;
                map.put(idx, tuple);
            }
        }
        else {
            tmp[i][j] = idx;
            map.put(idx, tuple);
        }
    }

    public static void fishing(int j) {
        for (int i = 0; i < R; i++) {
            if (arr[i][j] > 0) {
                result += map.get(arr[i][j]).z;
                map.remove(arr[i][j]);
                arr[i][j] = 0;
                break;
            }
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int s;
        int d;
        int z;

        public Tuple (int s, int d, int z) {
            this.s = s;
            this.d = d;
            this.z = z; // 1위 2아래 3오 4왼
        }

        @Override
        public int compareTo(Tuple t) {
            return t.z - this.z;
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
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        arr = new int[R][C];
        map = new HashMap<>();

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            arr[r][c] = i;
            map.put(i, new Tuple(s, d, z));
        }

        br.close();
    }
}