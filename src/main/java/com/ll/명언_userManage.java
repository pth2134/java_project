package com.ll;

import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

public class 명언_userManage {
    private HashMap<String, 명언_userInfo> user_data = new HashMap<>();
    private String user_id;
    private int user_number = 0;
    private int last_number = 1;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    명언_userManage() {
        //회원가입? 중복체크, push
        //로그인? contains, 비밀번호 확인, number받아오기
        load();
        in_or_up();
    }

    private void in_or_up() {
        try {
            System.out.println("로그인 하시려면 1, 회원가입 하시려면 2를 입력해주세요.");
            int input = Integer.parseInt(br.readLine());
            if (input == 1) {
                sign_in();
                return;
            } else if (input == 2) {
                sign_up();
                save();
                return;
            } else {
                System.out.println("올바르지 않은 입력입니다.");
            }
        } catch (IOException io) {
            error_print();
        }
        in_or_up();
    }

    private void sign_up() {
        try {
            System.out.println("원하는 ID를 입력해주세요. : ");
            String id = br.readLine();
            if (user_data.containsKey(id)) System.out.println("이미 등록된 ID입니다.\n");
            else {
                System.out.println("비밀번호를 등록해주세요. : ");
                String pw = br.readLine();
                user_data.put(id, new 명언_userInfo(last_number, id, pw));
                System.out.println("회원가입이 완료되었습니다.\n");
                user_number = last_number;
                last_number++;
                return;
            }
        } catch (IOException ie) {
            error_print();
        }
        sign_up();
    }

    private void sign_in() {
        try {
            System.out.println("아이디를 입력해주세요 : ");
            String id = br.readLine();
            if (user_data.containsKey(id)) {
                if (check_password(id)) return;
                return;
            } else {
                while (!user_data.containsKey(id) && !id.equals("")) {
                    System.out.println("등록되지 않은 ID입니다.");
                    System.out.println("아이디를 입력해주세요 : ");
                    id = br.readLine();
                }
                if (user_data.containsKey(id)) {
                    if (check_password(id)) return;
                } else {
                    System.out.println("로그인에 실패했습니다.");
                    return;
                }
            }
        } catch (IOException io) {
            error_print();
        }
        sign_in();
    }

    private boolean check_password(String id) throws IOException {
        System.out.println("비밀번호를 입력해주세요 : ");
        String pw = br.readLine();
        while (!pw.equals(user_data.get(id).getPassword()) && !pw.equals("")) {
            System.out.println("올바르지 않은 비밀번호입니다.");
            System.out.println("비밀번호를 입력해주세요 : ");
            pw = br.readLine();
        }
        if (pw.equals(user_data.get(id).getPassword())) {
            user_number = user_data.get(id).getUser_number();
            user_id = user_data.get(id).getUser_id();
            return true;
        } else {
            System.out.println("로그인에 실패했습니다.");
            return false;
        }
    }

    private void error_print() {
        System.out.println("오류가 발생했습니다.\n");
    }

    public void set_path(){
        System.out.println("원하는 저장경로를 입력해주세요.");
        try{
            String path = br.readLine();
            user_data.get(user_id).setData_path(path);
            System.out.println("경로 지정이 완료되었습니다.\n현재경로 : "+path);
        } catch (IOException io){

        }
    }

    public int getUser_number() {
        return user_number;
    }

    public boolean save() {
        StringBuilder sb = new StringBuilder();
        try {
            FileWriter f = new FileWriter("user_data.json");
            sb.append("[").append("\n");
            Set<String> keySet = user_data.keySet();
            int size = keySet.size();
            int j = 1;
            for (String i : keySet) {
                try {
                    sb.append(user_data.get(i).jsonForm());
                    if (j != size) sb.append(",");
                    sb.append("\n");
                    j++;
                } catch (Exception e) {
                    continue;
                }
            }
            sb.append("]");
            f.write(sb.toString());
            f.close();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    void load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("user_data.json"));
            br.readLine();
            String str;
            while (!(str = br.readLine()).equals("]") && str != null) {
                StringTokenizer st = new StringTokenizer(br.readLine(), ("\":, "));
                st.nextToken();
                int user_number = Integer.parseInt(st.nextToken());
                last_number = Math.max(user_number,last_number);
                st = new StringTokenizer(br.readLine(), ("\":, "));
                st.nextToken();
                String id = st.nextToken();
                st = new StringTokenizer(br.readLine(), ("\":, "));
                st.nextToken();
                String password = st.nextToken();
                st = new StringTokenizer(br.readLine(), ("\":,"));
                st.nextToken();
                st.nextToken();
                String data_path = st.nextToken();
                user_data.put(id, new 명언_userInfo(user_number, id, password));
                br.readLine();
            }
            last_number++;
        } catch (FileNotFoundException fnfe) {
            System.out.println("유저 정보가 존재하지 않습니다.\n");
            return;
        } catch (Exception e) {
            System.out.println("유저 정보를 불러오는데 오류가 발생했습니다. 새로 등록해주세요.\n");
            return;
        }
    }
}
