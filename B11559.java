import java.util.*;
import java.io.*;

class B11559 {
    static int N = 12;
    static int M = 6;
    static char[][] arr;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        while (bumb()) {
            result++;
        }
    }

    public static boolean bumb() {
        List<Tuple> empty = new ArrayList<>();
        boolean[][] visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visit[i][j] && arr[i][j] != '.') {
                    List<Tuple> tmp = bfs(i, j, visit);

                    if (tmp.size() >= 4) empty.addAll(tmp);
                }
            }
        }

        makeEmpty(empty);
        down();

        if (empty.size() == 0) return false;
        return true;
    }

    public static void makeEmpty(List<Tuple> empty) {
        for (Tuple t : empty) {
            arr[t.x][t.y] = '.';
        }
    }

    public static void down() {
        for (int j = 0; j < M; j++) {
            int moveCnt = 0;

            for (int i = N-1; i >= 0; i--) {
                if (arr[i][j] == '.') moveCnt++;
                else {
                    if (moveCnt > 0) {
                        arr[i+moveCnt][j] = arr[i][j];
                        arr[i][j] = '.';
                    }
                }
            }
        }
    }

    public static List<Tuple> bfs(int i, int j, boolean[][] visit) {
        List<Tuple> tmp = new ArrayList<>();

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (arr[i][j] != arr[x][y]) continue;

            if (visit[x][y]) continue;
            else visit[x][y] = true;

            tmp.add(new Tuple(x, y));

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));
        }

        return tmp;
    }

    public static class Tuple{
        int x;
        int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
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
        arr = new char[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}