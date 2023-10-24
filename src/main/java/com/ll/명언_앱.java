package com.ll;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class 명언_앱 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int id = 0;
        명언_command command = new 명언_command();
        System.out.println("== 명언 앱 ==");
        StringTokenizer st = new StringTokenizer(sc.nextLine(), "?");
        String 명령 = st.nextToken();
        while (!명령.equals("종료")) {
            switch (명령) {
                case "등록":
                    command.등록();
                    break;
                case "목록":
                    command.목록();
                    break;
                case "삭제":
                    id = Integer.parseInt(st.nextToken().replace("id=", ""));
                    command.삭제(id);
                    break;
                case "수정":
                    id = Integer.parseInt(st.nextToken().replace("id=", ""));
                    command.수정(id);
                    break;
                case "빌드":
                    command.빌드();
            }
            st = new StringTokenizer(sc.nextLine(), "?");
            명령 = st.nextToken();
        }

    }
}
