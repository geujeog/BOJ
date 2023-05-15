import java.util.*;
import java.io.*;

public class B20366 {
        public static void main (String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

            int N = Integer.parseInt(br.readLine());
            int[] arr = new int[N];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(arr);

            int result = Integer.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                for (int j = i+1; j < N; j++) {
                    int innerStart = i+1;
                    int innerEnd = j-1;

                    int outerSize = arr[i] + arr[j];

                    while (innerStart < innerEnd && innerEnd < j) {
                        int innerSize = arr[innerStart] + arr[innerEnd];
                        result = Math.min(result, Math.abs(outerSize - innerSize));

                        if (outerSize > innerSize) {
                            innerStart++;
                        }
                        else if (outerSize < innerSize) {
                            innerEnd--;
                        }
                        else break;
                    }
                    if (result == 0) break;
                }
                if (result == 0) break;
            }

            bw.write(result+"");

            br.close();
            bw.flush();
            bw.close();
        }
    }