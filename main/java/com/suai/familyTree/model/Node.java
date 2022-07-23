package com.suai.familyTree.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Node {

    private int id;

    private FamilyTree familyTree;


    private List<Person> nodeChilds;
    private Person father = null;
    private Person mother = null;

    public volatile int curX;
    public volatile int curY;



    public Node(Person curPerson, int id, FamilyTree familyTree) {
        this.id = id;
        this.nodeChilds = new ArrayList<>();
        //this.nodeList = new ArrayList<>();
        this.familyTree = familyTree;

        if(Objects.equals(curPerson.getGender(), "male"))
            this.father = curPerson;
        else
            this.mother = curPerson;

        if(this.father != null)
            this.mother = curPerson.getSpouse();
        else if(this.mother != null)
            this.father = curPerson.getSpouse();

        this.nodeChilds = curPerson.getChildren();
        //this.maxAgeInNode = findMaxAge();
    }

    public Node(Person person, FamilyTree familyTree) {
        this.nodeChilds = new ArrayList<>();
        List<Node> newNodeList = new ArrayList<>();
        this.familyTree = familyTree;
        this.familyTree.setNodeList(newNodeList);

        this.nodeChilds.add(person);
        this.father = person.getFather();
        this.mother = person.getMother();
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getNodeId(){return this.id; }

    public Person getFather() { return this.father; }

    public Person getMother() { return this.mother; }

    public List<Person> getChildren() { return this.nodeChilds; }

    public boolean personNotInNode(Person person){

        boolean personIsChild = false;
        for(Person tmpPerson : this.nodeChilds)
            if(tmpPerson == person)
                personIsChild = true;

        if(this.father != person && this.mother != person && !personIsChild)
            return true;
        else
            return false;
    }


    public void add(Person person) {
        this.nodeChilds.add(person);
    }

    public void add(Node node) {
        this.familyTree.getNodeList().add(node);
    }





}
