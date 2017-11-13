package com.movie.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonTools {
    private static String convertToJsonFunc(Object obj){
        StringBuffer sb = new StringBuffer();

        if (obj instanceof String || obj instanceof Integer || obj instanceof Float){
            return "\"" + obj + "\"";

        } else if (obj instanceof List){
            sb.append("[");
            boolean first = true;
            for (Object element : (List)obj) {
                if (!first){
                    sb.append(",");
                    sb.append("\n");
                }
                first=false;
                sb.append( convertToJsonFunc(element));


            }
            sb.append("]");
            return sb.toString();
        } else if (obj instanceof Map){
            Set keys =  ((Map) obj).keySet();
            sb.append("{");
            boolean first = true;
            for (Object key : keys){
                if (!first){
                    sb.append(",");
                }
                first=false;
                sb.append( convertToJsonFunc(key));
                sb.append(":");
                sb.append(convertToJsonFunc(((Map) obj).get(key)));


            }
            sb.append("}");
            return sb.toString();

        } else {
             return "\"" + obj +  "\"";
        }
    }

    public static String convertToJson(Object obj){
        return convertToJsonFunc(obj);
    }
}
