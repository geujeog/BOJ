import java.util.*;
import java.io.*;

class B12100 {
    static int N;
    static int[][] arr;
    static int result;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        dfs(arr, 0);
    }

    public static void dfs(int[][] map, int cnt) {
        if (cnt == 5) {
            result = Math.max(result, getMax(map));
            return;
        }

        dfs(moveUp(copy(map)), cnt+1);
        dfs(moveDown(copy(map)), cnt+1);
        dfs(moveRight(copy(map)), cnt+1);
        dfs(moveLeft(copy(map)), cnt+1);
    }

    public static int[][] moveLeft(int[][] map) {
        boolean[][] isCombination = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            int moveCnt = 0;

            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) moveCnt++;
                else {
                    if (moveCnt > 0) {
                        // 일단 이동
                        map[i][j-moveCnt] = map[i][j];
                        map[i][j] = 0;
                    }

                    // 합칠 수 있는지 확인 후, 합치기
                    if (j-moveCnt > 0 && map[i][j-moveCnt] == map[i][j-moveCnt-1] && !isCombination[i][j-moveCnt-1]) {
                        isCombination[i][j-moveCnt-1] = true;
                        map[i][j-moveCnt-1] *= 2;
                        map[i][j-moveCnt] = 0;
                        moveCnt++;
                    }
                }
            }
        }

        return map;
    }

    public static int[][] moveRight(int[][] map) {
        boolean[][] isCombination = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            int moveCnt = 0;

            for (int j = N-1; j >= 0; j--) {
                if (map[i][j] == 0) moveCnt++;
                else {
                    if (moveCnt > 0) {
                        // 일단 이동
                        map[i][j+moveCnt] = map[i][j];
                        map[i][j] = 0;
                    }

                    // 합칠 수 있는지 확인 후, 합치기
                    if (j+moveCnt < N-1 && map[i][j+moveCnt] == map[i][j+moveCnt+1] && !isCombination[i][j+moveCnt+1]) {
                        isCombination[i][j+moveCnt+1] = true;
                        map[i][j+moveCnt+1] *= 2;
                        map[i][j+moveCnt] = 0;
                        moveCnt++;
                    }
                }
            }
        }

        return map;
    }

    public static int[][] moveDown(int[][] map) {
        boolean[][] isCombination = new boolean[N][N];

        for (int j = 0; j < N; j++) {
            int moveCnt = 0;

            for (int i = N-1; i >= 0; i--) {
                if (map[i][j] == 0) moveCnt++;
                else {
                    if (moveCnt > 0) {
                        // 일단 이동
                        map[i + moveCnt][j] = map[i][j];
                        map[i][j] = 0;
                    }

                    // 합칠 수 있는지 확인 후, 합치기
                    if (i+moveCnt < N-1 && map[i+moveCnt][j] == map[i+moveCnt+1][j] && !isCombination[i+moveCnt+1][j]) {
                        isCombination[i+moveCnt+1][j] = true;
                        map[i+moveCnt+1][j] *= 2;
                        map[i+moveCnt][j] = 0;
                        moveCnt++;
                    }
                }
            }
        }

        return map;
    }

    public static int[][] moveUp(int[][] map) {
        boolean[][] isCombination = new boolean[N][N];

        for (int j = 0; j < N; j++) {
            int moveCnt = 0;

            for (int i = 0; i < N; i++) {
                if (map[i][j] == 0) moveCnt++;
                else {
                    if (moveCnt > 0) {
                        // 일단 이동
                        map[i-moveCnt][j] = map[i][j];
                        map[i][j] = 0;
                    }

                    // 합칠 수 있는지 확인 후, 합치기
                    if (i-moveCnt > 0 && map[i-moveCnt][j] == map[i-moveCnt-1][j] && !isCombination[i-moveCnt-1][j]) {
                        isCombination[i-moveCnt-1][j] = true;
                        map[i-moveCnt-1][j] *= 2;
                        map[i-moveCnt][j] = 0;
                        moveCnt++;
                    }
                }
            }
        }

        return map;
    }

    public static int[][] copy(int[][] map) {
        int[][] copyMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copyMap[i][j] = map[i][j];
            }
        }
        return copyMap;
    }

    public static int getMax(int[][] map) {
        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                max = Math.max(max, map[i][j]);
            }
        }
        return max;
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j  = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }
}