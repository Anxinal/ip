public class Task {
    private String description;
    private boolean isDone;

    public String toString(){
        String finishStat = isDone ? "X" : " ";
        return "["+ finishStat +"]" + " " + description;
    }

    private Task(String description){
        this.description = description;
        this.isDone = false;
    }

    public static Task newTask(String description){
        return new Task(description);
    }

    public void mark(){
        this.isDone = true;
    }

    public void unmark(){
        this.isDone = false;
    }

}
