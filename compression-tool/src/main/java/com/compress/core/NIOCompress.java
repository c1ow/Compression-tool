package com.compress.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 */
public class NIOCompress {
    /**
     * 将数据压缩成zip的形式
     */
    public byte[] commpressToZIP(){
        //获取数据流
        List<JSONObject> mapList = new ArrayList<JSONObject>();
        JSONObject m = new JSONObject();
       for (int i = 0;i<1000;i++){
           m.put("x"+i,"zzzzz");
       }
        mapList.add(m);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(String.valueOf(mapList).getBytes("UTF-8"));
            gzipOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解压缩
     * @param data
     * @return
     */
    public String unCommpress(byte[] data){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream,"UTF-8"));
            String s ;
            StringBuilder stringBuilder = new StringBuilder();
            while ((s = bufferedReader.readLine())!= null){
                System.out.println(s);
                stringBuilder.append(s);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        NIOCompress nioCompress = new NIOCompress();
        byte[] bytes = nioCompress.commpressToZIP();
        System.out.println(new String(bytes));
        String s = nioCompress.unCommpress(bytes);
        JSONArray array = JSONArray.parseArray(s);
        System.out.println(JSON.toJSONString(array.get(0)));
    }
}
