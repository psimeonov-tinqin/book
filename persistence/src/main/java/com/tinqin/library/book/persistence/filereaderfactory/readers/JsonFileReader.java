package com.tinqin.library.book.persistence.filereaderfactory.readers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.tinqin.library.book.persistence.filereaderfactory.base.FileReader;
import com.tinqin.library.book.persistence.filereaderfactory.models.BookModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.awt.print.Book;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JsonFileReader implements FileReader {
    private final Integer batchSize;
    private final JsonParser jsonParser;

    public JsonFileReader(String path, Integer batchSize) {
        this.batchSize = batchSize;
        this.jsonParser = initParser(path);
    }

    private JsonParser initParser(String path) {
        try {
            InputStream pathResource = new ClassPathResource(path).getInputStream();
            JsonFactory jsonFactory = new JsonFactory();
            return jsonFactory.createParser(pathResource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<BookModel> getBatch() {

        ArrayList<BookModel> batch = new ArrayList<>();

        try {
            while (jsonParser.nextToken() != JsonToken.END_ARRAY && batch.size() < batchSize) {
                if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
                    Optional<BookModel> book = parseObject();
                    book.ifPresent(batch::add);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return batch;
    }

    private Optional<BookModel> parseObject() {
        BookModel.BookModelBuilder builder = BookModel.builder();

        try {
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = jsonParser.currentName();
                jsonParser.nextToken();

                switch (fieldName) {
                    case "title" -> builder.title(jsonParser.getText());
                    case "pages" -> builder.pages(jsonParser.getIntValue());
                    case "price" -> builder.price(jsonParser.getDoubleValue());
                    case "authorFirstName" -> builder.authorFirstName(jsonParser.getText());
                    case "authorLastName" -> builder.authorLastName(jsonParser.getText());
                    default -> jsonParser.skipChildren();
                }
            }

            BookModel book = builder.build();
            return Optional.of(book);
        }catch (IOException e) {
            log.warn("Error parsing JSON " + e);
            return Optional.empty();
        }
    }
}
