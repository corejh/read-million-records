package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {

        System.out.println("Generating 1 million records...");

        int batchSize = 10000;
        int totalRecords = 1_000_000;

        for (int start = 1; start <= totalRecords; start += batchSize) {

            List<Integer> batch = new ArrayList<>();

            for (int i = start; i < start + batchSize && i <= totalRecords; i++) {
                batch.add(i);
            }

            jdbcTemplate.batchUpdate(
                    """
                    INSERT INTO employee_records
                    (id, first_name, last_name, salary)
                    VALUES (?, ?, ?, ?)
                    """,
                    new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(
                                PreparedStatement ps,
                                int i
                        ) throws SQLException {

                            int id = batch.get(i);

                            ps.setInt(1, id);
                            ps.setString(2, "First_" + id);
                            ps.setString(3, "Last_" + id);
                            ps.setDouble(4, 50000 + (id % 10000));
                        }

                        @Override
                        public int getBatchSize() {
                            return batch.size();
                        }
                    }
            );

            System.out.println("Inserted: " + (start + batch.size() - 1));
        }

        System.out.println("Finished generating records.");
    }
}