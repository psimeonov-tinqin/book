package com.tinqin.library.book.persistence.seeders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;


    @AllArgsConstructor
    @Builder
    @Getter
    private static class BookTemplate {
        private final String title;
        private final Integer pages;
        private final Double price;
        private final String authorFirstName;
        private final String authorLastName;
    }

    private final List<BookTemplate> books = List.of(
            BookTemplate.builder()
                    .title("Pod Igoto")
                    .pages(300)
                    .price(2.50)
                    .authorFirstName("Ivan")
                    .authorLastName("Vazov")
                    .build(),

            BookTemplate.builder()
                    .title("Elegiya")
                    .pages(200)
                    .price(2.00)
                    .authorFirstName("Hristo")
                    .authorLastName("Botev")
                    .build(),

            BookTemplate.builder()
                    .title("Elegiya")
                    .pages(200)
                    .price(2.00)
                    .authorFirstName("Hristo2")
                    .authorLastName("Botev")
                    .build()
    );


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

        for (BookTemplate book : books) {
            ps.setInt(1, book.getPages());
            ps.setDouble(2, book.getPrice());
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getAuthorFirstName());
            ps.setString(5, book.getAuthorLastName());

            ps.addBatch();
            ps.clearParameters();
        }

        ps.executeBatch();
    }
}

