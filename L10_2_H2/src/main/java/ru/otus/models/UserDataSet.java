package ru.otus.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AddressDataSet addres;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    public UserDataSet(){

    }

    public UserDataSet(String name, int age, AddressDataSet addres) {
        this.name = name;
        this.age = age;
        this.addres = addres;
        this.phones = new ArrayList<>();
    }

    public AddressDataSet getAddres() {
        return addres;
    }

    public void setAddres(AddressDataSet addres) {
        this.addres = addres;
    }

    public void addPhone(PhoneDataSet phone){
        phones.add(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDataSet)) return false;

        UserDataSet dataSet = (UserDataSet) o;

        if (age != dataSet.age) return false;
        if (id != dataSet.id) return false;
        return name != null ? name.equals(dataSet.name) : dataSet.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}
