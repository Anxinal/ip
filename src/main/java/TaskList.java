import java.util.ArrayList;

public class TaskList {
    
    private ArrayList<Task> tasks;
    public TaskList(){
        tasks = new ArrayList<>(100);
    }
    public Task add(String taskName){
        Task toAdd = Task.newTask(0,new String[]{taskName});
        tasks.add(toAdd);
        return toAdd;
    }

    public Task add(int taskType, String[] details){
        Task toAdd = Task.newTask(taskType,details);
        tasks.add(toAdd);
        return toAdd;
    }

    public String switchTaskStatus(int index, boolean done){
        if(done) tasks.get(index - 1).mark();
        if(!done) tasks.get(index - 1).unmark();  
        return this.tasks.get(index - 1).toString();
    }

    public String toString(){
        String items = "";
        for(int i = 0; i < tasks.size(); i++){
            items += (i + 1) + ". " + tasks.get(i).toString() + "\n";
        }
        return items;
    }
    public int getSize(){
        return tasks.size();
    }
    
    // returns the number of elements left in the list
    public Task delete(int index){
        return tasks.remove(index - 1);
    }
}
