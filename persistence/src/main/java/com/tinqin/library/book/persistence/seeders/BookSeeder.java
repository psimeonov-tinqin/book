package com.tinqin.library.book.persistence.seeders;

import com.tinqin.library.book.persistence.filereaderfactory.FileReaderFactory;
import com.tinqin.library.book.persistence.filereaderfactory.base.FileReader;
import com.tinqin.library.book.persistence.filereaderfactory.models.BookModel;
import com.tinqin.library.book.persistence.filereaderfactory.readers.CsvFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class BookSeeder implements ApplicationRunner {
    private final FileReaderFactory fileReaderFactory;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;

    String BOOKS_QUERY = """
            INSERT INTO books (id, created_ad, is_deleted, pages, price, stock, title, author_id)
            VALUES (gen_random_uuid(),
                    now(),
                    false,
                    ?,
                    ?,
                    0,
                    ?,
                    (SELECT id
                     FROM authors
                     WHERE first_name = ?
                        AND last_name = ?))
            """;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Connection connection = DriverManager.getConnection(jdbcUrl, postgresUsername, postgresPassword);

        PreparedStatement ps = connection.prepareStatement(BOOKS_QUERY);

//        FileReader fileReader = fileReaderFactory.createCsvFileReader("books.csv", 2);
        FileReader fileReader = fileReaderFactory.createJsonFileReader("books.json", 2);

        List<BookModel> batch = fileReader.getBatch();

        while (!batch.isEmpty()) {
            for (BookModel book : batch) {
                ps.setInt(1, book.getPages());
                ps.setDouble(2, book.getPrice());
                ps.setString(3, book.getTitle());
                ps.setString(4, book.getAuthorFirstName());
                ps.setString(5, book.getAuthorLastName());

                ps.addBatch();
                ps.clearParameters();
            }

            ps.executeBatch();
            batch = fileReader.getBatch();
        }
    }
}

