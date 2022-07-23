package com.suai.familyTree.io;

import com.suai.familyTree.model.Person;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FamilyReaderTest {

    @Test
    public void readFromTxt() {
        FamilyReader correct = new FamilyReader(new File("D:\\JavaProjects\\FamilyTreeWithTest\\src\\main\\java\\Balandyuk.txt"));

        List<Person> personList = correct.readFromTxt();
        assertEquals(7, personList.size());
        // if expected != 8 - test failed
    }
}