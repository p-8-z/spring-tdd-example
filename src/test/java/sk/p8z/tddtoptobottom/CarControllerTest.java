package sk.p8z.tddtoptobottom;


import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sk.p8z.tddtoptobottom.domain.Car;

/**
 * 2.
 * <p>
 * Test more focused to controller Should test every scenario that controller can respond
 * (happy/unhappy)
 * <p>
 * 6. RestDocumentationExtension
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest({CarController.class})
public class CarControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CarService carService;

  @BeforeEach
  public void setup(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation
  ) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  public void getCar_shouldReturnCar() throws Exception {
    ConstraintDescriptions desc = new ConstraintDescriptions(Car.class);

    given(carService.getCarDetails(anyString())).willReturn(new Car(0L, "prius", "hybrid"));

    mockMvc.perform(get("/cars/{name}", "prius"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("prius"))
        .andExpect(jsonPath("type").value("hybrid"))
        // document your service
        .andDo(
            document("getCar", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("name").description("The name of the car")),
                responseFields(
                    fieldWithPath("id").description(
                        "The id of the car" + collectionToDelimitedString(
                            desc.descriptionsForProperty("id"), ". ")),
                    fieldWithPath("name").description(
                        "The name of the car" + collectionToDelimitedString(
                            desc.descriptionsForProperty("name"), ". ")),
                    fieldWithPath("type").description("The type of the car")
                )));
  }

  @Test
  public void getCar_notFound() throws Exception {
    given(carService.getCarDetails(anyString())).willThrow(new CarNotFoundException());

    mockMvc.perform(get("/cars/{name}", "prius"))
        .andExpect(status().isNotFound());
  }
}
