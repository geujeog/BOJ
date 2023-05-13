import java.io.*;
import java.util.*;

public class B2461 {
        public static void main (String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            List<Tuple> list = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < M; j++) {
                    list.add(new Tuple(i, Integer.parseInt(st.nextToken())));
                }
            }

            Collections.sort(list, new Comparator<Tuple>(){
                @Override
                public int compare (Tuple t1, Tuple t2) {
                    return Integer.compare(t1.number, t2.number);
                }
            });

            int result = Integer.MAX_VALUE;
            int start = 0;
            int end = 0;

            Map<Integer, Integer> map = new HashMap<>();

            while (end < N*M) {
                Tuple e = list.get(end);

                map.put(e.idx, map.getOrDefault(e.idx, 0)+1);
                while (map.get(list.get(start).idx) > 1) {
                    map.put(list.get(start).idx, map.get(list.get(start).idx)-1);

                    if (map.get(list.get(start).idx) == 0) map.remove(list.get(start).idx);
                    start++;
                }

                if (map.size() == N) result = Math.min(result, e.number - list.get(start).number);

                end++;
            }

            bw.write(result+"");

            br.close();
            bw.flush();
            bw.close();
        }

        public static class Tuple {
            int idx;
            int number;

            public Tuple (int idx, int number) {
                this.idx = idx;
                this.number = number;
            }
        }
    }
