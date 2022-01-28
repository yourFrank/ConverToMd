import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    //用来记录文件总数
    private static int count = 0;
    //用来临时存放文件内容
    private static List<String> contents;

    public static int convert(String path, ArrayList<File> list) throws Exception {
        FileUtil.getFiles(path, list, "java", "md");
        return count;
    }

    public static void getFiles(String path, ArrayList<File> list, String from, String to) throws Exception {
        //目标集合fileList
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                //如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath(), list, from, to);
                } else {
                    count++;
                    //如果文件是普通文件，则将文件句柄放入集合中
                    if (fileIndex.getName().endsWith(from)) {
                        contents=readFile(fileIndex); //读取文件临时保存内容
                        writeToFile(fileIndex,contents,"```java","```");//修改文件内容
                        convertTo(fileIndex, from, to);//转换文件格式
                        list.add(fileIndex); //保存修改文件个数
                    }


                }
            }
        }
    }

    //form后缀转换成to后缀文件
    /* 可在Java API中的File类中查询renameTo的方法
     * renameTo可以用来给File改名字,改路径
     * 他需要的参数也是一个File对象,表示要把当前文件重命名(移动)为哪个文件
     * 如果目标文件存在,则此方法返回false
     */
    public static void convertTo(File file, String from, String to) {
        String name = file.getName();
        file.renameTo(new File(file.getParent() + "/" + name.substring(0, name.indexOf(from)) + to));
    }

    /**
     * 逐行写入文件内容，第一行是指定的
     *
     * @param file
     * @param contents
     * @param firstLine
     * @throws IOException
     */
    public static void writeToFile(File file, List<String> contents, String firstLine,String lastLine) throws IOException {
//        File file = new File(fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        //写入第一行
        writer.write(firstLine + "\r\n");

        //写入列表，列表是原来读入的文件
        for (String content : contents) {
            writer.write(content);
            writer.write("\r\n");//写入换行符
        }

        //写入最后一行
        writer.write(lastLine + "\r\n");

        writer.close();

    }
    /**
     * 逐行读取文件，并得到一个列表
     * @param fileName
     * @return
     * @throws IOException
     */
    public static List<String> readFile(File fileName) throws IOException{
        List<String> result = new ArrayList<String>();
        FileInputStream is = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String content = null;
        while ((content = reader.readLine()) != null) {
            result.add(content);
        }

        reader.close();
        return result;
    }
}
