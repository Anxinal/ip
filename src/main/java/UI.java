import java.util.Scanner;

public class UI {

    Scanner s = new Scanner(System.in);

    public void renderChatBotMessage(String s){
        addLineBreak();
        System.out.println(s);
        addLineBreak();
    }
    public void renderErrorMessage(Exception err){
        addLineBreak();
        System.out.println(err.getMessage());
        addLineBreak();
    }

    private void addLineBreak(){
        System.out.println("--------(-w-)---------");
    }

    public String getNextInput(){
        return this.s.nextLine();
    }

    public void close(){
        this.s.close();
    }

}
