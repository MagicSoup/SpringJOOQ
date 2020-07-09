package ch.qiminfo.librairy.exception.handler;

import ch.qiminfo.librairy.controller.AuthorRestController;
import ch.qiminfo.librairy.das.AuthorDAS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static ch.qiminfo.librairy.exception.handler.RestResponseEntityExceptionHandler.CONFLICT_MESSAGE_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorRestController.class)
class RestResponseEntityExceptionHandlerTest {

    private final static String API_BASE_URI = "/v1/author/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorDAS authorDAS;

    @Test
    void return_conflict_http_error_on_illegal_argument_exception() throws Exception {
        checkRuntimeException(new IllegalArgumentException(), CONFLICT_MESSAGE_ERROR);
    }

    @Test
    void return_conflict_http_error_on_illegal_state_exception() throws Exception {
        checkRuntimeException(new IllegalStateException(), CONFLICT_MESSAGE_ERROR);
    }

    private void checkRuntimeException(RuntimeException runtimeException, String errorMessage) throws Exception {
        doThrow(runtimeException).when(this.authorDAS).getAll();

        MockHttpServletRequestBuilder requestBuilder = get(API_BASE_URI);
        ResultActions perform = this.mockMvc.perform(requestBuilder);

        ResultActions resultActions = perform.andDo(print()).andExpect(status().isConflict());
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String contentAsString = response.getContentAsString();

        assertThat(contentAsString).isNotNull().isEqualTo(errorMessage);
    }
}
