package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TimeEntryConfiguration {

    @Bean
    public TimeEntryRepository createInMemoryRepo(DataSource dataSource){

        //return new InMemoryTimeEntryRepository();
        //MysqlDataSource dataSource = new MysqlDataSource();
        //dataSource.setUrl(System.getenv("SPRING_DATASOURCE_URL"));
        return new JdbcTimeEntryRepository(dataSource);
    }
}
