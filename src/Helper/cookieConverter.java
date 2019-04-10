package Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class cookieConverter {
    //接近base64的RandomBase16编码
    /*
       this class algorithm like base64-encode ;
       but this code-list is random.
       so,anyone decode diffcult but it user.
     */
    private final static Random rand=new Random();
    private char[] chars=null;
    private final int Base=16;
    private HashMap<Character,Integer> map=new HashMap<>();
    private static final String charSet="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890+-=[]";
    public void shuffle(){
        chars=new char[Base];
        char[] charset=charSet.toCharArray();
        for(int i=0;i<charset.length/2;i++){
            int p=rand.nextInt(charset.length-1);
            int q=rand.nextInt(charset.length-1);
            char c=charset[p];
            charset[p]=charset[q];
            charset[q]=c;
        }
        for(int i=0;i<Base;i++){
            chars[i]=charset[i];
            map.put(chars[i],i);
        }
    }
    public cookieConverter(){
        shuffle();
    }
    public String encode(String cookie){
        String str=cookie;
        char[] charArray=str.toCharArray();
        StringBuilder stringBuilder=new StringBuilder();
        for(char c:charArray){
            stringBuilder.append(Integer.toBinaryString((int)c));
        }
        String temp=stringBuilder.toString();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<temp.length();i+=4){
            String sub=temp.substring(i,i+4);
            int key=Integer.valueOf(sub,2);
            sb.append(chars[key]);
        }
        return sb.toString();
    }
    public String decode(String str){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<str.length();i++){
            char c=str.charAt(i);
            int val=map.get(c);
            String v=Integer.toBinaryString(val);
            v="0".repeat(4-v.length())+v;
            sb.append(v);
        }
        String temp=sb.toString();
        StringBuilder ans=new StringBuilder();
        for(int i=0;i<temp.length();i+=6){
            String sub=temp.substring(i,i+6);
            int key=Integer.parseInt(sub,2);
            ans.append((char)key);

        }
        return ans.toString();
    }
    public static void main(String[] args){
        String s="123456789123456789";
        cookieConverter converter=new cookieConverter();
        String enc=converter.encode(s);
        System.out.println(enc);
        String dec=converter.decode(enc);
        System.out.println(dec);
    }
}
