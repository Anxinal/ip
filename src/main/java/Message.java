
public class Message {

    private String content = "";
    public Message(String content){
        this.content = content;     
    }
    public boolean checkCommand(){
        return this.content.equals("bye");
    }

    class ListItem extends Message {
        
        ListItem(String content){
            super(content);
        }
    }

}
