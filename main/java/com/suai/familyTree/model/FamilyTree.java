package com.suai.familyTree.model;

import java.io.File;
import com.suai.familyTree.model.Node;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.suai.familyTree.io.FamilyReader;
import com.suai.familyTree.io.FamilyWriter;

public class FamilyTree {

    private Person root;
    private List<Person> treeList;
    private List<Node> nodeList;

    public FamilyTree() {
        this.root = null;
        this.treeList = new ArrayList<>();
        this.nodeList = new ArrayList<>();
    }

    public Person findRoot() {
        return treeList.get(0);
    }

    public void sort() {
        this.treeList.sort(new AgeComparator());
    }

    public void loadFromTextFile(File file) {
        FamilyReader familyReader = new FamilyReader(file);
        this.treeList = familyReader.readFromTxt();
        this.sort();
        this.setRoot(this.findRoot());
        this.createNodes();
    }

    public void saveToTxtFile(File file) {
        FamilyWriter familyWriter = new FamilyWriter(file);
        familyWriter.writeToTxt(this.treeList);
    }

    public void clear() {
        this.getTreeList().clear();
        this.nodeList.clear();
        this.root = null;
    }

    public void createNodes() {

        int countNodeId = 0;
        Person curPerson = null;
        Node tmpNode = null;
        Node curNode = null;
        for (int i = 0; i < this.treeList.size(); i++) {
            curPerson = this.treeList.get(i);


            if (curPerson == this.root) {
                if (curPerson.has(Person.Attribute.CHILDREN)) {
                    countNodeId++;
                    curNode = new Node(curPerson, countNodeId, this);
                    curNode.curX = 300;
                    curNode.curY = 200;
                    this.nodeList.add(curNode);
                }
            } else { // новое поколение(уже другая семья)
                tmpNode = this.findNodeById(countNodeId);

                if (tmpNode.personNotInNode(curPerson) && (curPerson.has(Person.Attribute.CHILDREN))) {
                    countNodeId++;
                    curNode = new Node(curPerson, countNodeId, this);
                    curNode.curX = tmpNode.curX + Constants.X_STEP;
                    curNode.curY = tmpNode.curY + Constants.Y_STEP;
                    this.nodeList.add(curNode);
                }
            }
        }
        this.checkSpouseWithoutChild();
        this.setPersonsCoordinates();
    }


    public Node findNodeById(int id) {

        for (Node node : this.nodeList)
            if (node.getNodeId() == id)
                return node;
        return null;
    }

    private void checkSpouseWithoutChild() {
        for (Person person : this.treeList)
            if (person.has(Person.Attribute.SPOUSE) && person.getChildren().size() == 0)
                person.setSpouseWithoutChildren();
    }

    private void setPersonsCoordinates() {

        for (Node node : this.nodeList) {
            if (node.getFather() != null) {
                Person parent = node.getFather();

                parent.curX = node.curX - Constants.ICON_WIDTH;
                parent.curY = node.curY - Constants.ICON_HEIGHT;

                Person parentSpouse = parent.getSpouse();
                if (parentSpouse != null && parentSpouse.isSpouseWithoutChild()) {
                    parentSpouse.curX = node.curX + Constants.ICON_WIDTH;
                    parentSpouse.curY = node.curY - Constants.ICON_HEIGHT;
                }

            }
            if (node.getMother() != null) {
                Person parent = node.getMother();

                parent.curX = node.curX + Constants.NODE_WIDTH;
                parent.curY = node.curY - Constants.ICON_HEIGHT;

                Person parentSpouse = parent.getSpouse();
                if (parentSpouse != null && parentSpouse.isSpouseWithoutChild()) {
                    parentSpouse.curX = node.curX - Constants.ICON_WIDTH;
                    parentSpouse.curY = node.curY - Constants.ICON_HEIGHT;
                }
            }

            Person tempPerson;
            int t = (node.getChildren().size() * Constants.ICON_WIDTH) / 2;
            for (Person person : node.getChildren()) {

                Person personSpouse = person.getSpouse();
                if (personSpouse != null && personSpouse.isSpouseWithoutChild()) {
                    personSpouse.curX = node.curX - t + Constants.NODE_WIDTH / 2;
                    personSpouse.curY = node.curY + Constants.ICON_HEIGHT * 3;
                    t -= Constants.ICON_WIDTH;
                }


                if (person.curX == 0 && person.curY == 0) {
                    person.curX = node.curX - t + Constants.NODE_WIDTH / 2;
                    person.curY = node.curY + Constants.ICON_HEIGHT;
                    t -= Constants.ICON_WIDTH;
                }

            }
        }
    }

