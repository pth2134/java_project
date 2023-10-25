package com.ll;

public class 명언 {
    private int id;
    private String content;
    private String author;

    명언(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInfo() {
        return id + " / " + author + " / " + content + "\n";
    }

    public StringBuilder jsonForm() {
        StringBuilder sb = new StringBuilder();
        sb.append("  {").append("\n")
                .append("    \"id\" : " + id + ",\n")
                .append("    \"content\" : \"" + content + "\",\n")
                .append("    \"author\" : \"" + author + "\"\n");
        sb.append("  }");
        return sb;
    }

}
