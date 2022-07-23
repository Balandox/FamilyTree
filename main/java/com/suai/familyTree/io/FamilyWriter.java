package com.suai.familyTree.io;

import com.suai.familyTree.model.Person;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FamilyWriter {

    private File file;

    public FamilyWriter(File file){
        this.file = file;
    }

    public void writeToTxt(List<Person> treeList){
        // id firstName lastName middleName birthDate deathDate gender fatherId motherId spouseId

        try {
            FileWriter fileWriter = new FileWriter(this.file);

            BufferedWriter buffer = new BufferedWriter(fileWriter);

            String deathDate = null, fatherId = null, motherId = null, spouseId = null;

            for(Person person : treeList){
                if(person.getDeathDate() == null)
                    deathDate = "null";
                else
                    deathDate = person.getDeathDate().toString();

                if(person.getFatherId() == -1)
                    fatherId = "null";
                else
                    fatherId = String.valueOf(person.getFatherId());

                if(person.getMotherId() == -1)
                    motherId = "null";
                else
                    motherId = String.valueOf(person.getMotherId());

                if(person.getSpouseId() == -1)
                    spouseId = "null";
                else
                    spouseId = String.valueOf(person.getSpouseId());

                buffer.write(person.getPersonId() + " " + person.getFirstName() + " " + person.getLastName()
                + " " + person.getMiddleName() + " " + person.getBirthDate() + " " + deathDate
                + " " + person.getGender() + " " + fatherId + " " + motherId + " " + spouseId + "\n");

            }
            buffer.close();
        }
        catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

}
