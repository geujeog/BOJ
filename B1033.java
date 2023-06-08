import java.util.*;
import java.io.*;

public class B1033 {
    static int N;
    static List<Tuple>[] list;
    static long[] balance;
    static boolean[] visit;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }

        // 전체 최소 공배수
        long lcm = 1l;

        for (int i = 1; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long p = Long.parseLong(st.nextToken());
            long q = Long.parseLong(st.nextToken());

            // 두 수의 최대공약수
            long div = (p > q) ? gcd(p, q) : gcd(q, p);

            long pd = p / div;
            long qd = q / div;

            lcm *= (pd * qd);

            list[a].add(new Tuple(b, qd, pd));
            list[b].add(new Tuple(a, pd, qd));
        }

        balance = new long[N];
        visit= new boolean[N];

        balance[0] = lcm;

        combination(0);

        for (int i = 1; i < N; i++) {
            lcm = gcd(lcm, balance[i]);
        }

        for (int i = 0; i < N; i++) {
            bw.write(balance[i]/lcm+" ");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    public static void combination (int parent) {
        visit[parent] = true;

        for (Tuple tuple : list[parent]) {
            int child = tuple.child;
            long cb = tuple.childBalance;
            long pb = tuple.parentBalance;

            if (visit[child]) continue;

            balance[child] = balance[parent] * cb / pb;
            combination(child);
        }
    }

    public static long gcd (long a, long b) {
        if (b == 0l) return a;
        return gcd(b, a%b);
    }

    public static class Tuple {
        int child;
        long childBalance;
        long parentBalance;

        public Tuple (int child, long childBalance, long parentBalance) {
            this.child = child;
            this.childBalance = childBalance;
            this.parentBalance = parentBalance;
        }
    }
}