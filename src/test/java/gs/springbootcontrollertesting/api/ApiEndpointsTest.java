package gs.springbootcontrollertesting.api;

import gs.springbootcontrollertesting.model.ThingResponse;
import gs.springbootcontrollertesting.service.ThingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiEndpointsTest {

    private MockMvc mockMvc;

    @Mock
    private ThingService thingService;

    @InjectMocks
    private ApiEndpoints apiEndpoints;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiEndpoints).build();
    }

    @Test
    public void create_returnsError_onEmptyRequest() throws Exception {
        mockMvc.perform(
                post("/thing/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(status().is(400));
    }

    @Test
    public void create_returnsError_onRequestWithMissingRequiredField() throws Exception {
        mockMvc.perform(
                post("/thing/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"a description\", \"notes\": \"some notes\"}")
        ).andExpect(status().is(400));
    }

    @Test
    public void create_returnsCreated_onValidRequest() throws Exception {
        int id = 1;
        when(thingService.create(any())).thenReturn(id);

        MvcResult result = mockMvc.perform(
                        post("/thing/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\": \"the name\", \"description\": \"a description\", \"notes\": \"some notes\"}")
                ).andExpect(status().is(201))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.format("{\"id\": %d}", id));
    }

    @Test
    public void get_returnsThingResponse_onRequestWithIdThatExists() throws Exception {
        int id = 1;
        when(thingService.get(id)).thenReturn(
                ThingResponse.builder()
                        .id(id)
                        .name("thing-name")
                        .description("thing-description")
                        .notes("thing-notes")
                        .build()
        );

        MvcResult result = mockMvc.perform(
                        get("/thing/" + id)
                ).andExpect(status().is(200))
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("{\"id\":1,\"name\":\"thing-name\",\"description\":\"thing-description\",\"notes\":\"thing-notes\"}");
    }

    @Test
    public void get_returnsThingResponse_onRequestWithIdThatDoesNotExists() throws Exception {
        when(thingService.get(anyInt())).thenReturn(null);

        MvcResult result = mockMvc.perform(
                        get("/thing/2")
                ).andExpect(status().is(200))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    public void delete_returnsNoContent_onRequestWithId() throws Exception {
        mockMvc.perform(
                delete("/thing/1")
        ).andExpect(status().is(204));
    }

}