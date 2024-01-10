import java.util.*;
import java.io.*;

class B2933 {
    static int R, C;
    static char[][] arr;
    static int[] attacks;
    static int[] dAttack = {1, -1};
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        for (int i = 0; i < attacks.length; i++) {
            if (i % 2 == 0) attack(attacks[i], 1, 0);
            else attack(attacks[i], C, 1);
        }
    }

    public static void attack(int x, int y, int d) {
        while(arr[x][y] != 'x') {
            y += dAttack[d];

            if (y == 0 || y > C) return;
        }

        // 미네랄 파괴
        arr[x][y] = '.';

        List<List<Tuple>> list = new ArrayList<>();

        // 클러스터 확인
        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx == 0 || ny == 0 || nx > R || ny > C) continue;
            if (arr[nx][ny] == '.') continue;

            list.add(findCluster(nx, ny));
        }

        for (List<Tuple> cluster : list) {
            if(moveDown(cluster)) break;
        }
    }

    public static boolean moveDown(List<Tuple> clustering) {
        boolean move = false;
        while (canMoveDown(clustering)) {
            List<Tuple> tmp = new ArrayList<>();

            for (Tuple cluster : clustering) {
                tmp.add(new Tuple(cluster.x+1, cluster.y));
                arr[cluster.x+1][cluster.y] = arr[cluster.x][cluster.y];
                arr[cluster.x][cluster.y] = '.';
            }

            clustering = tmp;
            move = true;
        }
        return move;
    }

    public static boolean canMoveDown(List<Tuple> clustering) {
        // y를 기준으로 x 내림차순 정렬
        Collections.sort(clustering, new Comparator<Tuple>() {
            @Override
            public int compare(Tuple o1, Tuple o2) {
                if (o1.y == o2.y) return o2.x - o1.x;
                return o2.y - o1.y;
            }
        });

        int beforeY = -1;
        // 아래가 바닥이면 움직이지 못함
        for (Tuple cluster : clustering) {
            if (beforeY == cluster.y) continue;

            // 최초의 x행
            beforeY = cluster.y;

            if (cluster.x == R || arr[cluster.x + 1][cluster.y] == 'x') return false;
        }

        return true;
    }

    public static List<Tuple> findCluster(int i, int j) {
        List<Tuple> result = new ArrayList<>();
        Queue<Tuple> queue = new ArrayDeque<>();
        boolean[][] visit = new boolean[R+1][C+1];

        queue.add(new Tuple(i, j));
        visit[i][j] = true;

        while (!queue.isEmpty()) {
            Tuple q = queue.poll();
            int x = q.x;
            int y = q.y;

            result.add(new Tuple(x, y));

            for (int d = 0; d < dx.length; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx == 0 || ny == 0 || nx > R || ny > C) continue;
                if (arr[nx][ny] == '.' || visit[nx][ny]) continue;

                visit[nx][ny] = true;
                queue.add(new Tuple(nx, ny));
            }
        }

        return result;
    }

    public static class Tuple implements Comparable<Tuple> {
        int x;
        int y;

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Tuple t) {
            return t.x - this.x;
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                bw.write(arr[i][j]);
            }
            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        arr = new char[R+1][C+1];

        for (int i = 1; i <= R; i++) {
            String line = br.readLine();
            for (int j = 1; j <= C; j++) {
                arr[i][j] = line.charAt(j - 1);
            }
        }

        int N = Integer.parseInt(br.readLine());
        attacks = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            attacks[i] = R - Integer.parseInt(st.nextToken()) + 1;
        }

        br.close();
    }
}