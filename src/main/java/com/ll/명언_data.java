package com.ll;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class 명언_data {
    private HashMap<Integer, 명언> data = new HashMap<>();
    int id = 1;

    public 명언_data() {
        this.load();
    }

    public int getId() {
        return id;
    }

    public 명언 getData(int id) {
        return data.get(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addData(int id, String phrase, String author) {
        data.put(id, new 명언(id, phrase, author));
    }

    public StringBuilder list() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : data.keySet()) {
            try {
                sb.append(data.get(i).makeInfo());
            } catch (NullPointerException npe) {
                continue;
            }
        }
        return sb;
    }

    public StringBuilder my_list(HashSet<Integer> my_phrase) {
        StringBuilder sb = new StringBuilder();
        try {
            HashSet<Integer> mp = my_phrase;
        } catch (NullPointerException npe) {
            return sb;
        }
        try {
            for (int i : my_phrase) {
                try {
                    sb.append(data.get(i).makeInfo());
                } catch (NullPointerException npe) {
                    my_phrase.remove(i);
                    continue;
                }
            }
        } catch (NullPointerException npe) {

        }
        return sb;
    }

    public boolean delete(int id) {
        if (data.containsKey(id)) {
            data.remove(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean save() {
        try {
            FileWriter f = new FileWriter("data.json");
//            StringBuilder sb = new StringBuilder();
//            sb.append("[").append("\n");
//            for (int i = 1; i < id; i++) {
//                try {
//                    sb.append(data.get(i).jsonForm());
//                    if (i != id - 1) sb.append(",");
//                    sb.append("\n");
//                } catch (NullPointerException npe) {
//                    continue;
//                }
//            }
//            sb.append("]");
//            f.write(sb.toString());
            ObjectMapper objectMapper = new ObjectMapper();
            f.write(objectMapper.writeValueAsString(data));
            f.close();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }
    void load() {
        try {
//            BufferedReader br = new BufferedReader(new FileReader("data.json"));
//            br.readLine();
//            String str;
//            while (!(str = br.readLine()).equals("]") && str != null) {
//                StringTokenizer st = new StringTokenizer(br.readLine(), ("\":, "));
//                st.nextToken();
//                int id = Integer.parseInt(st.nextToken());
//                this.id = id;
//                st = new StringTokenizer(br.readLine().trim(), ("\":,"));
//                st.nextToken();
//                st.nextToken();
//                st.nextToken();
//                String content = st.nextToken();
//                st = new StringTokenizer(br.readLine(), ("\":, "));
//                st.nextToken();
//                String author = st.nextToken();
//                data.put(id, new 명언(id, content, author));
//                br.readLine();
//            }
//            if (data.size() == 0) id = 0;
//            id++;
            ObjectMapper objectMapper = new ObjectMapper();
            String json = Files.readString(Paths.get("data.json"));
            TypeReference<HashMap<Integer,명언>> typeReference = new TypeReference<HashMap<Integer, 명언>>() {};
            data = objectMapper.readValue(json, typeReference);
            id = Collections.max(data.keySet())+1;
        } catch (FileNotFoundException fnfe) {
            System.out.println("기존 파일을 찾을 수 없습니다. 새로운 파일을 생성합니다.\n");
            return;
        } catch (IOException ioe) {
            System.out.println("파일 불러오는데 오류가 발생했습니다. 새로운 파일을 생성합니다.\n");
            return;
        }
    }
}
