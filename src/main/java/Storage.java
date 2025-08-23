import java.util.Scanner;

public class Storage {

    private String path;

    private Storage(String path){
        this.path = path;
    }
    
    public static Storage initialiseStorage(String path) {
        return new Storage(path);
    }

    public TaskList readTaskList(){
        return new TaskList();
    }

    public void writeTaskList(){
        // TODO: Implement this

    }
}
