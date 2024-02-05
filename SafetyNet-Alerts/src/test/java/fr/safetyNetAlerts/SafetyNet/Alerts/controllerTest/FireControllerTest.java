package fr.safetyNetAlerts.SafetyNet.Alerts.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalrecordService medicalrecordService;

    public static String loadJson(String filename) {
        try {
            Resource data = new ClassPathResource(filename);
            return Files.readString(data.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testGetResidentsAndFireStationByAddress() throws Exception {
        String address = "1509 Culver St";

        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", address))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"),
                        MockMvcResultMatchers.content().json(loadJson("resultGetResidentsAndFireStationByAddressController.json"))
                );
    }
}