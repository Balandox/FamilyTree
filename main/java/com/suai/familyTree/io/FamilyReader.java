package com.suai.familyTree.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.suai.familyTree.model.Person;

public class FamilyReader {

    private File file;
    private Map<Integer, Person> idPersonMap;
    private Map<Integer, int[]> idRelationMap;

    public FamilyReader(File file){
        this.file = file;
        this.idPersonMap = new HashMap<>();
        this.idRelationMap = new HashMap<>();
    }

    public List<Person> readFromTxt(){

        List<Person> treeList = new ArrayList<>();
        // id firstName lastName middleName birthDate deathDate gender fatherId motherId spouseId
        int personId = -1, fatherId = -1, motherId = -1, spouseId = -1;
        String firstName = null, lastName = null, middleName = null, birthDate = null, deathDate = null, gender = null;

        int countOfArgs = 0;
        String tmpString = null;

        try(Scanner scanner = new Scanner(this.file)) {
            while(scanner.hasNext()){

                //if(scanner.hasNext()) {
                    switch (countOfArgs){
                        case 0:
                            tmpString = scanner.next();
                            if(!Objects.equals(tmpString, "null"))
                                personId = Integer.parseInt(tmpString);
                            break;
                        case 1:
                            firstName = scanner.next();
                            break;
                        case 2:
                            lastName = scanner.next();
                            break;
                        case 3:
                            middleName = scanner.next();
                            break;
                        case 4:
                            birthDate = scanner.next();
                            break;
                        case 5:
                            deathDate = scanner.next();
                            if(Objects.equals(deathDate, "null"))
                                deathDate = null;
                            break;
                        case 6:
                            gender = scanner.next();
                            break;
                        case 7:
                            tmpString = scanner.next();
                            if(!Objects.equals(tmpString, "null"))
                                fatherId = Integer.parseInt(tmpString);
                            break;
                        case 8:
                            tmpString = scanner.next();
                            if(!Objects.equals(tmpString, "null"))
                                motherId= Integer.parseInt(tmpString);
                            break;
                        case 9:
                            tmpString = scanner.next();
                            if(!Objects.equals(tmpString, "null"))
                                spouseId = Integer.parseInt(tmpString);
                            break;
                    }
                    countOfArgs++;

                if(countOfArgs == 10) {
                    Person curPerson = new Person(personId, firstName, lastName, middleName, birthDate, deathDate, gender);
                    this.idPersonMap.put(personId, curPerson);
                    this.idRelationMap.put(personId, new int[] {fatherId, motherId, spouseId});
                    treeList.add(curPerson);
                    scanner.nextLine();
                    fatherId = -1;
                    motherId = -1;
                    spouseId = -1;
                    countOfArgs = 0;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error! File not found.");
        }
        catch (InputMismatchException exception){
            System.out.println("Incorrect format of input data!");
        }

        setRelations(treeList);

        return treeList;
    }

    private void setRelations(List<Person> treeList){
        int personId = 0, fatherId = 0, motherId = 0, spouseId = 0;

        for(Person person : treeList) {
            personId = person.getPersonId();
            fatherId = this.idRelationMap.get(personId)[0];
            motherId = this.idRelationMap.get(personId)[1];
            spouseId = this.idRelationMap.get(personId)[2];

            if (fatherId != -1)
                person.setFather(this.idPersonMap.get(fatherId));
            if (motherId != -1)
                person.setMother(this.idPersonMap.get(motherId));
            if (spouseId != -1 && person.getSpouse() == null)
                person.setSpouse(this.idPersonMap.get(spouseId));

        }

    }


}
