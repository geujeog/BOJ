import java.util.*;
import java.io.*;

public class B13460 {

    static int N;
    static int M;
    static char[][] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new char[N][M];
        int redX = 0;
        int redY = 0;
        int blueX = 0;
        int blueY = 0;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                arr[i][j] = line.charAt(j);

                if (arr[i][j] == 'R') {
                    redX = i;
                    redY = j;
                    arr[i][j] = '.';
                }
                else if (arr[i][j] == 'B') {
                    blueX = i;
                    blueY = j;
                    arr[i][j] = '.';
                }
            }
        }

        bw.write(bfs(redX, redY, blueX, blueY)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int bfs (int redX, int redY, int blueX, int blueY) {
        int result = -1;

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(redX, redY, blueX, blueY, 0, 's'));

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int rx = tuple.rx;
            int ry = tuple.ry;
            int bx = tuple.bx;
            int by = tuple.by;
            int cnt = tuple.cnt;
            char direction = tuple.direction;

            if (rx < 0 || ry < 0 || rx >= N || ry >= M) continue;
            if (bx < 0 || by < 0 || bx >= N || by >= M) continue;
            if (cnt > 10) continue;

            Tuple movedTuple = move(rx, ry, bx, by, direction);

            if (arr[movedTuple.bx][movedTuple.by] == 'O') continue;
            if (arr[movedTuple.rx][movedTuple.ry] == 'O') {
                result = cnt;
                break;
            }

            queue.add(new Tuple(movedTuple.rx, movedTuple.ry, movedTuple.bx, movedTuple.by, cnt+1, 'l'));
            queue.add(new Tuple(movedTuple.rx, movedTuple.ry, movedTuple.bx, movedTuple.by, cnt+1, 'r'));
            queue.add(new Tuple(movedTuple.rx, movedTuple.ry, movedTuple.bx, movedTuple.by, cnt+1, 'u'));
            queue.add(new Tuple(movedTuple.rx, movedTuple.ry, movedTuple.bx, movedTuple.by, cnt+1, 'd'));
        }

        return result;
    }

    public static Tuple move(int rx, int ry, int bx, int by, char direction) {
        if (direction == 's') return new Tuple(rx, ry, bx, by, 0, direction);

        miniTuple tr;
        miniTuple tb;

        if ((direction == 'l' && ry > by) || (direction == 'r' && ry < by) || (direction == 'u' && bx < rx) ||  (direction == 'd' && bx > rx)) {
            tb = moveUntilWall(bx, by, direction);
            if (arr[tb.x][tb.y] != 'O') arr[tb.x][tb.y] = 'B';
            tr = moveUntilWall(rx, ry, direction);
            if (arr[tb.x][tb.y] != 'O') arr[tb.x][tb.y] = '.';
        }
        else {
            tr = moveUntilWall(rx, ry, direction);
            if (arr[tr.x][tr.y] != 'O') arr[tr.x][tr.y] = 'R';
            tb = moveUntilWall(bx, by, direction);
            if (arr[tr.x][tr.y] != 'O') arr[tr.x][tr.y] = '.';
        }

        return new Tuple(tr.x, tr.y, tb.x, tb.y, 0, direction);
    }

    public static miniTuple moveUntilWall (int x, int y, char direction) {
        if (direction == 'l') {
            while (arr[x][y-1] == '.' || arr[x][y-1] == 'O') {
                y--;
                if (arr[x][y] == 'O') break;
            }
        }
        else if (direction == 'r') {
            while (arr[x][y+1] == '.' || arr[x][y+1] == 'O') {
                y++;
                if (arr[x][y] == 'O') break;
            }
        }
        else if (direction == 'u') {
            while (arr[x-1][y] == '.' || arr[x-1][y] == 'O') {
                x--;
                if (arr[x][y] == 'O') break;
            }
        }
        else {
            while (arr[x+1][y] == '.' || arr[x+1][y] == 'O') {
                x++;
                if (arr[x][y] == 'O') break;
            }
        }

        return new miniTuple(x, y);
    }

    public static class Tuple {
        int rx;
        int ry;
        int bx;
        int by;
        int cnt;
        char direction;

        public Tuple (int rx, int ry, int bx, int by, int cnt, char direction) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.cnt = cnt;
            this.direction = direction;
        }
    }

    public static class miniTuple {
        int x;
        int y;

        public miniTuple (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}