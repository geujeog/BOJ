import java.util.*;
import java.io.*;

public class B2234 {
    static int M; // 가로
    static int N; // 세로
    static boolean[][][] arr;
    static int[][] map;
    static int[] mapRoom;

    static int roomCnt;
    static int roomSize;
    static int roomSizeBroken;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        // 0 - 남, 1 - 동, 2 - 북, 3 - 서
        // true면 벽 있음, false면 벽 없음
        arr = new boolean[N][M][4];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                init(i, j, Integer.parseInt(st.nextToken()));
            }
        }

        checkMap();
        checkRoomSize();
        brokenWall();

        bw.write(roomCnt+"\n");
        bw.write(roomSize+"\n");
        bw.write(roomSizeBroken+"\n");

        br.close();
        bw.flush();
        bw.close();
    }

    public static void brokenWall () {
        int[] check = new int[N*M + 1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (check[map[i][j]] == 0) {
                    check[map[i][j]] = brokenWallBFS(i, j);

                    roomSizeBroken = Math.max(roomSizeBroken, check[map[i][j]]);
                }
            }
        }

    }

    public static int brokenWallBFS (int i, int j) {
        int result = 0;

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j, 0));

        boolean[][] check = new boolean[N][M];

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (check[x][y]) continue;
            if (map[i][j] != map[x][y]) {
                result = Math.max(result, (mapRoom[map[i][j]] + mapRoom[map[x][y]]));
                continue;
            }

            check[x][y] = true;

            queue.add(new Tuple(x+1, y, 0));
            queue.add(new Tuple(x, y+1, 0));
            queue.add(new Tuple(x-1, y, 0));
            queue.add(new Tuple(x, y-1, 0));
        }

        return result;
    }

    public static void checkRoomSize () {
        mapRoom = new int[N*M + 1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                roomSize = Math.max(roomSize, ++mapRoom[map[i][j]]);
            }
        }
    }

    public static void checkMap () {
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {
                    checkMapBFS(i, j, ++roomCnt);
                }
            }
        }
    }

    public static void checkMapBFS (int i, int j, int roomNumber) {
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j, 1));

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int cnt = tuple.cnt;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;
            if (map[x][y] != 0) continue;

            map[x][y] = roomNumber;

            if (!arr[x][y][0]) queue.add(new Tuple(x+1, y, cnt+1));
            if (!arr[x][y][1]) queue.add(new Tuple(x, y+1, cnt+1));
            if (!arr[x][y][2]) queue.add(new Tuple(x-1, y, cnt+1));
            if (!arr[x][y][3]) queue.add(new Tuple(x, y-1, cnt+1));
        }
    }

    public static void init (int i, int j, int k) {
        int[] wall = {8, 4, 2, 1};

        for (int w = 0; w < wall.length; w++) {
            if (k >= wall[w]) {
                k -= wall[w];
                arr[i][j][w] = true;
            }
        }
    }

    public static class Tuple {
        int x;
        int y;
        int cnt;

        public Tuple (int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
    }
}