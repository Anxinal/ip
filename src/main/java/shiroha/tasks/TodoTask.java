package shiroha.tasks;

public class TodoTask extends Task{

        protected TodoTask(String description){
            super(description);
        }

        @Override
        public String toString(){
            return "[T]" + super.toString();
        }
        
}

