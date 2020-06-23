package ch.qiminfo.demo.controller;

import ch.qiminfo.demo.bean.AuthorBean;
import ch.qiminfo.demo.das.AuthorDAS;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    private final static String API_BASE_URI = "/v1/author/";

    private final static String EXPECTED_NOT_FOUND_MESSAGE = "Author not found with uuid %s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorDAS authorDAS;

    @Test
    public void get_existing_author_should_return_ok_status() throws Exception {

        String uuid = UUID.randomUUID().toString();
        AuthorBean authorBean = getAuthor(uuid);

        when(this.authorDAS.getByUuid(eq(uuid))).thenReturn(Optional.of(authorBean));

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI + uuid);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        AuthorBean authorResponse = this.mapper.readValue(contentAsString, AuthorBean.class);

        assertThat(authorResponse).isNotNull();
        assertThat(authorResponse.uuid()).isEqualTo(uuid);
    }

    @Test
    public void get_unknown_author_should_return_not_found_status() throws Exception {

        String uuid = UUID.randomUUID().toString();

        when(this.authorDAS.getByUuid(eq(uuid))).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI + uuid);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isNotFound());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        assertThat(contentAsString).isNotNull();
        assertThat(contentAsString).isEqualTo(String.format(EXPECTED_NOT_FOUND_MESSAGE, uuid));
    }

    @Test
    public void get_all_authors_should_return_ok_status() throws Exception {

        String uuid = UUID.randomUUID().toString();

        when(this.authorDAS.getAll()).thenReturn(Lists.newArrayList(getAuthor(uuid)));

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        JavaType authorBeanListType = mapper.getTypeFactory().constructCollectionType(List.class, AuthorBean.class);
        List<AuthorBean> authorBeans = this.mapper.readValue(contentAsString, authorBeanListType);

        assertThat(authorBeans).hasSize(1);
        AuthorBean authorBean = authorBeans.get(0);
        assertThat(authorBean).isNotNull();
        assertThat(authorBean.uuid()).isEqualTo(uuid);

    }

    private AuthorBean getAuthor(String uuid) {
        LocalDate birth = LocalDate.of(1984, 4, 23);
        return AuthorBean.builder().uuid(uuid).firstName("Louis").lastName("Champion").birth(birth).build();
    }
}
