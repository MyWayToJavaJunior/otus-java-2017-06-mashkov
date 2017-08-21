package ru.otus.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users2")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    String name;
    @Column(name = "age", nullable = false)
    int age;

    public UserDataSet(){

    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
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
