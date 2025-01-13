package com.tinqin.library.book.persistence.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Order(1)
@Profile("!test")
public class AuthorSeeder implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_AUTHOR_QUERY_TEMPLATE = """
            INSERT INTO authors (id, first_name, last_name)
            VALUES """;

    private final List<String> authors = List.of(
            "Ivan Vazov",
            "Hristo Botev");

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Integer authorCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM authors", Integer.class);

        if (authorCount > 0) {
            return;
        }

        String names = authors
                .stream()
                .map(author -> author.split(" "))
                .map(authorNames -> String.format("(gen_random_uuid(), '%s', '%s')", authorNames[0], authorNames[1]))
                .collect(Collectors.joining(", "));

        String query = INSERT_AUTHOR_QUERY_TEMPLATE + names;

        jdbcTemplate.execute(query);
    }
}
