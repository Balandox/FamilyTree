package com.suai.familyTree.model;

import com.suai.familyTree.io.FamilyReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FamilyTreeTest {

    FamilyTree tree;


    @BeforeEach
    public void init() {
        tree = new FamilyTree();
        tree.loadFromTextFile(new File("D:\\JavaProjects\\FamilyTreeWithTest\\src\\main\\java\\Balandyuk.txt"));
    }

    @Test
    public void sort() {
        // tree call sort() in loadFromTextFile method
        Person rootExpected = tree.getRoot();
        Person rootActual = tree.getTreeList().get(0);
        assertEquals(rootExpected, rootActual);
    }


    @Test
    public void clear() {
        assertEquals(7, tree.getTreeList().size());
        assertEquals(3, tree.getNodeList().size());

        tree.clear();

        assertEquals(0, tree.getTreeList().size());
        assertEquals(0, tree.getNodeList().size());
    }

    @Test
    public void createNodes() {
        // tree call createNode() in loadFromTextFile method
        assertEquals(3, tree.getNodeList().size());
    }

    @Test
    public void findNodeById() {
    Node node = tree.getNodeList().get(0);
    assertEquals(node, tree.findNodeById(node.getNodeId()));
    }

    @Test
    public void removeAndFixNodes() {
        tree.removeAndFixNodes(tree.getRoot());
        assertEquals(6, tree.getTreeList().size());
        assertEquals(2, tree.getNodeList().size());

    }



    @Test
    public void getPersonsArray() {
        int sizeOfPersonList = tree.getTreeList().size();
        int sizeOfPersonArray = tree.getPersonsArray().length - 1;
        assertEquals(sizeOfPersonList, sizeOfPersonArray);
    }

    @Test
    public void getFemalePersonsArray(){

        int sizeOfFemalePersonsArray = tree.getFemalePersonsArray().length - 1;
        assertEquals(4, sizeOfFemalePersonsArray);
    }

    @Test
    public void getMalePersonsArray(){
        int sizeOfMalePersonsArray = tree.getMalePersonsArray().length - 1;
        assertEquals(3, sizeOfMalePersonsArray);
    }

    @Test
    public void getRelativeTypeArray(){
        int sizeOfRelativeTypeArray = tree.getRelativeTypeArray().length;
        assertEquals(4, sizeOfRelativeTypeArray);

    }

    @Test
    public void getFurtherPoint() {

        int[] result = tree.getFurtherPoint();
        Person lastAddedPerson = tree.getTreeList().get(tree.getTreeList().size() - 1);
        assertEquals(lastAddedPerson.curY, result[1]);
    }

    @Test
    public void addNewPerson() {
        Person p = new Person();
        tree.addNewPerson(p);

        assertEquals(8, tree.getTreeList().size());
        assertEquals(3, tree.getNodeList().size());

    }


}