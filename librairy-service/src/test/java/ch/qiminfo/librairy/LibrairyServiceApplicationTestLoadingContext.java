package ch.qiminfo.librairy;


import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibrairyServiceApplicationTestLoadingContext {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        assertThat(this.objectMapper).isNotNull();
        assertThat(this.objectMapper.getRegisteredModuleIds())
                .containsExactlyInAnyOrder(Jdk8Module.class.getName(),
                        JavaTimeModule.class.getName(), ParameterNamesModule.class.getName());

    }
}
