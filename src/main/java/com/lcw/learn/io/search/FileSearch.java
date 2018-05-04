package com.lcw.learn.io.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by engle on 2018/5/4.
 */
public class FileSearch {
    private static final ConcurrentMap<Integer,String> concurrentMap = new ConcurrentHashMap<Integer,String>();

    private static final String mainPath = "D:\\svn\\rj\\02code\\22bran";
    public static void main(String[] args) throws Exception {
        File mainFile = new File(mainPath);
        FileBox fileBox = new FileBox(mainFile,1);
        buildFiles(mainFile,fileBox);
        fileBox.print();
        //search file key word
        List<File> fList = fileBox.getAllFiles();
        String keyWord = "才江南";
        for (File file : fList){
            if(searchKey(file,keyWord)){
                System.out.println(String.format("找到了，文件名为%s:",file.getCanonicalPath()));
            }
        }
    }

    public static boolean  searchKey(File file,String key) throws Exception{
        boolean findFlag =false ;
        if(file.isFile()){
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line =null ;
            while ((line = bufferedReader.readLine())!= null) {
                if( line.contains(key)){
                    findFlag = true ;
                    break;
                }
            }
        }
        return findFlag;
    }


    public static void buildFiles(File file,FileBox vo ){
        File[] files =  file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File tempFile = files[i];
            if( tempFile.isDirectory()){
                FileBox newVo = new FileBox(tempFile,vo.index+1);
                vo.childreList.add(newVo);
                buildFiles(tempFile,newVo);
            }else {
                if(vo.childrenFiles == null) {
                    vo.childrenFiles = new ArrayList<File>();
                }
                vo.childrenFiles.add(tempFile);
            }
        }

    }

    private static class FileBox {

        public File nowFile;
        public List<File> childrenFiles = null;
        public List<FileBox> childreList = new ArrayList<FileBox>();
        public int index;
        public FileBox(File file, int ind) {
            index = ind;
            nowFile = file;
        }

        public void print() throws Exception {
            System.out.println(coverToPic(index)+nowFile.getName());
            if( childrenFiles != null ) {
                for(File file : childrenFiles){
                    System.out.println(coverToPic(index+1)+file.getName());
                }
            }
            if(childreList.size() > 0 ){
                for(FileBox fileBox : childreList ){
                    fileBox.print();
                }
            }

        }

        public List<File> getAllFiles(){
            return getAllFiles(new ArrayList<File>());
        }

        public List<File> getAllFiles(List<File> fList){
            if( childrenFiles != null ) {
                for(File file : childrenFiles){
                    fList.add(file);
                }
            }
            if(childreList.size() > 0 ){
                for(FileBox fileBox : childreList ){
                    fileBox.getAllFiles(fList);
                }
            }
            return fList;
        }

        private final String coverToPic(int index){
            String result = concurrentMap.get(index);
            if(result == null ){
                String temp = "|";
                for(int i = 0 ; i< index ; i++) {
                    temp+="*";
                }
                concurrentMap.put(index, temp);
            }
            return concurrentMap.get(index);
        }
    }

}
