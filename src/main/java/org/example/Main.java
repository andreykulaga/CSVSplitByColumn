package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args)  {
        String filePath = args[0];
        String delimeter = args[1];
        int headerToSplitBy = Integer.parseInt(args[2]);
        String header = "no header";
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));) {
            header = bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String key = line.split(delimeter)[headerToSplitBy-1];
                if (!hashMap.containsKey(key)) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(line);
                    hashMap.put(key, list);
                } else {
                    ArrayList<String> list = hashMap.get(key);
                    list.add(line);
                    hashMap.put(key, list);
                 }
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

        for (String key: hashMap.keySet()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(key))) {
                bufferedWriter.write(header);
                bufferedWriter.newLine();
                int size = hashMap.get(key).size();
                for (int i = 0; i < size; i++) {
                    String line = hashMap.get(key).get(i);
                    bufferedWriter.write(line);
                    if (i != size-1) {
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}