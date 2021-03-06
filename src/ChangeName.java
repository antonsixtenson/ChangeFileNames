import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangeName {
    Scanner scanner = new Scanner(System.in);
    Runtime run = Runtime.getRuntime();

    public void getFiles() throws Exception{
        System.out.println("What file-ending does the files have? ");
        String fileEnding = scanner.nextLine();

        // add "." if not included with input
        if(!fileEnding.startsWith(".")){
            fileEnding = "."+fileEnding;
        }
        // run command ls
        Process pr = run.exec("ls");
        BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        ArrayList<String> filesNdirs = new ArrayList<>();
        int k = 0;
        while((line = br.readLine()) != null){
            if(k != 0) {
                if (line.endsWith(fileEnding)) {
                    filesNdirs.add(line);
                }
            } else {
                k++;
            }
        }

        for(int i = 0; i < filesNdirs.size(); i++){
            System.out.println(filesNdirs.get(i));
        }

        System.out.println("You want to change name of these " + filesNdirs.size() + " files [y/n]: ");
        String ans = scanner.nextLine().toLowerCase();
        if(ans.equals("y")){
            changeNames(filesNdirs);
        } else {
            System.out.println("Okay, bye!");
        }
    }

    public void changeNames(ArrayList<String> files){
        for(int i = 0; i < files.size(); i++){
            System.out.println("File " + i + " " + files.get(i));
        }
        System.out.println("(The names will be in format: newName_number) Input newName: ");
        String newName = scanner.nextLine();
        int end = files.get(0).indexOf(".");
        String fileending = files.get(0).substring(end);
        for(int i = 0; i < files.size(); i++){
            try {
                run.exec("mv " + files.get(i) + " " + newName + "_" + i + fileending);
            } catch (Exception e){
                System.out.println(e + " at index " + i);
            }
        }
    }

    public static void main(String[] args) {
        ChangeName cn = new ChangeName();
        try {
            cn.getFiles();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
