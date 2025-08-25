import java.util.Scanner;

public class UI {

    Scanner s = new Scanner(System.in);

    /**
     * Renders a message from the chatbot to the user with line breaks before and after the message
     * @param s The message to be rendered
     */
    public void renderChatBotMessage(String s){
        addLineBreak();
        System.out.println(s);
        addLineBreak();
    }
    /**
     * Renders an error message to the user with line breaks before and after the message
     * @param err The exception containing the error message to be rendered
     */
    public void renderErrorMessage(Exception err){
        addLineBreak();
        System.out.println(err.getMessage());
        addLineBreak();
    }

    private void addLineBreak(){
        System.out.println("--------(-w-)---------");
    }
    /**
     * Gets the next line of input from the user
     * @return The next line of input from the user
     */
    public String getNextInput(){
        return this.s.nextLine();
    }

    /**
     * Closes the scanner
     */
    public void close(){
        this.s.close();
    }

}
