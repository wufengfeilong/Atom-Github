package com.example.demo.controller;
/** 
* create by 張風武 
* 2017/11/15 10:59:22 
*/
public class JavaTest {
    public static void main(String[] args) {
        String mString = "f5165895";
        ByteArrayToHexString(mString.getBytes());
    }
    private static String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
        "B", "C", "D", "E", "F" };
        String out = "";
        
        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        System.out.println(out);
        return out;
        }

}
