import java.sql.Array;
import java.util.*;
import java.io.*;

class B17471 {
    static int N;
    static int[] people;
    static int sumPeople;
    static List<Integer>[] list;
    static int result;

    public static void main (String[] args) throws IOException {
        input();
        solution();
        output();
    }

    public static void solution() {
        // 선거구 개수 구하기
        // 선거구가 하나일 경우, 조합으로 구역 나누기

        int areaCnt = getArea();
        if (areaCnt == 1) {
            result = getSeparate();
        }
        else if (areaCnt == 2) {
            result = getDiff();
        }
        else {
            result = -1;
        }
    }

    public static int getSeparate() {
        int min = Integer.MAX_VALUE;

        for (int i = 1; i <= N; i++) {
            min = Math.min(min, trace(i, new ArrayList<>(), 0));
        }

        return min;
    }

    public static int trace(int s, List<Integer> choice, int sum) {
        int min = Integer.MAX_VALUE;

        if (s == N+1) {
            if (isLink(choice)) {
                return Math.abs(sum - (sumPeople - sum));
            }
            return min;
        }

        choice.add(s);
        sum += people[s];

        for (int i = s+1; i <= N+1; i++) {
            min = Math.min(min, trace(i, choice, sum));
        }

        choice.remove((Integer) s);
        sum -= people[s];

        return min;
    }

    public static boolean isLink(List<Integer> choice) {
        if (choice.size() == 0 || choice.size() == N) return false;

        if (checkLinkChoice(choice)) {
            List<Integer> notChoice = new ArrayList<>();
            for (int i = 1; i <= N; i++) {
                if (!choice.contains(i)) {
                    notChoice.add(i);
                }
            }
            if (checkLinkChoice(notChoice)) return true;
        }
        return false;
    }

    public static boolean checkLinkChoice(List<Integer> choice) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visit = new boolean[N+1];
        int q, cnt = 1;

        queue.add(choice.get(0));
        visit[choice.get(0)] = true;

        while (!queue.isEmpty()) {
            q = queue.poll();

            for (int next: list[q]) {
                if (!visit[next] && choice.contains(next)) {
                    cnt++;
                    visit[next] = true;
                    queue.add(next);
                }
            }
        }

        return (cnt == choice.size());
    }

    public static int getDiff() {
        boolean[] visit = new boolean[N+1];
        int sum = 0;

        visit[1] = true;
        getAreaDFS(1, visit);

        for (int i = 1; i <= N; i++) {
            if (visit[i]) sum += people[i];
        }

        return Math.abs(sum - (sumPeople - sum));
    }

    public static int getArea() {
        boolean[] visit = new boolean[N+1];
        int cnt = 0;

        for (int i = 1; i <= N; i++) {
            if (!visit[i]) {
                cnt++;
                visit[i] = true;
                getAreaDFS(i, visit);
            }
        }
        return cnt;
    }

    public static void getAreaDFS(int v, boolean[] visit) {
        for (int next: list[v]) {
            if (!visit[next]) {
                visit[next] = true;
                getAreaDFS(next, visit);
            }
        }
    }

    public static void output() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write(result+"");

        bw.flush();
        bw.close();
    }

    public static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        people = new int[N+1];
        list = new ArrayList[N+1];
        sumPeople = 0;
        result = Integer.MAX_VALUE;
        int k;

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            people[i] = Integer.parseInt(st.nextToken());
            sumPeople += people[i];
            list[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            k = Integer.parseInt(st.nextToken());
            while (k-- > 0) {
                list[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        br.close();
    }
}