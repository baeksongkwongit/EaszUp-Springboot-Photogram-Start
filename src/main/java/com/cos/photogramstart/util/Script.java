package com.cos.photogramstart.util;

public class Script {
    public static String back(String msg){
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+msg+"');");
        sb.append("history.back(-1);");
        sb.append("</script>");
        return sb.toString();

    }

}
