package com.ll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 명언_request {
    StringBuilder sb = new StringBuilder();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;

    private final static String infoMessage = "-----명령목록-----\n" +
            "등록 : 명언과 작가를 등록합니다.\n" +
            "목록 : 등록되어 있는 id / 작가 / 명언을 보여줍니다.\n" +
            "나의 명언 : 내가 등록한 명언의 목록을 보여줍니다.\n" +
            "수정?id=.. : 등록되어있는 id의 명언과 작가를 수정합니다.\n" +
            "삭제?id=.. : 입력한 id의 명언과 작가를 삭제합니다.\n" +
            "빌드 : 현재의 저장된 id,명언,작가를 파일에 저장합니다.\n" +
            "로그아웃 : 로그아웃합니다.\n" +
            "종료 : 현재상태를 저장하지 않고 종료합니다.\n";
    private final String errorMessage = "오류가 발생했습니다. 요청을 다시 진행해주세요.";
    private final String notMyPhrase = "다른 사람이 작성한 명언입니다.";
    private final String noPhrase = "작성한 명언이 없습니다.";
    명언_data data;
    명언_userManage um;

    명언_request() {
        um = new 명언_userManage();
        if (!um.check_user()) return;
        data = new 명언_data();
        System.out.println(infoMessage);
        request_wait();
    }

    private void request_wait() {
        //요청을 입력받는 메소드
        System.out.print("요청을 기다리는 중입니다 : ");
        try {
            st = new StringTokenizer(br.readLine(), "?");
            String request = st.nextToken();
            if (st.hasMoreTokens()) {
                int id = Integer.parseInt(st.nextToken().replace("id=", ""));
                request_execute(request, id);
                return;
            } else {
                request_execute(request);
            }
        } catch (IOException ioe) {
            System.out.println(errorMessage);
            request_wait();
        }
//        catch (NullPointerException npe) {
//            request_wait();
//        }
    }

    private void request_execute(String request) throws IOException {
        //등록,목록,빌드,종료를 실행하는 메소드
        switch (request) {
            case "등록":
                int id = data.getId();
                this.regist(id);
                um.set_my_phrase(id);
                data.setId(id + 1);
                break;
            case "목록":
                sb = data.list();
                if (sb.isEmpty()) System.out.println(noPhrase);
                else System.out.println(sb);
                break;
            case "나의 명언":
                sb = data.my_list(um.getMy_phrase());
                if (sb.isEmpty()) System.out.println(noPhrase);
                else System.out.println(sb);
                break;
            case "빌드":
                if (data.save()) System.out.println("파일의 내용이 갱신되었습니다.");
                if (um.save()) System.out.println("사용자 정보가 갱신되었습니다.\n");
                else System.out.println("정상적으로 저장되지 않았습니다.\n");
                break;
//            case "경로":
//                um.set_path();
            case "로그아웃":
                um.logOut();
                if (!um.check_user()) return;
                break;
            case "종료":
                System.out.println("명언 앱이 종료됩니다.");
                return;
        }
        request_wait();
    }

    private void regist(int id) throws IOException {
        data.addData(id, phrase_wait(), author_wait());
        System.out.println(id + "번 명언이 등록되었습니다.\n");
    }

    private void request_execute(String request, int id) throws IOException {
        //수정,삭제를 실행하는 메소드
        switch (request) {
            case "수정":
                if (um.is_my_phrase(id)) this.adjust(id);
                else System.out.println(notMyPhrase);
                break;
            case "삭제":
                if (um.is_my_phrase(id)) {
                    this.delete(id);
                    um.delete_my_phrase(id);
                } else System.out.println(notMyPhrase);
                break;
        }
        request_wait();
    }

    private void adjust(int id) throws IOException {
        try {
            명언 p = data.getData(id);
            System.out.println("명언(기존) : " + p.getContent());
            p.setContent(phrase_wait());
            System.out.println("작가(기존) : " + p.getAuthor());
            p.setAuthor(author_wait());
            System.out.println(id + "번 명언이 수정되었습니다.\n");
        } catch (NullPointerException npe) {
            System.out.println(id + "번 명언이 존재하지 않습니다.\n등록하시겠습니까?(Y/N)");
            if (this.agree()) this.regist(id);
        }
    }

    private void delete(int id) throws IOException {
        if (data.delete(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.\n");
        } else {
            System.out.println(id + "번 명언이 존재하지 않습니다.\n");
        }
    }

    private String phrase_wait() throws IOException {
        System.out.print("명언 : ");
        String phrase = br.readLine();
        return phrase;
    }

    private String author_wait() throws IOException {
        System.out.print("작가 : ");
        String author = br.readLine();
        return author;
    }

    private boolean agree() throws IOException {
        if (br.readLine().equals("Y")) {
            return true;
        } else {
            return false;
        }
    }
}
