import java.util.*;
import java.io.*;

class B9328 {
    static int N;
    static int M;
    static char[][] arr;
    static List<Character> key;
    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            arr = new char[N][M];
            key = new ArrayList<>();
            result = 0;

            for (int i = 0; i < N; i++) {
                String line = br.readLine();

                for (int j = 0; j < M; j++) {
                    arr[i][j] = line.charAt(j);
                }
            }

            String keys = br.readLine();
            for (int i = 0; i < keys.length(); i++) {
                key.add(Character.toUpperCase(keys.charAt(i)));
            }

            solution();

            bw.write(result+"\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void solution() {
        // 바깥에서 들어갈 수 있는 위치 가져오기
        List<Tuple> inOut = getInOutSite();

        // 문서 훔치기
        while (true) {
            int keyCnt = key.size();

            for (Tuple start : inOut) {
                steal(start.x, start.y);
            }

            // 키 개수 변화 없으면 빠져나옴
            if (keyCnt == key.size()) break;
        }

    }

    public static void steal(int i, int j) {
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j));

        boolean[][] visit = new boolean[N][M];

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;

            if (visit[x][y]) continue;
            else visit[x][y] = true;

            if (arr[x][y] == '*') continue;
            else if (arr[x][y] == '$') {
                result++;
                arr[x][y] = '.';
            }
            else if (Character.isLowerCase(arr[x][y])) {
                if (!key.contains(Character.toUpperCase(arr[x][y]))) key.add(Character.toUpperCase(arr[x][y]));
                arr[x][y] = '.';
            }
            else if (Character.isUpperCase(arr[x][y])) {
                if (!key.contains(arr[x][y])) continue;
                else {
                    arr[x][y] = '.';
                }
            }

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));
        }
    }

    public static List<Tuple> getInOutSite() {
        List<Tuple> start = new ArrayList<>();
        boolean[][] visit = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if ((i == 0 || j == 0 || i == N-1 || j == M-1) && arr[i][j] != '*' && !visit[i][j]) {
                    moveInner(i, j, visit);
                    start.add(new Tuple(i, j));
                }
            }
        }

        return start;
    }

    public static void moveInner(int i, int j, boolean[][] visit) {
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(i, j));

        while (!queue.isEmpty()) {
            Tuple t = queue.poll();
            int x = t.x;
            int y = t.y;

            if (x < 0 || y < 0 || x >= N || y >= M) continue;

            if (arr[x][y] == '*') continue;
            else if (Character.isUpperCase(arr[x][y]) && !key.contains(arr[x][y])) continue;

            if (visit[x][y]) continue;
            else visit[x][y] = true;

            queue.add(new Tuple(x-1, y));
            queue.add(new Tuple(x+1, y));
            queue.add(new Tuple(x, y-1));
            queue.add(new Tuple(x, y+1));
        }
    }

    public static class Tuple {
        int x;
        int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}