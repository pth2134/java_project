package com.ll;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class 명언_command {
    HashMap<Integer, 명언> data = new HashMap<>();
    int idx;

    명언_command() {
        this.로드();
    }

    void 등록() {
        Scanner sc = new Scanner(System.in);
        System.out.print("명언 : ");
        String phrase = sc.nextLine();
        System.out.print("작가 : ");
        String writer = sc.nextLine();
        data.put(idx, new 명언(idx, phrase, writer));
        System.out.println(idx + "번 명언이 등록되었습니다.");
        idx++;
    }

    void 목록() {
        for (int i = 1; i < idx; i++) {
            try {
                명언 p = data.get(i);
                System.out.println(p.id + " / " + p.author + " / " + p.content);
            } catch (Exception e) {
                continue;
            }
        }
    }

    void 삭제(int id) {
        if(!data.containsKey(id)) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }
        data.remove(id);
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }

    void 수정(int id) {
        Scanner sc = new Scanner(System.in);
        명언 p = data.get(id);
        System.out.println("명언(기존) : " + p.content);
        System.out.print("명언 : ");
        p.content = sc.nextLine();
        System.out.println("작가(기존) : " + p.author);
        System.out.print("작가 : ");
        p.author = sc.nextLine();
    }

    void 로드() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("data.json"));
            br.readLine();
            String str;
            idx = 0;
            while (!(str = br.readLine()).equals("]") && str != null) {
                StringTokenizer st = new StringTokenizer(br.readLine(), ("\":,\t "));
                st.nextToken();
                int id = Integer.parseInt(st.nextToken());
                idx = id;
                st = new StringTokenizer(br.readLine(), ("\":,\t"));
                st.nextToken();
                st.nextToken();
                st.nextToken();
                String content = st.nextToken();
                st = new StringTokenizer(br.readLine(), ("\":,\t "));
                st.nextToken();
                String author = st.nextToken();
                data.put(id, new 명언(id, content, author));
                br.readLine();
            }
            idx++;
        } catch (FileNotFoundException fnfe) {
            return;
        } catch (IOException ioe) {
            System.out.println("파일 읽기 오류");
            return;
        }
    }

    void 빌드() {
        StringBuilder sb = new StringBuilder();
        try {
            FileWriter f = new FileWriter("data.json");
            sb.append("[").append("\n");
            for (int i = 1; i < idx; i++) {
                try {
                    명언 p = data.get(i);
                    sb.append("\t{").append("\n")
                            .append("\t\t\"id\" : " + p.id + ",\n")
                            .append("\t\t\"content\" : \"" + p.content + "\",\n")
                            .append("\t\t\"author\" : \"" + p.author + "\"\n");
                    sb.append("\t}");
                    if (i != idx - 1) sb.append(",");
                    sb.append("\n");
                } catch (Exception e) {
                    continue;
                }
            }
            sb.append("]");
            f.write(sb.toString());
            f.close();
        } catch (IOException ioe) {
            System.out.println("정상적으로 종료되지 않았습니다.");
        }
    }
}
