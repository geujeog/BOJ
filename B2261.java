import java.util.*;
import java.io.*;

class B2261 {
    static int N;
    static List<Tuple> list;
    static int result;

    public static void main (String[] args) throws IOException {
        init();
        solution();
        print();
    }

    public static void solution() {
        Collections.sort(list, (a, b) -> a.x - b.x);

        result = shortest(0, N-1);
    }

    public static int shortest(int s, int e) {
        if (e - s + 1 <= 3) {
            return bruteforce(s, e);
        }

        int mid = (s + e) / 2;

        int min = Math.min(shortest(s, mid), shortest(mid+1, e));

        return shortestBand(s, e, min);
    }

    public static int shortestBand(int s, int e, int min) {
        List<Tuple> band = new ArrayList<>();

        int mid = (s + e) / 2;

        for (int i = s; i <= e; i++) {
            int d = list.get(mid).x - list.get(i).x;

            if (d * d < min) {
                band.add(list.get(i));
            }
        }

        Collections.sort(band, (a, b) -> a.y - b.y);

        for (int i = 0; i < band.size(); i++) {
            for (int j = i+1; j < band.size(); j++) {
                int d = band.get(i).y - band.get(j).y;

                if (d * d < min) {
                    min = Math.min(min, getDistance(band.get(i), band.get(j)));
                }
                else break;
            }
        }

        return min;
    }

    public static int bruteforce(int s, int e) {
        int min = Integer.MAX_VALUE;

        for (int i = s; i <= e; i++) {
            for (int j = i+1; j <= e; j++) {
                min = Math.min(min, getDistance(list.get(i), list.get(j)));
            }
        }

        return min;
    }

    public static int getDistance(Tuple a, Tuple b) {
        return (int) Math.pow(a.x - b.x, 2) + (int) Math.pow(a.y - b.y, 2);
    }

    public static class Tuple {
        int x;
        int y;

        public Tuple (int x, int y) {
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

    public static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            list.add(new Tuple(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        br.close();
    }
}