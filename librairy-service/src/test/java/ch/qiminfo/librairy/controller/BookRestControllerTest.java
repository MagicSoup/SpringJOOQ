package ch.qiminfo.librairy.controller;

import ch.qiminfo.librairy.bean.AuthorBean;
import ch.qiminfo.librairy.bean.BookBean;
import ch.qiminfo.librairy.das.BookDAS;
import ch.qiminfo.librairy.das.request.BookRequest;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    private final static String API_BASE_URI = "/v1/book/";

    private final static String EXPECTED_NOT_FOUND_MESSAGE = "Book not found with uuid %s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookDAS bookDAS;

    @Test
    void get_existing_book_should_return_ok_status() throws Exception {

        String uuid = UUID.randomUUID().toString();
        BookBean bean = getBook(uuid);

        when(this.bookDAS.getByUuid(eq(uuid))).thenReturn(Optional.of(bean));

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI + uuid);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        BookBean bookResponse = this.mapper.readValue(contentAsString, BookBean.class);

        Assertions.assertThat(bookResponse).isNotNull();
        Assertions.assertThat(bookResponse.uuid()).isEqualTo(uuid);
    }

    @Test
    void get_unknown_book_should_return_not_found_status() throws Exception {

        String uuid = UUID.randomUUID().toString();

        when(this.bookDAS.getByUuid(eq(uuid))).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI + uuid);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isNotFound());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        assertThat(contentAsString).isNotNull()
                .isEqualTo(String.format(EXPECTED_NOT_FOUND_MESSAGE, uuid));
    }

    @Test
    void get_all_books_should_return_ok_status() throws Exception {

        String uuid = UUID.randomUUID().toString();

        when(this.bookDAS.search(any(BookRequest.class))).thenReturn(Lists.newArrayList(getBook(uuid)));

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        JavaType bookBeanListType = this.mapper.getTypeFactory().constructCollectionType(List.class, BookBean.class);
        List<BookBean> bookBeans = this.mapper.readValue(contentAsString, bookBeanListType);

        Assertions.assertThat(bookBeans).hasSize(1);
        BookBean bookBean = bookBeans.get(0);
        Assertions.assertThat(bookBean).isNotNull();
        Assertions.assertThat(bookBean.uuid()).isEqualTo(uuid);
    }

    @Test
    void get_all_books_by_author_should_return_ok_status() throws Exception {

        String bookUuid = UUID.randomUUID().toString();
        String authorUuid = UUID.randomUUID().toString();

        when(this.bookDAS.search(any(BookRequest.class))).thenReturn(Lists.newArrayList(getBook(bookUuid, authorUuid)));

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI + "author/" + authorUuid);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isOk());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        JavaType bookBeanListType = mapper.getTypeFactory().constructCollectionType(List.class, BookBean.class);
        List<BookBean> bookBeans = this.mapper.readValue(contentAsString, bookBeanListType);

        Assertions.assertThat(bookBeans).hasSize(1);
        BookBean bookBean = bookBeans.get(0);
        Assertions.assertThat(bookBean).isNotNull();
        Assertions.assertThat(bookBean.uuid()).isEqualTo(bookUuid);
        Assertions.assertThat(bookBean.authors().get(0).uuid()).isEqualTo(authorUuid);
    }

    private BookBean getBook(String uuid) {
        return getBook(uuid, UUID.randomUUID().toString());
    }

    private BookBean getBook(String uuid, String authorUuid) {
        LocalDate birth = LocalDate.of(1984, 4, 23);
        AuthorBean authorBean = AuthorBean.builder()
                .uuid(authorUuid)
                .firstName("Louis")
                .lastName("Champion")
                .birth(birth)
                .build();
        return BookBean.builder().title("the Great Escape ! ! !")
                .uuid(uuid)
                .authors(Lists.newArrayList(authorBean))
                .build();
    }
}
