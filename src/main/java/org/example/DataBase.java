package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    List<Map<String, Object>> list = new ArrayList<>();

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
        return list;
    }

    private void insertValues(String value) {
        Map<String, Object> row = new HashMap<>();
        StringBuilder key = new StringBuilder();
        StringBuilder mapValue = new StringBuilder();
        value = value.replaceAll(" ", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll("WHERE", "");
        value = value.replaceAll("VALUES", "");

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
                try {
                    row.put(key.toString(), Integer.parseInt(mapValue.toString()));
                } catch (NumberFormatException e) {
                    row.put(key.toString(), mapValue.toString());
                }
                key.delete(0, 100);
                mapValue.delete(0, 100);
            } else {
                key.append(value.charAt(i));
            }
        }

        try {
            row.put(key.toString(), Integer.parseInt(mapValue.toString()));
        } catch (NumberFormatException e) {
            row.put(key.toString(), mapValue.toString());
        }
        list.add(row);
    }

    private void updateValues(String value) {
        value = value.replaceAll(" ", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll("VALUES", "");
        StringBuilder key = new StringBuilder();
        StringBuilder mapValue = new StringBuilder();
        StringBuilder oldKey = new StringBuilder();
        StringBuilder oldValue = new StringBuilder();
        StringBuilder comparison = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            while (value.charAt(i) != '=') {
                key.append(value.charAt(i));
                if (key.toString().equals("WHERE")) {
                    i++;
                    key.delete(0, 1000);

                    while (value.charAt(i) != '=' & value.charAt(i) != '<' & value.charAt(i) != '>') {
                        oldKey.append(value.charAt(i));
                        i++;
                    }
                    comparison.append(value.charAt(i));
                    i++;
                    while (value.charAt(i) != ',') {
                        oldValue.append(value.charAt(i));
                        i++;
                    }
                    break;
                }
                i++;
            }
            if (value.charAt(i) == '=') {
                i++;
                while (i < value.length()) {
                    mapValue.append(value.charAt(i));
                    i++;
                }
            }
        }
        if (comparison.toString().equals("=")) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                try {
                    if (row.containsKey(oldKey.toString()) & row.containsValue(Integer.parseInt(oldValue.toString()))) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                    }
                } catch (NumberFormatException e) {
                    if (row.containsKey(oldKey.toString()) & row.containsValue(oldValue.toString())) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                    }
                }
            }
        }
        if (comparison.toString().equals("<")) {
            int oldIntValue = Integer.parseInt(oldValue.toString());
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                for (int index = 0; index < oldIntValue; index++) {
                    if (row.containsKey(oldKey.toString()) & row.containsValue(index)) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                    }
                }
            }
        }
        if (comparison.toString().equals(">")) {
            int oldIntValue;
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                oldIntValue = Integer.parseInt(oldValue.toString());
                for (int index = oldIntValue; index < mapMaxValue(row); index++) {
                    if (row.containsKey(oldKey.toString()) & row.get(oldKey.toString()).equals(oldIntValue)) {
                        row.replace(key.toString(), mapValue);
                        list.set(i, row);
                        break;
                    }
                    oldIntValue++;
                }
            }
        }
    }

    private void deleteValues(String value) {
        value = value.replaceAll(" ", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll("WHERE", "");
        value = value.replaceAll("VALUES", "");
        StringBuilder key = new StringBuilder();
        StringBuilder mapValue = new StringBuilder();
        StringBuilder comparison = new StringBuilder();

        for (int i = 0; i != value.length(); i++) {
            if (value.charAt(i) == '=' || value.charAt(i) == '<' || value.charAt(i) == '>') {
                comparison.append(value.charAt(i));
                while (i != value.length() - 1) {
                    i++;
                    mapValue.append(value.charAt(i));
                }
            } else {
                key.append(value.charAt(i));
            }
        }
        if (comparison.toString().equals("=")) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                try {
                    if (row.containsKey(key.toString()) & row.containsValue(Integer.parseInt(mapValue.toString()))) {
                        list.remove(i);
                    }
                } catch (NumberFormatException e) {
                    if (row.containsKey(key.toString()) & row.containsValue(mapValue.toString())) {
                        list.remove(i);
                    }
                }
            }
        }
        if (comparison.toString().equals("<")) {
            int oldIntValue = Integer.parseInt(mapValue.toString());
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                for (int index = 0; index < oldIntValue; index++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(index)) {
                        list.remove(i);
                    }
                }
            }
        }
        if (comparison.toString().equals(">")) {
            int oldIntValue;
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> row = list.get(i);
                oldIntValue = Integer.parseInt(mapValue.toString());
                for (int index = oldIntValue; index < mapMaxValue(row); index++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(oldIntValue)) {
                        list.remove(i);
                        break;
                    }
                    oldIntValue++;
                }
            }
        }
    }

    private void selectValues(String value) {
        value = value.replaceAll(" ", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll("WHERE", "");
        value = value.replaceAll("VALUES", "");
        StringBuilder key = new StringBuilder();
        StringBuilder mapValue = new StringBuilder();
        StringBuilder comparison = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '=' || value.charAt(i) == '<' || value.charAt(i) == '>') {
                comparison.append(value.charAt(i));
                i++;
                while (i < value.length()) {
                    mapValue.append(value.charAt(i));
                    i++;
                }
                break;
            }
            key.append(value.charAt(i));
        }

        if (comparison.toString().equals("=")) {
            for (Map<String, Object> row : list) {
                try {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(Integer.parseInt(mapValue.toString()))) {
                        System.out.println(row);
                    }
                } catch (NumberFormatException e) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(mapValue.toString())) {
                        System.out.println(row);
                    }
                }
            }
        }
        if (comparison.toString().equals("<")) {
            int oldIntValue = Integer.parseInt(mapValue.toString());
            for (Map<String, Object> row : list) {
                for (int index = 0; index < oldIntValue; index++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(index)) {
                        System.out.println(row);
                    }
                }
            }
        }
        if (comparison.toString().equals(">")) {
            int oldIntValue;
            for (Map<String, Object> row : list) {
                oldIntValue = Integer.parseInt(mapValue.toString());
                for (int index = oldIntValue; index < mapMaxValue(row); index++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(index)) {
                        System.out.println(row);
                        break;
                    }
                    oldIntValue++;
                }
            }
        }
    }

    private int mapMaxValue(Map<String, Object> map) {
        String val;
        int maxValue = 0;
        int oldValue = 0;
        List<Object> values = new ArrayList<>(map.values());
        for (int i = 0; i < map.size(); i++) {
            val = values.get(i).toString();
            try {
                oldValue = Integer.parseInt(val);
            } catch (NumberFormatException ignored) {

            }
            if (oldValue > maxValue) {
                maxValue = oldValue;
            }
        }
        return maxValue;
    }
}