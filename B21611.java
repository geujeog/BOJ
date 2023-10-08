import java.util.*;
import java.io.*;

public class B21611 {
    static int N;
    static int M;
    static int[][] arr;
    static int[][] magic; // 방향(1:상, 2:하, 3:좌, 4:우), 거리
    static int result;
    static int[][] list;
    static int one, two, three;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    public static void solution() {
        for (int m = 0; m < M; m++) {
            int d = magic[m][0];
            int s = magic[m][1];

            sharkAttack(d, s);
            while (bumb()) {
            }
            moveEmpty();
            change();
        }

        result = 1*one + 2*two + 3*three;
    }

    public static void change() {
        int num = 0; // 구슬 개수
        int cnt = 0; // 구술 번호

        List<Integer> group = new ArrayList<>();

        for (int i = 0; i < list.length; i++) {
            int[] t = list[i];

            if (arr[t[0]][t[1]] == 0) break;

            if (num == arr[t[0]][t[1]]) {
                cnt++;
            }
            else {
                if (num != 0) {
                    group.add(cnt);
                    group.add(num);
                }

                num = arr[t[0]][t[1]];
                cnt = 1;
            }
        }

        group.add(cnt);
        group.add(num);

        for (int i = 0; i < list.length; i++) {
            int[] t = list[i];

            if (group.size() <= i) {
                arr[t[0]][t[1]] = 0;
            }
            else arr[t[0]][t[1]] = group.get(i);
        }
    }

    public static boolean bumb() {
        int num = 0;
        int cnt = 0;
        int emptyCnt = 0;

        List<Integer> removeIdx = new ArrayList<>();
        int lastIdx = 0;

        for (int i = 0; i < list.length; i++) {
            int[] t = list[i];

            if (arr[t[0]][t[1]] == -1) {
                emptyCnt++;
                continue;
            }
            else if (arr[t[0]][t[1]] == 0) {
                lastIdx = i;
                break;
            }

            if (num == arr[t[0]][t[1]]) {
                cnt++;
            }
            else {
                if (cnt >= 4) {
                    for (int c = 1; c <= cnt + emptyCnt; c++) {
                        removeIdx.add(i - c);
                    }
                }
                emptyCnt = 0;
                num = arr[t[0]][t[1]];
                cnt = 1;
            }
        }

        if (cnt >= 4) {
            for (int c = 1; c <= cnt + emptyCnt; c++) {
                int tmp = lastIdx - c;

                if (tmp < 0 || tmp >= N*N) continue;
                else removeIdx.add(lastIdx - c);
            }
        }

        if (removeIdx.size() == 0) return false;


        for (Integer idx : removeIdx) {
            int[] t = list[idx];
            int tmp = arr[t[0]][t[1]];

            if (tmp == 1) one++;
            else if (tmp == 2) two++;
            else if (tmp == 3) three++;

            arr[t[0]][t[1]] = -1;
        }

        return true;
    }

    public static void printArr() {
        System.out.println();
        System.out.println("==================");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf(" %3d ", arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void moveEmpty() {
        int cnt = 0;

        for (int i = 0; i < list.length; i++) {
            int[] t = list[i];
            int x = t[0];
            int y = t[1];

            if (arr[x][y] == -1) {
                cnt++;
            }
            else {
                if (cnt > 0) {
                    int[] before = list[i - cnt];

                    arr[before[0]][before[1]] = arr[x][y];
                    arr[x][y] = 0;
                }
            }
        }
    }

    public static void sharkAttack(int d, int s) {
        int sharkX = N / 2;
        int sharkY = N / 2;

        for (int i = 0; i < s; i++) {
            if (d == 1) sharkX--;
            else if (d == 2) sharkX++;
            else if (d == 3) sharkY--;
            else sharkY++;

            arr[sharkX][sharkY] = -1;
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

    public static void print() throws IOException {
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
        magic = new int[M][2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            magic[i][0] = Integer.parseInt(st.nextToken());
            magic[i][1] = Integer.parseInt(st.nextToken());
        }

        list = new int[N*N][2];

        int x = N/2;
        int y = N/2;
        int cnt = 0;
        boolean flag = false;
        for (int i = 1; i <= N; i++) {
            // y
            int num = (i % 2 == 0) ? 1 : -1;
            for (int j = 1; j <= i; j++) {
                if (i == N && j == N) {
                    flag = true;
                    break;
                }

                y += num;

                list[cnt][0] = x;
                list[cnt++][1] = y;
            }

            if (flag) break;

            // x
            num = (i % 2 == 0) ? -1 : 1;
            for (int j = 1; j <= i; j++) {
                x += num;

                list[cnt][0] = x;
                list[cnt++][1] = y;
            }
        }
        br.close();
    }
}