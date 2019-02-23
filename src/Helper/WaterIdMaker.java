package Helper;

import java.util.Date;

public class WaterIdMaker{
    private final static int head=10000000;
    private static int count=0;
    public static String getWid(String fromId,String toId){
        Date d=new Date();
        String s=String.format("%02d%02d%02d%02d%02d%02d",d.getYear(),d.getMonth(),d.getDay(),d.getHours(),d.getMinutes(),d.getSeconds());//12
        String w=Integer.valueOf(head+count++).toString();//8
        count%=head;
        return s+w;
    }
}