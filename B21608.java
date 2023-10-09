import java.util.*;
import java.io.*;

public class B21608 {
    static int N;
    static int[][] arr;
    static int[][] site;
    static int[][] order;
    static int result;
    static List<Tuple> seat;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        for (int i = 0; i < N*N; i++) {
            int num = order[i][0];

            Tuple s = choiceSite(i);

            arr[s.x][s.y] = num;
            site[num][0] = s.x;
            site[num][1] = s.y;
        }

        for (int i = 0; i < N*N; i++) {
            int cnt = 0;

            int x = site[order[i][0]][0];
            int y = site[order[i][0]][1];

            for (int j = 1; j <= 4; j++) {
                int like = order[i][j];

                int likeX = site[like][0];
                int likeY = site[like][1];

                if (Math.abs(x - likeX) + Math.abs(y - likeY) == 1) cnt++;
            }

            if (cnt == 0) continue;
            else {
                result += Math.pow(10, cnt-1);
            }
        }
    }

    public static Tuple choiceSite(int idx) {
        seat = new ArrayList<>();

        // 1번
        choiceImportantOne(idx);

        if (seat.size() == 1) return seat.get(0);

        // 2번
        choiceImportantTwo();

        if (seat.size() == 1) return seat.get(0);

        return choiceImportantThree();
    }

    public static Tuple choiceImportantThree() {
        Collections.sort(seat, new Comparator<Tuple>() {
            @Override
            public int compare(Tuple a, Tuple b) {
                if (a.x == b.x) return a.y - b.y;
                return a.x - b.x;
            }
        });

        return seat.get(0);
    }

    public static void choiceImportantTwo() {
        List<Tuple> list = new ArrayList<>();

        int max = 0;

        for (Tuple s : seat) {
            int x = s.x;
            int y = s.y;

            int tmp = 0;

            if (x-1 >= 0 && arr[x-1][y] == 0) tmp++;
            if (x+1 < N && arr[x+1][y] == 0) tmp++;
            if (y-1 >= 0 && arr[x][y-1] == 0) tmp++;
            if (y+1 < N && arr[x][y+1] == 0) tmp++;

            if (max <= tmp) {
                if (max < tmp) list = new ArrayList<>();

                max = Math.max(max, tmp);
                list.add(new Tuple(x, y));
            }
        }

        seat = list;

    }

    public static void choiceImportantOne(int idx) {
        int[][] prefer = new int[N][N];

        for (int j = 1; j <= 4; j++) {
            findSite(prefer, order[idx][j]);
        }

        int max = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == 0 && max <= prefer[i][j]) {
                    if (max < prefer[i][j]) seat = new ArrayList<>();

                    max = Math.max(max, prefer[i][j]);
                    seat.add(new Tuple(i, j));
                }
            }
        }
    }

    public static class Tuple{
        int x;
        int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void findSite(int[][] prefer, int like) {
        int x = site[like][0];
        int y = site[like][1];

        if (x == -1 || y == -1) return;

        if (x-1 >= 0 && arr[x-1][y] == 0) prefer[x-1][y]++;
        if (x+1 < N && arr[x+1][y] == 0) prefer[x+1][y]++;
        if (y-1 >= 0 && arr[x][y-1] == 0) prefer[x][y-1]++;
        if (y+1 < N && arr[x][y+1] == 0) prefer[x][y+1]++;
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.close();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        site = new int[N*N+1][2];
        order = new int[N*N][5];

        for (int i = 0; i < N*N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            order[i][0] = Integer.parseInt(st.nextToken());
            order[i][1] = Integer.parseInt(st.nextToken());
            order[i][2] = Integer.parseInt(st.nextToken());
            order[i][3] = Integer.parseInt(st.nextToken());
            order[i][4] = Integer.parseInt(st.nextToken());

            Arrays.fill(site[i], -1);
        }
        Arrays.fill(site[N*N], -1);

        br.close();
    }
}
