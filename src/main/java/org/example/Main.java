package org.example;

public class Main {
    public static void main(String[] args) throws Exception{
        DataBase data = new DataBase();
        data.execute("INSERT LastName=Petrov, Id=228");
    }
}