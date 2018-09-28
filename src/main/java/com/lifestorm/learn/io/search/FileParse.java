package com.lifestorm.learn.io.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by life_storm on 2018/8/15.
 */
public class FileParse {
    //2018-08-14 10:18:18=====>>消息[25df9a61d00e4a73bcd38b018dbe34f0]已处理，业务单号[118000005118796]-业务类型[sendForVisaTrans]，耗时：908
    private static final String mainPath = "D:\\parse";
    private final static Map<String,List<TotalMessage>> map = new HashMap<String, List<TotalMessage>>(1000);

    static class TotalMessage{
        int time;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File(mainPath);
        File[] files = file.listFiles();
        for(File tempFile : files ){
             fileRead(tempFile);
        }
        Set<String> keySet = map.keySet();
        for (String key : keySet ){
            List<TotalMessage> messageList = map.get(key);
            int countTime = 0;
            int totalCount = messageList.size();
            for (TotalMessage t : messageList) {
                countTime += t.getTime();
            }
            System.out.println("业务类型："+key+"平均耗时："+countTime/totalCount+"毫秒");
        }
    }

    public static void fileRead(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line =null ;
        while ((line = bufferedReader.readLine())!= null) {
                String[] index = line.split("业务类型\\[");
                if(index.length >= 2 ){
                    String[] index2 = index[1].split("]");
                    if(index2.length >=2 ){
                        String key = index2[0];
                        List<TotalMessage> totalMessages = map.get(key);
                        if(totalMessages == null ){
                            totalMessages = new ArrayList<TotalMessage>();
                            map.put(key,totalMessages);
                        }
                        String[] index3 = index2[1].split("：");
                        if(index3.length >=2 ){
                            int time = Integer.valueOf(index3[1].trim());
//                                System.out.println(line);
                                TotalMessage totalMessage = new TotalMessage();
                                totalMessage.setTime(time);
                                totalMessages.add(totalMessage);
                        }
                    }
                }
        }
        bufferedReader.close();
        fileReader.close();
    }
}
