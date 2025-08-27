package shiroha;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;


public class StorageTest {

    @Test 
    public void readTest() {
        // The path does not exist
        Storage test = Storage.initialiseStorage("../data/randomfile.txt");
        assertEquals(new TaskList(), test.readTaskList());
    }

    @Test 
    public void writeFileTest() {

        String ref = "./src/test/testData/memory.mem";
        Storage test = Storage.initialiseStorage(ref);
        TaskList list = new TaskList();
        list.add("read book");
        list.add("play game");
        test.writeTaskList();
        assertTrue(new File(ref).exists());

    }

    @Test
    public void readCorruptedFileTest() {
        
        String ref = "./src/test/testData/memorycorrupted.mem";
        Storage test = Storage.initialiseStorage(ref);
        assertThrows(UnknownCommandException.class, () -> {
            test.readTaskList();
        });
    }

}
