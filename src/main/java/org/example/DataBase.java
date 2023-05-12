package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    public List<Map<String, Object>> execute(String request) {
        StringBuilder action = new StringBuilder();
        StringBuilder value = new StringBuilder();

        for (int i = 0; i < request.length(); i++) {
            if (i < 6) {
                action.append(request.charAt(i));
            } else {
                value.append(request.charAt(i));
            }
        }

        switch (action.toString()) {
            case "INSERT" -> insertValues(value.toString());
            case "UPDATE" -> updateValues(value.toString());
            case "DELETE" -> deleteValues(value.toString());
            case "SELECT" -> selectValues(value.toString());
            default -> System.out.println("Wrong Request");
        }
        return new ArrayList<>();
    }

    private void insertValues(String value) {
        Map<String, Object> row = new HashMap<>();
        StringBuilder key = new StringBuilder();
        StringBuilder mapValue = new StringBuilder();
        value = value.replaceAll(" ", "");

        for (int i = 0; i != value.length(); i++) {
            if (value.charAt(i) == '=') {
                i++;
                while (value.charAt(i) != ',') {
                    mapValue.append(value.charAt(i));
                    i++;
                    if (i == value.length()) {
                        break;
                    }
                }
                i--;
            } else if (value.charAt(i) == ',') {
                row.put(key.toString(), mapValue.toString());
                key.delete(0, 100);
                mapValue.delete(0, 100);
            } else {
                key.append(value.charAt(i));
            }
        }
        row.put(key.toString(), mapValue.toString());

        System.out.println(row.keySet() +"\n" + row.values());
    }

    private void updateValues(String value) {

    }

    private void deleteValues(String value) {

    }

    private void selectValues(String value) {

    }
}
