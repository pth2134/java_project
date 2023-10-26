package com.ll;

import java.util.HashSet;

public class 명언_userInfo {
    private String password;
    private int user_number;
    private String user_id;
    private HashSet<Integer> my_phrase;

    public String getUser_id() {
        return user_id;
    }

    명언_userInfo(int number, String id, String pw, HashSet<Integer> my_phrase) {
        user_number = number;
        user_id = id;
        password = pw;
        this.my_phrase = my_phrase;
    }

    public HashSet<Integer> getMy_phrase() {
        return my_phrase;
    }

    public void setMy_phrase(int phrase_id) {
        my_phrase.add(phrase_id);
    }

    public void removeMy_phrase(int phrase_id) {
        my_phrase.remove(phrase_id);
    }

    public String getPassword() {
        return password;
    }

    public int getUser_number() {
        return user_number;
    }

//    public void setData_path(String data_path) {
//        this.data_path = data_path;
//    }
//
//    public String getData_path() {
//        return data_path;
//    }

    public StringBuilder jsonForm() {
        StringBuilder sb = new StringBuilder();
        sb.append("  {").append("\n")
                .append("    \"user_number\" : " + user_number + ",\n")
                .append("    \"id\" : \"" + user_id + "\",\n")
                .append("    \"password\" : \"" + password + "\",\n")
//              .append("    \"data_path\" : \"" + data_path + "\"\n")
        ;
        sb.append("    \"my_phrase\" : {");
        for (int i : my_phrase) {
            sb.append(i).append(",");
        }
        sb.append("}\n");
        sb.append("  }");
        return sb;
    }
}
