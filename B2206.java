import java.util.*;
import java.io.*;

class B2206 {
    static int sero;
    static int garo;
    static char[][] arr;
    static boolean[][][] check;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        sero = Integer.parseInt(st.nextToken());
        garo = Integer.parseInt(st.nextToken());

        arr = new char[sero][garo];
        for (int i = 0; i < sero; i++) {
            String line = br.readLine();
            for (int j = 0; j < garo; j++) {
                arr[i][j] = line.charAt(j);
            }
        }

        check = new boolean[2][sero][garo];
        bw.write(bfs()+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int bfs () {
        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(0, 0, 1, 0));

        int count = -1;
        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int depth = tuple.depth;
            int broken = tuple.broken;

            if (x == sero-1 && y == garo-1) {
                count = depth;
                break;
            }
            if (x < 0 || y < 0 || x == sero || y == garo) continue;
            if (check[broken][x][y]) continue;
            if (broken == 1 && arr[x][y] == '1') continue;

            check[broken][x][y] = true;

            if (arr[x][y] == '1') {
                queue.add(new Tuple(x-1, y, depth+1, 1));
                queue.add(new Tuple(x+1, y, depth+1, 1));
                queue.add(new Tuple(x, y-1, depth+1, 1));
                queue.add(new Tuple(x, y+1, depth+1, 1));
            }
            else {
                queue.add(new Tuple(x - 1, y, depth + 1, broken));
                queue.add(new Tuple(x + 1, y, depth + 1, broken));
                queue.add(new Tuple(x, y - 1, depth + 1, broken));
                queue.add(new Tuple(x, y + 1, depth + 1, broken));
            }
        }
        return count;
    }

    public static class Tuple {
        int x;
        int y;
        int depth;
        int broken;

        public Tuple (int x, int y, int depth, int broken) {
            this.x = x;
            this.y = y;
            this.depth = depth;
            this.broken = broken;
        }
    }
}