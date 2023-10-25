package com.ll;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class 명언_data {
    private HashMap<Integer, 명언> data = new HashMap<>();
    int id = 1;

    public 명언_data() {
        this.load();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public 명언 getData(int id) {
        return data.get(id);
    }

    public void addData(int id, String phrase, String author) {
        data.put(id, new 명언(id, phrase, author));
    }

    public StringBuilder list() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < id; i++) {
            try{
                sb.append(data.get(i).getInfo());
            } catch (NullPointerException npe){
                continue;
            }
        }
        return sb;
    }

    public boolean save() {
        StringBuilder sb = new StringBuilder();
        try {
            FileWriter f = new FileWriter("data.json");
            sb.append("[").append("\n");
            for (int i = 1; i < id; i++) {
                try {
                    sb.append(data.get(i).jsonForm());
                    if (i != id - 1) sb.append(",");
                    sb.append("\n");
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

    public boolean delete(int id) {
        if (data.containsKey(id)) {
            data.remove(id);
            return true;
        } else {
            return false;
        }
    }

    void load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.json"));
            br.readLine();
            String str;
            while (!(str = br.readLine()).equals("]") && str != null) {
                StringTokenizer st = new StringTokenizer(br.readLine(), ("\":, "));
                st.nextToken();
                int id = Integer.parseInt(st.nextToken());
                this.id = id;
                st = new StringTokenizer(br.readLine(), ("\":,"));
                st.nextToken();
                st.nextToken();
                st.nextToken();
                String content = st.nextToken();
                st = new StringTokenizer(br.readLine(), ("\":, "));
                st.nextToken();
                String author = st.nextToken();
                data.put(id, new 명언(id, content, author));
                br.readLine();
            }
            id++;
        } catch (FileNotFoundException fnfe) {
            System.out.println("기존 파일을 찾을 수 없습니다. 새로운 파일을 생성합니다.\n");
            return;
        } catch (Exception e) {
            System.out.println("파일 불러오는데 오류가 발생했습니다. 새로운 파일을 생성합니다.\n");
            return;
        }
    }

}