    public void removeAndFixNodes(Person person) {

        this.treeList.remove(person);

        if (person == root)
            this.setRoot(this.findRoot());

        for (Person personElem : this.treeList) {
            if (personElem.getChildren().remove(person))// удаляем как ребенка
                  personElem.getChildren().remove(person);


            if (personElem.getFather() == person)
                personElem.setNullFather();

            if (personElem.getMother() == person)
                personElem.setNullMother();

            if (personElem.getSpouse() == person)
                personElem.setNullSpouse();
        }

        this.nodeList.clear();
        this.createNodes();

    }

    private int findLastId() {
        int maxId = 0;

        for (Person person : this.treeList)
            maxId = Math.max(maxId, person.getPersonId());

        return maxId;
    }

    public Person[] getPersonsArray() {
        Person[] ArrayOfPersons = new Person[this.treeList.size() + 1];
        ArrayOfPersons[0] = new Person("", "", "");

        for (int i = 0; i < this.treeList.size(); i++)
            ArrayOfPersons[i + 1] = this.treeList.get(i);
        return ArrayOfPersons;
    }

    public Person[] getMaleSpouseArray() {
        List<Person> maleSpousePerson = new ArrayList<>();

        for (Person person : this.treeList)
            if (Objects.equals(person.getGender(), "male") && !person.has(Person.Attribute.SPOUSE))
                maleSpousePerson.add(person);

        Person[] maleSpouseArray = new Person[maleSpousePerson.size() + 1];
        maleSpouseArray[0] = new Person("", "", "");

        for (int i = 0; i < maleSpousePerson.size(); i++)
            maleSpouseArray[i + 1] = maleSpousePerson.get(i);
        return maleSpouseArray;
    }


    public Person[] getFemaleSpouseArray() {
        List<Person> femaleSpousePerson = new ArrayList<>();

        for (Person person : this.treeList)
            if (Objects.equals(person.getGender(), "female") && !person.has(Person.Attribute.SPOUSE))
                femaleSpousePerson.add(person);

        Person[] femaleSpouseArray = new Person[femaleSpousePerson.size() + 1];
        femaleSpouseArray[0] = new Person("", "", "");

        for (int i = 0; i < femaleSpousePerson.size(); i++)
            femaleSpouseArray[i + 1] = femaleSpousePerson.get(i);
        return femaleSpouseArray;
    }

    public Person[] getFemalePersonsArray() {
        List<Person> femalePerson = new ArrayList<>();

        for (Person person : this.treeList)
            if (Objects.equals(person.getGender(), "female"))
                femalePerson.add(person);

        Person[] femaleArray = new Person[femalePerson.size() + 1];
        femaleArray[0] = new Person("", "", "");

        for (int i = 0; i < femalePerson.size(); i++)
            femaleArray[i + 1] = femalePerson.get(i);
        return femaleArray;
    }

    public Person[] getMalePersonsArray() {
        List<Person> malePerson = new ArrayList<>();

        for (Person person : this.treeList)
            if (Objects.equals(person.getGender(), "male"))
                malePerson.add(person);

        Person[] maleArray = new Person[malePerson.size() + 1];
        maleArray[0] = new Person("", "", "");

        for (int i = 0; i < malePerson.size(); i++)
            maleArray[i + 1] = malePerson.get(i);
        return maleArray;
    }

    public Person.RelativeType[] getRelativeTypeArray() {
        return new Person.RelativeType[]{Person.RelativeType.FATHER, Person.RelativeType.MOTHER,
                Person.RelativeType.SPOUSE, Person.RelativeType.CHILD};
    }


    public void addNewPerson(Person person) {

        person.setPersonId(findLastId() + 1);

        this.treeList.add(person);
        this.sort();
        this.setRoot(this.findRoot());
        this.nodeList.clear();
        this.createNodes();

    }


    public void printCountOfNode() {
        System.out.println("Nodes: " + this.nodeList.size());
    }

    public void setRoot(Person newRoot) {
        this.root = newRoot;
    }

    public boolean hasRoot() {
        return this.root != null;
    }

    public Person getRoot() {
        return this.root;
    }

    public List<Node> getNodeList() {
        return this.nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public int[] getFurtherPoint() {
        int[] point = {0, 0};
        for (Person person : this.treeList) {
            point[0] = Math.max(point[0], person.curX);
            point[1] = Math.max(point[1], person.curY);
        }

        return point;
    }

    public List<Person> getTreeList() {
        return this.treeList;
    }


    class AgeComparator implements Comparator<Person> {
        @Override
        public int compare(Person a, Person b) {

            long agePersonLeft = a.getAge();
            long agePersonRight = b.getAge();

            return Long.compare(agePersonRight, agePersonLeft);
        }
    }
}
