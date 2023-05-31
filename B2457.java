import java.util.*;
import java.io.*;

public class B2457 {
    static int N;
    static int[][] days;

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        days = new int[N][2];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            days[i][0] = Integer.parseInt(st.nextToken()) * 100 + Integer.parseInt(st.nextToken());
            days[i][1] = Integer.parseInt(st.nextToken()) * 100 + Integer.parseInt(st.nextToken());
        }

        // start는 작은순, end는 큰순
        Arrays.sort(days, new Comparator<int[]>(){
            @Override
            public int compare (int[] d1, int[] d2) {
                if (d1[0] == d2[0]) {
                    return Integer.compare(d2[1], d1[1]);
                }
                return Integer.compare(d1[0], d2[0]);
            }
        });

        int start = 301;
        int end = 1201;
        int result = 0;
        int last = 0;
        int idx = 0;

        while (start < end) {
            boolean check = false;

            for (int i = idx; i < N; i++) {
                if (days[i][0] > start) break;

                if (last < days[i][1]) {
                    check = true;
                    last = days[i][1];
                    idx = i+1;
                }
            }

            if (check) {
                start = last;
                result++;
            }
            else break;
        }

        if (last >= 1201) bw.write(result+"");
        else bw.write(0+"");

        br.close();
        bw.flush();
        bw.close();
    }
}