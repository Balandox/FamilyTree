package com.suai.familyTree.model;

import com.suai.familyTree.view.TreeListener;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class Person {

    private int personId;
    private String firstName;
    private String middleName; // info about person
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private long age;
    private String gender;
    private String photo;

    private Person father;
    private Person mother;            // relation
    private Person spouse;          // супруг
    private ArrayList<Person> children;


    public volatile int curX = 0;
    public volatile int curY = 0;
    private boolean spouseWithoutChild = false;



    /**
     * Attribute types used to check if a family member has any of these attributes
     */
    public enum Attribute {
        FATHER,
        MOTHER,
        CHILDREN,
        SPOUSE,
        PARENTS;
    }

    /**
     * Relative types used to add relatives to a family member
     */
    public enum RelativeType {
        FATHER,
        MOTHER,
        CHILD,
        SPOUSE;
    }



    public Person(int personId, String firstName, String lastName,String middleName,
                  String birthDate, String deathDate, String gender){

        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = LocalDate.parse(birthDate);

        if(deathDate != null)
            this.deathDate = LocalDate.parse(deathDate);
        else
            this.deathDate = null;

        this.gender = gender;

        if(Objects.equals(this.gender, "male"))
            this.photo = Constants.PHOTO_DIR + "maleSign.png";
        else
            this.photo = Constants.PHOTO_DIR + "femaleSign.png";

        this.age = ChronoUnit.YEARS.between(this.birthDate, LocalDate.now());


        this.mother = null;
        this.father = null;
        this.spouse = null;
        this.children = new ArrayList<>();
    }

    public Person() {
        this.personId = -1;
        this.firstName = "";
        this.lastName = "";
        this.middleName = "";
        this.photo = "";
        this.birthDate = null;
        this.deathDate = null;
        this.age = 0;
        this.gender = null;

        this.father = null;
        this.mother = null;
        this.spouse = null;
        this.children = new ArrayList<>();

    }

    public Person(String firstName, String lastName, String middleName) {
        this.personId = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.photo = "";
        this.birthDate = null;
        this.deathDate = null;
        this.age = 0;
        this.gender = null;

        this.father = null;
        this.mother = null;
        this.spouse = null;
        this.children = new ArrayList<>();
    }

    public Person(Person person) {
        this.personId = person.personId;
        this.firstName = person.firstName;
        this.lastName = person.lastName;
        this.middleName = person.middleName;
        this.birthDate = person.birthDate;
        this.deathDate = person.deathDate;
        this.father = person.father;
        this.mother = person.mother;
        this.spouse = person.spouse;
        this.photo = person.photo;
        this.children = new ArrayList<>();
        this.age = person.age;
        this.curX = person.curX;
        this.curY = person.curY;
    }


    public boolean isSpouseWithoutChild() {
        return spouseWithoutChild;
    }

    public void setSpouseWithoutChildren(){
        this.spouseWithoutChild = true;
    }

    @Override
    public String toString() {
        //displays a nice string representation of a perosn. the () means they have
        //a maiden name and it uses the gender symols to identify them
        String s = null;
        if (Objects.equals(this.gender, "male")){
            s = "♂ ";
        }else if (Objects.equals(this.gender, "female")){
            s = "♀ ";
        }
        s += this.getFirstName() + " " + this.getMiddleName() + " " + this.getLastName();
        return s;
    }


    public Person getMother() {
        return this.mother;
    }

    public Person getFather() {
        return this.father;
    }

    public Person getSpouse() {
        return this.spouse;
    }

    public ArrayList<Person> getChildren() {
        return this.children;
    }

    public int getPersonId(){return this.personId;}

    public String getGender() {
        return this.gender;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public LocalDate getBirthDate(){return this.birthDate;}

    public LocalDate getDeathDate(){return this.deathDate;}

    public String getFullName() {
        return this.lastName + " " + this.firstName + " " + this.middleName;
    }

    public int getFatherId(){
        if(this.father != null)
            return this.father.getPersonId();
        else
            return -1;
    }

    public int getMotherId(){
        if(this.mother != null)
            return this.mother.getPersonId();
        else
            return -1;
    }

    public int getSpouseId(){
        if(this.spouse != null)
            return this.spouse.getPersonId();
        else
            return -1;
    }

    public String getPhoto() {
        return this.photo;
    }

    public long getAge(){return this.age;}

    public String getShortName() {
        StringBuilder result = new StringBuilder();
        if (firstName.length() >= 1) {
            result.append(firstName.charAt(0)).append(". ");
        }
        if (middleName.length() >= 1) {
            result.append(middleName.charAt(0)).append(".");
        }
        return lastName + " " + result;
    }


    public void setPhoto() {
        if(Objects.equals(this.gender, "male"))
            this.photo = Constants.PHOTO_DIR + "maleSign.png";
        else
            this.photo = Constants.PHOTO_DIR + "femaleSign.png";
    }

    public void setPersonId(int id){ this.personId = id; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setBirthDate(String birthDate){this.birthDate = LocalDate.parse(birthDate);}

    public void setDeathDate(String deathDate){
        if(!Objects.equals(deathDate, "null"))
            this.deathDate = LocalDate.parse(deathDate);
        else
            this.deathDate = null;
    }

    public void setAge(){this.age = ChronoUnit.YEARS.between(this.birthDate, LocalDate.now());}

    public void setGender(String gender){ this.gender = gender;}


    /**
     * adds a child to the family member. Consequently, adding the spouse and the current
     * family member as the parents if they exist
     * @param child the child to add to set of children
     */



    public void addChild(Person child) {
        //father
        if (Objects.equals(this.gender, "male")) {
            //if the child doesn't have a father set it
            if (!child.has(Attribute.FATHER)) {
                child.setFather(this);
            }
            //if the family member has a spouse set it as the mother
            if (this.has(Attribute.SPOUSE)) {
                if (!child.has(Attribute.MOTHER)) { // если у this есть супруга и у ребенка нет матери, то проставляем ребенку мать супругу this
                    child.setMother(this.getSpouse());
                }
            }
            //mother
        }else if (Objects.equals(this.gender, "female")){
            //if the child doesn't have a mother set it
            if (!child.has(Attribute.MOTHER)) {
                child.setMother(this);
            }
            //if the family member has a spouse set it as the father
            if (this.has(Attribute.SPOUSE)) {
                if (!child.has(Attribute.FATHER)) {
                    child.setFather(this.getSpouse());
                }
            }
        }
        //make sure no duplicate children objects
        if(!this.getChildren().contains(child)){
            this.getChildren().add(child);
        }

    }


    public int numOfChildren(){
        return this.getChildren().size();
    }


    /**
     * sets the mother and makes sure it is a female. it also adds the current m
     * member as a child to the mother
     * A member can only have one mother
     * @param mother the mother to set
     */
    public void setMother(Person mother) {

            if (!this.has(Attribute.MOTHER)) {
                if (Objects.equals(mother.getGender(), "female")) {
                    if (!mother.getChildren().contains(this)) {
                        mother.getChildren().add(this);
                    }
                    this.mother = mother;

                } else {
                    throw new IllegalArgumentException("Mother can only be female");
                }

            } else {
                throw new IllegalArgumentException("Mother already added");
            }
    }



    /**
     * sets the father and makes sure it is a male. it also adds the current
     * member as a child to the father
     * A member can only have one father
     * @param father the father to set
     */
    public void setFather(Person father) {
        if (!this.has(Attribute.FATHER)) {
            if (Objects.equals(father.getGender(), "male")) {
                if (!father.getChildren().contains(this)){
                    father.getChildren().add(this);
                }
                this.father = father;


            }else{
                throw new IllegalArgumentException("Father can only be male");
            }

        }else{
            throw new IllegalArgumentException("Father already added");
        }

    }

    public void setNullSpouse(){
        this.spouse = null;
    }

    public void setNullFather(){
        this.father = null;
    }

    public void setNullMother(){
        this.mother = null;
    }


    /**
     * sets the spouse of the member. a spouse must be the opposite gender and a
     * member can have only one spouse
     * @param spouse the spouse to set
     */
    public void setSpouse(Person spouse) {
        if (!this.has(Attribute.SPOUSE)) {
            if(!Objects.equals(spouse.getGender(), this.getGender())){
                //if(this.getChildren().size() != 0)
                    spouse.setChildren(this.getChildren());
                this.spouse = spouse;
                if (!this.getSpouse().has(Attribute.SPOUSE)) {
                    spouse.setSpouse(this);
                }
            }else{
                throw new IllegalArgumentException("Spouse can only be opposite gender");
            }
        }else{
            throw new IllegalArgumentException("Spouse already exists");
        }

    }


    /**
     * @param children the children to set
     */
    public void setChildren(ArrayList<Person> children) {
        this.children = children;
    }

    /**
     * checks if the member has a specific type of attribute
     * @param type the attribute type to check
     * @return true if the conditions are met
     */
    public boolean has(Person.Attribute type){
        switch(type){
            case FATHER:
                return this.getFather() != null;
            case CHILDREN:
                return !this.getChildren().isEmpty();
            case MOTHER:
                return this.getMother() != null;
            case SPOUSE:
                return this.getSpouse() != null;
            case PARENTS:
                return this.has(Attribute.FATHER) || this.has(Attribute.MOTHER);
        }
        return false;
    }

    /**
     * adds a relative based on the specified type variable. Basically a convenience method
     * @param type the type of the added member
     * @param member the member to add
     */
    public void addRelative(Person.RelativeType type, Person member){
        switch(type){
            case FATHER:
                this.setFather(member);
                return;
            case CHILD:
                this.addChild(member);
                return;
            case MOTHER:
                this.setMother(member);
                return;
            case SPOUSE:
                this.setSpouse(member);
                return;
        }
    }

}

