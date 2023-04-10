package com.example.doodle.model.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScoreTable {
    static List<String> lines;

    public void addData() throws IOException {
        lines = new ArrayList<>();
        File file = new File("C:\\4 семестр\\Doodle\\src\\main\\resources\\score.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
    }

    public void addToTable(long resultScore) {
        int size = lines.size();

        for (int i = 0; i < size; i++) {
            if (resultScore > Long.parseLong(lines.get(i))) {
                lines.add(i, Long.toString(resultScore));
                break;
            }
        }

        if (size < 10 && lines.size() == size) {
            lines.add(Long.toString(resultScore));
        }

        if (size == 10 && lines.size() > size) {
            lines.remove(lines.size() - 1);
        }
    }

    public void updateFile() throws IOException {
        File file = new File("C:\\4 семестр\\Doodle\\src\\main\\resources\\score.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String updatedLine : lines) {
            writer.write(updatedLine);
            writer.newLine();
        }
        writer.close();
    }

    public Iterator<String> getIterator() {
        return lines.iterator();
    }
}
