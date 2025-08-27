package shiroha.tasks;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskListTest {
    @Test
    public void addTaskTest1(){

        TaskList test = new TaskList();
        test.add("read book");

        TaskListStub stub = new TaskListStub();
        stub.add("read book");

        assertEquals(stub.toString(), test.toString());
    }

    @Test
    public void addTaskTest2(){
        TaskList test = new TaskList();
        test.add("read book");
        test.add(2, new String[]{"return book", "2025-08-30"});
        test.add(1, new String[]{"project meeting", "2025-09-01", "2025-09-02"});

        TaskListStub stub = new TaskListStub();
        stub.add("read book");
        stub.add(2, new String[]{"return book", "2025-08-30"});
        stub.add(1, new String[]{"project meeting", "2025-09-01", "2025-09-02"});
        assertEquals(stub.toString(), test.toString());
    }

    @Test
    public void markTaskTest(){
        TaskList test = new TaskList();
        Task todo = test.add("read book");
        Task deadline = test.add(2, new String[]{"return book", "2025-08-30"});
        Task event = test.add(1, new String[]{"project meeting", "2025-09-01", "2025-09-02"});
        test.switchTaskStatus(2, true);
        String expected = "";
        expected += "1. " + todo.toString() + "\n";
        expected += "2. " + deadline.toString().replace("[ ]", "[X]") + "\n";
        expected += "3. " + event.toString() + "\n";
        assertEquals(expected, test.toString());
        test.switchTaskStatus(2, false);
        expected = "";
        expected += "1. " + todo.toString() + "\n";
        expected += "2. " + deadline.toString().replace("[ ]", "[X]") + "\n";
        expected += "3. " + event.toString() + "\n";
    }


}
