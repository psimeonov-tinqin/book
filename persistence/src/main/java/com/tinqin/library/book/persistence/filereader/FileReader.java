package com.tinqin.library.book.persistence.filereader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
public class FileReader {
    private final Integer batchSize;
    private final BufferedReader reader;

    private FileReader(Integer batchSize, BufferedReader reader) {
        this.batchSize = batchSize;
        this.reader = reader;
    }

    public static FileReader loadFile(String path, Integer batchSize) {
        try {
            InputStream pathResource = new ClassPathResource(path).getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(pathResource);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            return new FileReader(batchSize, reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookModel> getBatch() {

        return IntStream
                .range(0, batchSize)
                .mapToObj(this::readLine)
                .map(this::parseLine)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private String readLine(Integer index) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    private Optional<BookModel> parseLine(String line) {

        if (line == null || line.isBlank()) {
            return Optional.empty();
        }

        String[] parts = line.split(",\\s*");

        if (parts.length != 5) {
            log.warn("Invalid line format: " + line);
            return Optional.empty();
        }

        return Optional.of(BookModel
                .builder()
                .title(parts[0])
                .pages(Integer.parseInt(parts[1]))
                .price(Double.parseDouble(parts[2]))
                .authorFirstName(parts[3])
                .authorLastName(parts[4])
                .build()
        );
    }
}
