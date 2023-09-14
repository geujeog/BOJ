import java.util.*;
import java.io.*;

class B2580 {
    static int N = 9;
    static int[][] arr;
    static List<Tuple> emptyList;

    public static void main (String[] args) throws IOException {
        init();
        solve();
        print();
    }

    public static void solve() {
        backtracking(0);
    }

    public static boolean backtracking(int idx) {
        if (idx == emptyList.size()) return true;

        Tuple tuple = emptyList.get(idx);
        int x = tuple.x;
        int y = tuple.y;

        for (int i = 0; i < N; i++) {
            if (check(x, y, i+1)) {
                arr[x][y] = i+1;

                if(backtracking(idx+1)) return true;
                else arr[x][y] = 0;
            }
        }

        return false;
    }

    public static boolean check(int x, int y, int num) {
        for (int i = 0; i < N; i++) {
            if (arr[i][y] == num) return false;
            if (arr[x][i] == num) return false;
        }

        int share = x / 3 * 3;
        int remain = y / 3 * 3;

        for (int i = share; i < share+3; i++) {
            for (int j = remain; j < remain+3; j++) {
                if (arr[i][j] == num) return false;
            }
        }

        return true;
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                bw.write(arr[i][j]+" ");
            }
            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        arr = new int[N][N];
        emptyList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());

                if (arr[i][j] == 0) emptyList.add(new Tuple(i, j));
            }
        }

        br.close();
    }

    public static class Tuple{
        int x;
        int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}