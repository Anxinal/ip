package shiroha.command;

import org.junit.jupiter.api.Test;

import shiroha.tasks.TaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;

public class ParserTest {

    @Disabled
    public void addTest1(){
        Parser test = new Parser(new TaskList());
        assertEquals(test.taskList, getClass());
    }
}
