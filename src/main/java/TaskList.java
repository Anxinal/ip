import java.util.ArrayList;

public class TaskList {
    
    private ArrayList<Task> tasks;
    public TaskList(){
        tasks = new ArrayList<>(100);
    }
    public void add(String taskName){
        tasks.add(Task.newTask(taskName));
    }

    public String toString(){
        String items = "";
        for(int i = 0; i < list.size(); i++){
            items += i + ". " + tasks.get(i).toString() + "\n";
        }
        return items;
    }
    
}
