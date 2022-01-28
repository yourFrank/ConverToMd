import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @Description TODO
 * @Author 71042
 * @Date 2022/1/28 14:33
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要修改的文件/文件夹路径:");
        String path = sc.nextLine();

        ArrayList<File> fileList = new ArrayList<File>();
        int count = FileUtil.convert(path, fileList);
        System.out.println("总共检测到"+count+"个文件, "+"已经成功为您转换"+fileList.size()+"个文件");

    }


}
