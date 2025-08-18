
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

    public static Task newTask(int type, String[] details){
        switch(type){
            case 0 -> {
                return new TodoTask(details[0]);
            }
            case 1 -> {
                return new EventTask(details[0], details[1], details[2]);
            }
            case 2 -> {
                return new DeadlineTask(details[0], details[1]);
            }
            default -> throw new UnknownCommandException("");
        }
    }

    public void mark(){
        this.isDone = true;
    }

    public void unmark(){
        this.isDone = false;
    }

    private static class TodoTask extends Task{
        private TodoTask(String description){
            super(description);
        }
        @Override
        public String toString(){
            return "[T]" + super.toString();
        }
    }
    private static class EventTask extends Task{
        String from;
        String to;
        private EventTask(String description, String from, String to){
            super(description);
            this.from = from;
            this.to = to;
        }
        @Override
        public String toString(){
            return "[E]" + super.toString() + String.format(" (from: %s to: %s)", from, to);
        }
    }
    private static class DeadlineTask extends Task{
        String deadline;
        private DeadlineTask(String description, String deadline){
            super(description);
            this.deadline = deadline;
        }
        @Override
        public String toString(){
            return "[D]" + super.toString() + String.format(" (by %s)", deadline);
        }
    }


}
