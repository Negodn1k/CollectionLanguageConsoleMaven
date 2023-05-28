package org.example;

public class Main {
    public static void main(String[] args) {
        DataBase data = new DataBase();
        data.execute("INSERT VALUES LastName=Petrov, Id=1, Age=36, Count = 50");
        data.execute("INSERT VALUES LastName=Petrov, Id=1, Age=36, Count = 500");
        data.execute("INSERT LastName=vas, Id=2, Age=23, Count = 50");
        data.execute("SELECT WHERE Age>30");
    }
}