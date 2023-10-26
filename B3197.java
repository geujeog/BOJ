import java.util.*;
import java.io.*;

class B3197 {
    static int R;
    static int C;
    static char[][] arr;
    static int result;
    static Integer[][] meltDays;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        // 얼음 녹기
        melt();

        // 최소 얼음 녹는 날짜 구하기
        link();
    }

    public static void link() {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();
        boolean[][] visit = new boolean[R][C];
        int ex = 0;
        int ey = 0;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (arr[i][j] == 'L') {
                    if (queue.size() == 1) {
                        ex = i;
                        ey = j;
                    }
                    else {
                        queue.add(new Tuple(i, j , 0));
                    }
                }
            }
        }

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int cnt = tuple.cnt;

            if (x < 0 || y < 0 || x >= R || y >= C) continue;

            if (visit[x][y]) continue;
            else visit[x][y] = true;

            if (x == ex && y == ey) {
                result = cnt;
                break;
            }

            queue.add(new Tuple(x-1, y, Math.max(cnt, meltDays[x][y])));
            queue.add(new Tuple(x+1, y, Math.max(cnt, meltDays[x][y])));
            queue.add(new Tuple(x, y-1, Math.max(cnt, meltDays[x][y])));
            queue.add(new Tuple(x, y+1, Math.max(cnt, meltDays[x][y])));
        }
    }

    public static void melt() {
        PriorityQueue<Tuple> queue = new PriorityQueue<>();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (arr[i][j] == '.' || arr[i][j] == 'L') queue.add(new Tuple(i, j, 0));
            }
        }

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int cnt = tuple.cnt;

            if (x < 0 || y < 0 || x >= R || y >= C) continue;

            if (meltDays[x][y] != null) continue;
            else {
                if (arr[x][y] == 'X') meltDays[x][y] = ++cnt;
                else meltDays[x][y] = 0;
            }

            queue.add(new Tuple(x-1, y, meltDays[x][y]));
            queue.add(new Tuple(x+1, y, meltDays[x][y]));
            queue.add(new Tuple(x, y-1, meltDays[x][y]));
            queue.add(new Tuple(x, y+1, meltDays[x][y]));
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;
        int cnt;

        public Tuple(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Tuple t) {
            return this.cnt - t.cnt;
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
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        arr = new char[R][C];
        meltDays = new Integer[R][C];

        for (int i = 0; i < R; i++) {
            String line = br.readLine();

            for (int j = 0; j < C; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        br.close();
    }
}