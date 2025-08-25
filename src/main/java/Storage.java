import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Storage {

    private String path;
    private TaskList taskListRef;

    private Storage(String path){
        this.path = path;
    }
    
    public static Storage initialiseStorage(String path) {
        return new Storage(path);
    }

    public TaskList readTaskList(){
        try{
          if(!new File(path).exists()) {
            TaskList saved = new TaskList();
            taskListRef = saved;
            return saved;
          }
          ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
          TaskList saved = (TaskList) in.readObject();
          this.taskListRef = saved;
          in.close();
          return saved;

        }catch(IOException e){
            System.err.println(e);
        }catch(ClassNotFoundException e){
            System.err.println(e);
        }
        return new TaskList(); 
    }

    public void writeTaskList(){
        // TODO: Implement this

        try{
        File save = new File(path);
        if(!save.exists()){
            save.createNewFile();
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(taskListRef);
        out.close();
        } catch(IOException e){
            System.err.println(e);
        } 

    }
}
