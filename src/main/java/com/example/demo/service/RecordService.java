package com.example.demo.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;

@Service
public class RecordService {

    private final JdbcTemplate jdbcTemplate;

    public RecordService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String processRecords() {

        long startTime = System.currentTimeMillis();

        final long[] count = {0};

        jdbcTemplate.query(
                connection -> {

                    PreparedStatement ps = connection.prepareStatement(
                            """
                            SELECT id,
                                   first_name,
                                   last_name,
                                   salary
                            FROM employee_records
                            """
                    );

                    // Stream rows in chunks
                    ps.setFetchSize(1000);

                    return ps;
                },
                resultSet -> {

                    while (resultSet.next()) {

                        long id = resultSet.getLong("id");
                        String firstName =
                                resultSet.getString("first_name");

                        // Simulate processing
                        if (count[0] % 100000 == 0) {

                            System.out.println(
                                    "Processed: " + count[0]
                            );
                        }

                        count[0]++;
                    }
                }
        );

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        return """
                Processing Complete
                
                Total Records: %d
                Execution Time (ms): %d
                """.formatted(count[0], duration);
    }
}