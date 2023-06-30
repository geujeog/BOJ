import java.util.*;
import java.io.*;

public class B9935 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String str = br.readLine();
        String bumb = br.readLine();

        LinkedList<Character> queue = new LinkedList<>();

        for (int i = 0; i < str.length(); i++) {
            queue.add(str.charAt(i));

            if (queue.size() >= bumb.length()) {
                StringBuilder sb = new StringBuilder();

                boolean find = false;

                int bumbIdx = bumb.length()-1;
                while (queue.peekLast() == bumb.charAt(bumbIdx)) {
                    sb.append(queue.pollLast());
                    bumbIdx--;

                    if (bumbIdx == -1) {
                        find = true;
                        break;
                    }
                }

                if (!find) {
                    for (int j = sb.length()-1; j >= 0; j--) {
                        queue.add(sb.charAt(j));
                    }
                }
            }
        }

        if (queue.isEmpty()) {
            bw.write("FRULA");
        }
        else {
            while (!queue.isEmpty()) {
                bw.write(queue.pollFirst());
            }
        }

        br.close();
        bw.flush();
        bw.close();
    }
}