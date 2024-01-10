import java.util.*;
import java.io.*;

class B21609 {
    static int N, M;
    static int[][] arr;
    static int[] maxGroup;
    static Queue<int[]> group;
    static int result;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        while(true) {
            if (!checkBlockGroup()) break;

            deleteBlockGroup();
            moveDown();
            turn();
            moveDown();
        }
    }

    public static void turn() {
        int[][] tmp = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[i][j] = arr[j][N - i - 1];
            }
        }

        arr = tmp;
    }

    public static void moveDown() {
        for (int j = 0; j < N; j++) {
            int moveCnt = 0;

            for (int i = N-1; i >= 0; i--) {
                if (arr[i][j] == -2) {
                    moveCnt++;
                }
                else if (arr[i][j] == -1) {
                    moveCnt = 0;
                }
                else {
                    if (moveCnt != 0) {
                        arr[i+moveCnt][j] = arr[i][j];
                        arr[i][j] = -2;
                    }
                }
            }
        }
    }

    public static void deleteBlockGroup() {
        result += Math.pow(maxGroup[0], 2);

        while (!group.isEmpty()) {
            int[] q = group.poll();
            arr[q[0]][q[1]] = -2;
        }
    }

    public static boolean checkBlockGroup() {
        boolean[][] visit = new boolean[N][N];
        maxGroup = new int[4]; // 그룹개수, 무지개카운팅, 기준x, 기준y
        group = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] > 0 && !visit[i][j]) {
                    getBlockGroup(i, j, visit);
                }
            }
        }

        if (group.size() == 0) return false;

        return true;
    }

    public static void getBlockGroup(int i, int j, boolean[][] visit) {
        int groupCnt = 0;
        int rainbowCnt = 0;
        int standardX = i;
        int standardY = j;
        Queue<int[]> queue = new ArrayDeque<>();
        Queue<int[]> tmp = new ArrayDeque<>();
        Queue<int[]> rainbow = new ArrayDeque<>();

        queue.add(new int[]{i, j});
        visit[i][j] = true;

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int x = q[0];
            int y = q[1];

            groupCnt++;
            tmp.add(q);

            if (arr[x][y] == 0) {
                rainbow.add(q);
            }
            else {
                if ((x < standardX) || (x == standardX && y < standardY)) {
                    standardX = x;
                    standardY = y;
                }
            }

            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (arr[nx][ny] <= -1 || visit[nx][ny]) continue;

                if (arr[nx][ny] != arr[i][j]) {
                    if (arr[nx][ny] == 0) rainbowCnt++;
                    else continue;
                }

                visit[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }

        if (groupCnt == 1) return;

        while (!rainbow.isEmpty()) {
            int[] q = rainbow.poll();
            visit[q[0]][q[1]] = false;
        }

        if (maxGroup[0] < groupCnt) {
            initMaxGroup(groupCnt, rainbowCnt, standardX, standardY, tmp);
        }
        else if (maxGroup[0] == groupCnt) {
            if (maxGroup[1] < rainbowCnt) {
                initMaxGroup(groupCnt, rainbowCnt, standardX, standardY, tmp);
            }
            else if (maxGroup[1] == rainbowCnt) {
                if (maxGroup[2] < standardX) {
                    initMaxGroup(groupCnt, rainbowCnt, standardX, standardY, tmp);
                }
                else if (maxGroup[2] == standardX) {
                    if (maxGroup[3] < standardY) {
                        initMaxGroup(groupCnt, rainbowCnt, standardX, standardY, tmp);
                    }
                }
            }
        }
    }

    public static void initMaxGroup(int groupCnt, int rainbowCnt, int x, int y, Queue<int[]> list) {
        maxGroup[0] = groupCnt;
        maxGroup[1] = rainbowCnt;
        maxGroup[2] = x;
        maxGroup[3] = y;
        group = list;
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
        arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}