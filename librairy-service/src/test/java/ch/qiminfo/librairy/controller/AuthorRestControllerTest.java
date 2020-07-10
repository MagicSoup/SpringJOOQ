package ch.qiminfo.librairy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.das.AuthorDAS;
import ch.qiminfo.librairy.exception.AuthorNotFoundException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(AuthorRestController.class)
class AuthorRestControllerTest {

    private final static String API_BASE_URI = "/v1/author/";

    private final static String EXPECTED_NOT_FOUND_MESSAGE = "Author not found with uuid %s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorDAS authorDAS;

    @Test
    void get_existing_author_should_return_ok_status() throws Exception {

        String uuid = UUID.randomUUID().toString();
        AuthorBean authorBean = getAuthor(uuid);

        when(this.authorDAS.getByUuid(eq(uuid))).thenReturn(authorBean);

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI + uuid);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        AuthorBean authorResponse = this.mapper.readValue(contentAsString, AuthorBean.class);

        Assertions.assertThat(authorResponse).isNotNull();
        Assertions.assertThat(authorResponse.uuid()).isEqualTo(uuid);
    }

    @Test
    void get_unknown_author_should_return_not_found_status() throws Exception {

        String uuid = UUID.randomUUID().toString();

        doThrow(new AuthorNotFoundException(uuid)).when(this.authorDAS).getByUuid(eq(uuid));

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI + uuid);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isNotFound());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        assertThat(contentAsString).isNotNull()
                .isEqualTo(String.format(EXPECTED_NOT_FOUND_MESSAGE, uuid));
    }

    @Test
    void get_all_authors_should_return_ok_status() throws Exception {

        String uuid = UUID.randomUUID().toString();

        when(this.authorDAS.getAll()).thenReturn(Lists.newArrayList(getAuthor(uuid)));

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        JavaType authorBeanListType = mapper.getTypeFactory().constructCollectionType(List.class, AuthorBean.class);
        List<AuthorBean> authorBeans = this.mapper.readValue(contentAsString, authorBeanListType);

        Assertions.assertThat(authorBeans).hasSize(1);
        AuthorBean authorBean = authorBeans.get(0);
        Assertions.assertThat(authorBean).isNotNull();
        Assertions.assertThat(authorBean.uuid()).isEqualTo(uuid);

    }

    private AuthorBean getAuthor(String uuid) {
        LocalDate birth = LocalDate.of(1984, 4, 23);
        return AuthorBean.builder().uuid(uuid).firstName("Louis").lastName("Champion").birth(birth).build();
    }
}
