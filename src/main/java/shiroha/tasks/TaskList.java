package shiroha.tasks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;

import shiroha.tasks.Task.TaskType;

public class TaskList implements Serializable{
    
    private ArrayList<Task> tasks;
    //for version control in case different serial IDs are implemented
    private static final long serialVersionUID = 1000;

    public TaskList(){
        tasks = new ArrayList<>(100);
    }
    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if(!(other instanceof TaskList)) return false;
        TaskList o = (TaskList) other;
        return this.tasks.equals(o.tasks);
    }

    /**
     * Adds a new task to the task list，when no extra details are needed
     * @param taskName The name of the todo task to add
     * @return The added task
     */
    public Task add(String taskName){
        Task toAdd = Task.newTask(Task.TaskType.TODO,new String[]{taskName});
        tasks.add(toAdd);
        return toAdd;
    }
    /**
     * Adds a new task to the task list, when extra details are needed
     * @param taskType The type of task to add (0 = todo, 1 = event, 2 = deadline)
     * @param details The details of the task to add
     * @return The added task
     */
    public Task add(Task.TaskType taskType, String[] details){
        Task toAdd = Task.newTask(taskType, details);
        tasks.add(toAdd);
        return toAdd;
    }

    /**
     * Marks or unmarks the task at the given index
     * @param index The index of the task to mark or unmark (1-based)
     * @param done true to mark the task, false to unmark the task
     * @return The string representation of the marked or unmarked task
     */
    public String switchTaskStatus(int index, boolean done){
        if(done) tasks.get(index - 1).mark();
        if(!done) tasks.get(index - 1).unmark();  
        return this.tasks.get(index - 1).toString();
    }
    /**
     * Returns the string representation of the task list
     * @return The string representation of the task list
     */
    public String toString(){
        String items = "";
        for(int i = 0; i < tasks.size(); i++){
            items += (i + 1) + ". " + tasks.get(i).toString() + "\n";
        }
        return items;
    }
    /**
     * Returns the number of tasks in the task list
     * @return The number of tasks in the task list
     */
    public int getSize(){
        return tasks.size();
    }
    
    /**
     * Deletes the task at the given index from the task list
     * @param index The index of the task to delete (1-based)
     * @return The deleted task
     */
    public Task delete(int index){
        return tasks.remove(index - 1);
    }
    /**
     * Filters the task list based on the given condition
     * @param condition The condition to filter the tasks
     * @return A new task list containing only the tasks that satisfy the condition
     */
    public TaskList filter(Predicate<Task> condition){
        TaskList filtered = new TaskList();
        
        for(Task t: this.tasks){
            if(condition.test(t)){
                filtered.tasks.add(t);
            }
        }
        return filtered;
    }


}
