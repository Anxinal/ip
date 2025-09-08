package shiroha;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;

public class Storage {

    private String path;
    private TaskList taskListRef;

    private Storage(String path){
        this.path = path;
    }


    /**
     * Initialises the storage system for the chatbot
     * @param path The path to the file to read from and write to
     * @return the storage object
     */
    public static Storage initialiseStorage(String path) {
        return new Storage(path);
    }


    /**
     * Reads the task list from the file specified in the path, throws UnknownCommandException if the file is corrupted
     * If the file does not exist or is empty, returns a new task list
     * @return The task list read from the file, or a new task list if the file does not exist or is empty
     */
    public TaskList readTaskList(){
        
        try{
          if(!new File(path).exists()) {
            TaskList saved = new TaskList();
            taskListRef = saved;
            return saved;
          }
          ObjectInputStream reader = new ObjectInputStream(new FileInputStream(path));
          TaskList saved = (TaskList) reader.readObject();
          this.taskListRef = saved;
          reader.close();
          return saved;

        } catch(IOException e){
            throw new UnknownCommandException("This file is already too hard to read so I will start with a new task list");
        } catch(ClassNotFoundException e){
            System.err.println(e);
        }
        return new TaskList(); 
    }
    
    /**
     * Writes the current task list to the file specified in the path, creates the file if it does not exist
     */
    public void writeTaskList(){

        try {

            File save = new File(path);
            if(!save.exists()){
                save.createNewFile();
           }

            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(path));
            writer.writeObject(taskListRef);
            writer.close();
            } catch (IOException e) {
                System.err.println(e);
            } 

    }
}
