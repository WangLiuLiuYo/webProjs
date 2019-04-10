package Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashKeyConverter {
    public static String string2MD5(String s){
        MessageDigest md5=null;
        try{
            md5=MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            System.out.println("have error in encode algorithm.");
        }
        char[] chars=s.toCharArray();
        byte[] old=new byte[chars.length];
        for(int i=0;i<chars.length;i++){
            old[i]=(byte)chars[i];
        }
        byte[] arr=md5.digest(old);
        StringBuilder sb=new StringBuilder();
        for(byte b:arr){
            sb.append(b);
        }
        return sb.toString();
    }
}
