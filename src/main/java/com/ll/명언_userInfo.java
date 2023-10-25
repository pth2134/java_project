package com.ll;

public class 명언_userInfo {
    private String password;
    private int user_number;
    private String user_id;
    private String data_path;

    public String getUser_id() {
        return user_id;
    }

    명언_userInfo(int number, String id, String pw) {
        user_number = number;
        user_id = id;
        password = pw;
    }

    public String getPassword() {
        return password;
    }

    public int getUser_number() {
        return user_number;
    }

    public void setData_path(String data_path) {
        this.data_path = data_path;
    }

    public String getData_path() {
        return data_path;
    }

    public StringBuilder jsonForm() {
        StringBuilder sb = new StringBuilder();
        sb.append("  {").append("\n")
                .append("    \"user_number\" : " + user_number + ",\n")
                .append("    \"id\" : \"" + user_id + "\",\n")
                .append("    \"password\" : \"" + password + "\"\n")
                .append("    \"data_path\" : \"" + data_path + "\"\n");
        sb.append("  }");
        return sb;
    }
}
