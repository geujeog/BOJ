import java.util.*;
import java.io.*;

class B23290 {
    static int N = 4;
    static int S;
    static List<Integer>[][] arr;
    static int[][] smell;
    static int sharkX;
    static int sharkY;
    static int result;

    static int sharkMoveSum;
    static int sharkMoveDirection;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        // 연습 중
        for (int i = 0; i < S; i++) {
            // 1번
            List<Integer>[][] copy = copyMagic();

            // 2번
            arr = moveFish();

            // 3번
            moveShark();

            // 4번
            removeSmell();

            showCopy(copy);
        }

        // 연습을 마친 후 물고기 수 구하기
        result = getFishCnt();
    }

    public static void showCopy(List<Integer>[][] copy) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j].addAll(copy[i][j]);
            }
        }
    }

    public static void removeSmell() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                smell[i][j] = Math.max(0, --smell[i][j]);
            }
        }
    }

    public static void moveShark() {
        int[][] cntArr = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cntArr[i][j] = arr[i][j].size();
            }
        }

        int[] dx = {0, -1, 0, 1, 0};
        int[] dy = {0, 0, -1, 0, 1};

        sharkMoveSum = 0;
        sharkMoveDirection = 0;

        for (int i = 1; i <= 4; i++) {
            dfs(sharkX+dx[i], sharkY+dy[i], cntArr, 0, i);
        }

        for (int i = 0; i < 3; i++) {
            int dir = String.valueOf(sharkMoveDirection).charAt(i) - '0';

            sharkX += dx[dir];
            sharkY += dy[dir];

            if (arr[sharkX][sharkY].size() > 0) {
                smell[sharkX][sharkY] = 3;
                arr[sharkX][sharkY].clear();
            }
        }
    }

    public static void dfs(int x, int y, int[][] cntArr, int sum, int cnt) {
        if (x < 0 || y < 0 || x >= N || y >= N) return;

        sum += cntArr[x][y];

        if (String.valueOf(cnt).length() == 3) {
            compareDirection(sum, cnt);
            return;
        }

        cntArr[x][y] = 0;

        int[] dx = {0, -1, 0, 1, 0};
        int[] dy = {0, 0, -1, 0, 1};

        for (int i = 1; i <= 4; i++) {
            dfs(x+dx[i], y+dy[i], cntArr, sum, cnt*10 + i);
        }

        cntArr[x][y] = arr[x][y].size();
    }

    public static void compareDirection(int sum, int cnt) {
        if (sharkMoveSum == sum) {
            if (sharkMoveDirection == 0) {
                sharkMoveDirection = cnt;
            }
            else {
                for (int i = 0; i < 3; i++) {
                    int sChr = String.valueOf(sharkMoveDirection).charAt(i) - '0';
                    int cChr = String.valueOf(cnt).charAt(i) - '0';

                    if (sChr == cChr) continue;
                    else {
                        if (sChr > cChr) {
                            sharkMoveDirection = cnt;
                        }
                        break;
                    }
                }
            }
        }
        else if (sharkMoveSum < sum) {
            sharkMoveSum = sum;
            sharkMoveDirection = cnt;
        }
    }

    public static List<Integer>[][] moveFish() {
        List<Integer>[][] tmp = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int fish = 0; fish < arr[i][j].size(); fish++) {
                    int direction = arr[i][j].get(fish);

                    int[] next = moveOneFish(i, j, direction);
                    tmp[next[0]][next[1]].add(next[2]);
                }
            }
        }

        return tmp;
    }

    public static int[] moveOneFish(int x, int y, int originDirection) {
        int direction = originDirection;

        boolean canGo = true;
        while(!canGoNext(x, y, direction)) {
            direction--;

            if (direction <= 0) direction = 8;

            if (originDirection == direction) {
                canGo = false;
                break;
            }
        }

        if (canGo) {
            int[] next = getNext(x, y, direction);
            return new int[]{next[0], next[1], direction};
        }

        return new int[]{x, y, originDirection};
    }

    public static boolean canGoNext(int x, int y, int direction) {
        int[] next = getNext(x, y, direction);

        // 격자벗어남
        if (next[0] < 0 || next[1] < 0 || next[0] >= N || next[1] >= N) return false;

        // 물고기 냄새
        if (smell[next[0]][next[1]] > 0) return false;

        // 상어
        if (next[0] == sharkX && next[1] == sharkY) return false;

        return true;
    }

    public static int[] getNext(int x, int y, int direction) {
        if (direction == 1) return new int[]{x, y-1};
        else if (direction == 2) return new int[]{x-1, y-1};
        else if (direction == 3) return new int[]{x-1, y};
        else if (direction == 4) return new int[]{x-1, y+1};
        else if (direction == 5) return new int[]{x, y+1};
        else if (direction == 6) return new int[]{x+1, y+1};
        else if (direction == 7) return new int[]{x+1, y};
        else return new int[]{x+1, y-1};
    }

    public static List<Integer>[][] copyMagic() {
        List<Integer>[][] tmp = new ArrayList[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[i][j] = new ArrayList<>();
                tmp[i][j].addAll(arr[i][j]);
            }
        }

        return tmp;
    }

    public static int getFishCnt() {
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cnt += arr[i][j].size();
            }
        }

        return cnt;
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        arr = new ArrayList[N][N];
        smell = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = new ArrayList<>();
            }
        }

        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());

            arr[a][b].add(dir);
        }

        st = new StringTokenizer(br.readLine());
        sharkX = Integer.parseInt(st.nextToken()) - 1;
        sharkY = Integer.parseInt(st.nextToken()) - 1;

        br.close();
    }
}