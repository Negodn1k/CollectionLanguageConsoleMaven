package org.example;

public class Main {
    public static void main(String[] args) throws Exception{
        DataBase data = new DataBase();
        data.execute("INSERT LastName=Petrov, Id=1, Age=36, Count = 150");
        data.execute("INSERT LastName=vas, Id=2, Age=, Count = 150");
        data.execute("INSERT LastName=vasilii, Id=2, Age=, Count = 150");
        data.execute("INSERT LastName=vas, Id=3, Age=10, Count = 150");
        data.execute("INSERT LastName=vas, Id=2, Age=15, Count = 150");
        data.execute("INSERT LastName=vas, Id=5, Age=, Count = 150");
        data.execute("INSERT LastName=vas, Id=2, Age=, Count = 150");
        data.execute("INSERT LastName=vas, Id=25, Age=, Count = 150");
        data.execute("DELETE Id = 1");
    }
}