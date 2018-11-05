package com.project.java.presentation.tools.oca.StackBeispielTest;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by john on 27.10.2015.
 */
public class StackTest {

    @Test
    public void testCreation(){
        final Stack<String> stack = new Stack<String>(2);
        stack.push("one");
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        stack.push("two");
        assertFalse(stack.isEmpty());
        assertFalse(stack.isFull());
        stack.push("three");
    }
}
