import java.util.*;
import java.io.*;

public class B3190 {
    static int N;
    static char[][] arr;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        arr = new char[N][N];
        List<Direction> directions = new ArrayList<>();

        int K = Integer.parseInt(br.readLine());
        for (int k = 0; k < K; k++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            arr[x-1][y-1] = 'a';
        }

        int L = Integer.parseInt(br.readLine());
        for (int l = 0; l < L; l++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            char direction = st.nextToken().charAt(0);

            directions.add(new Direction(time, direction));
        }

        bw.write(snake(directions)+"");

        br.close();
        bw.flush();
        bw.close();
    }

    public static int snake (List<Direction> directions) {
        int endTime = 0;

        Queue<Tuple> queue = new LinkedList<>();
        queue.add(new Tuple(0, 0, 0, true, 1, 0));
        queue.add(new Tuple(0, 0, 0, false, 1, 0));

        while (!queue.isEmpty()) {
            Tuple tuple = queue.poll();
            int x = tuple.x;
            int y = tuple.y;
            int time = tuple.time;
            boolean isHead = tuple.isHead;
            int dir = tuple.dir;
            int insIdx = tuple.insIdx;

            if (insIdx < directions.size() && time == directions.get(insIdx).time) {
                char direction = directions.get(insIdx).direction;
                insIdx++;

                if (direction == 'L') dir--;
                else if (direction == 'D') dir++;

                if (dir == -1) dir = 3;
                else if (dir == 4) dir = 0;
            }

            boolean isBreak = false;

            if (dir == 0) {
                if (x-1 >= 0) {
                    if (isHead && arr[x - 1][y] == 's') isBreak = true;

                    queue.add(new Tuple(x - 1, y, time + 1, isHead, dir, insIdx));
                }
                else isBreak = true;
            }
            else if (dir == 1) {
                if (y+1 < N) {
                    if (isHead && arr[x][y + 1] == 's') isBreak = true;
                    queue.add(new Tuple(x, y + 1, time + 1, isHead, dir, insIdx));
                }
                else isBreak = true;
            }
            else if (dir == 2) {
                if (x+1 < N) {
                    if (isHead && arr[x + 1][y] == 's') isBreak = true;
                    queue.add(new Tuple(x + 1, y, time + 1, isHead, dir, insIdx));
                }
                else isBreak = true;
            }
            else if (dir == 3) {
                if (y-1 >= 0) {
                    if (isHead && arr[x][y - 1] == 's') isBreak = true;
                    queue.add(new Tuple(x, y - 1, time + 1, isHead, dir, insIdx));
                }
                else isBreak = true;
            }

            if (isBreak) {
                endTime = time+1;
                break;
            }

            if (isHead) {
                if (arr[x][y] == 'a') {
                    queue.add(queue.poll());
                }

                arr[x][y] = 's';
            }
            else {
                arr[x][y] = '.';
            }
        }

        return endTime;
    }

    public static class Tuple {
        int x;
        int y;
        int time;
        boolean isHead; // true - head, false - tail
        int dir; // 0 - 북, 1 - 동, 2 - 남, 3 - 서
        int insIdx;

        public Tuple (int x, int y, int time, boolean isHead, int dir, int insIdx) {
            this.x = x;
            this.y = y;
            this.time = time;
            this.isHead = isHead;
            this.dir = dir;
            this.insIdx = insIdx;
        }
    }

    public static class Direction {
        int time;
        char direction;

        public Direction (int time, char direction) {
            this.time = time;
            this.direction = direction;
        }
    }
}