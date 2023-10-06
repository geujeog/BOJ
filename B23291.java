import java.util.*;
import java.io.*;

public class B23291 {
    static int N;
    static int K;
    static int[] arr;
    static int result;

    public static void main(String[] args) throws Exception {
        init();
        solution();
        print();
    }

    public static void solution() {
        while(isLoop()) {
            inputFish();
            stackFish();
            stackHalfFish();

            result++;
        }
    }

    public static void stackHalfFish() {
        int[][] entire = new int[N][N];
        int squareX = 1;
        int squareY = N;

        for (int i = 0; i < N; i++) {
            entire[0][i] = arr[i];
        }

        for (int k = 0; k < 2; k++) {
            int[][] square = new int[N][N];

            // 절반 square 가져오기
            square = getHalfFish(squareX, squareY, entire);

            squareY /= 2;

            // 90도 돌리고 쌓아 올리기
            square = turnSquare(squareX, squareY, square);
            square = turnSquare(squareY, squareX, square);

            entire = stackHalfSquare(squareX, squareY, square, entire);

            squareX += squareX;
        }

        entire = spread(entire);

        arr = makeOneArr(entire);
    }

    public static int[][] stackHalfSquare(int x, int y, int[][] square, int[][] entire) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                square[x + i][j] = entire[i][y + j];
            }
        }

        return square;
    }

    public static int[][] getHalfFish(int squareX, int squareY, int[][] square) {
        int[][] tmp = new int[N][N];

        for (int i = 0; i < squareX; i++) {
            for (int j = 0; j < squareY / 2; j++) {
                tmp[i][j] = square[i][j];
            }
        }

        return tmp;
    }

    public static int[][] makeHalfSquare(int squareX, int squareY, int[][] square) {
        int[][] tmp = new int[N][N];

        for (int i = 0; i < squareX; i++) {
            for (int j = 0; j < squareY / 2; j++) {
                tmp[i][j] = square[i][j];
            }
        }

        return tmp;
    }

    public static void stackFish() {
        int[][] square = new int[N][N];
        int squareX = 1;
        int squareY = 1;

        // 하나 쌓아올리기
        squareX++;
        square = stackOneFish(square);

        // 90도 돌리고 쌓아올리기
        while (squareX <= N - (squareX * squareY)) {
            square = turnSquare(squareX, squareY, square);
            square = stackSquare(squareX, squareY, square);

            int tmp = squareX;
            squareX = squareY + 1;
            squareY = tmp;
        }

        // 나머지 추가
        square = addRemain(squareX, squareY, square);

        // 물고기 분산
        square = spread(square);

        // 일렬로 만들고 arr 재정의
        arr = makeOneArr(square);
    }

    public static int[] makeOneArr(int[][] square) {
        int[] tmp = new int[N];
        int cnt = 0;

        for (int j = 0; j < N; j++) {
            for (int i = N - 1; i >= 0; i--) {
                if (square[i][j] == 0) continue;

                tmp[cnt++] = square[i][j];
            }
        }

        return tmp;
    }

    public static int[][] addRemain(int squareX, int squareY, int[][] square) {
        for (int i = 0; i < N-(squareX * squareY); i++) {
            square[squareX - 1][squareY + i] = arr[squareX * squareY + i];
        }

        return square;
    }

    public static int[][] stackOneFish(int[][] square) {
        square[0][0] = arr[0];
        square[1][0] = arr[1];

        return square;
    }

    public static int[][] spread(int[][] square) {
        int[][] cnt = new int[N][N];
        int[] dx = {1, 0};
        int[] dy = {0, 1};

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (square[i][j] == 0) continue;

                for (int d = 0; d < 2; d++) {
                    int xx = i + dx[d];
                    int yy = j + dy[d];

                    if (xx < 0 || yy < 0 || xx >= N || yy >= N) continue;
                    if (square[xx][yy] == 0) continue;

                    int remain = Math.abs(square[i][j] - square[xx][yy]) / 5;

                    if (remain > 0) {
                        if (square[i][j] > square[xx][yy]) {
                            cnt[i][j] -= remain;
                            cnt[xx][yy] += remain;
                        }
                        else {
                            cnt[xx][yy] -= remain;
                            cnt[i][j] += remain;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                square[i][j] += cnt[i][j];
            }
        }

        return square;
    }

    public static int[][] turnSquare(int x, int y, int[][] square) {
        int[][] tmp = new int[N][N];

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                tmp[i][j] = square[x - j - 1][i];
            }
        }

        return tmp;
    }

    public static int[][] stackSquare(int x, int y, int[][] square) {
        for (int i = 0; i < x; i++) {
            square[y][i] = arr[x * y + i];
        }

        return square;
    }

    public static void inputFish() {
        List<Integer> min = new ArrayList<>();
        int minCnt = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            if (arr[i] <= minCnt) {
                if (arr[i] < minCnt) min = new ArrayList<>();

                minCnt = arr[i];
                min.add(i);
            }
        }

        for (int i = 0; i < min.size(); i++) {
            arr[min.get(i)]++;
        }
    }

    public static boolean isLoop() {
        int min = Integer.MAX_VALUE;
        int max = 0;

        for (int i = 0; i < N; i++) {
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }

        if (max - min <= K) return false;
        return true;
    }

    public static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }

}
