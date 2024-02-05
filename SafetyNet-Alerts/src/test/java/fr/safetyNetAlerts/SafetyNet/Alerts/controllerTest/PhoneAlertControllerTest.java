package fr.safetyNetAlerts.SafetyNet.Alerts.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
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
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private PersonService personService;

    public static String loadJson(String filename) {
        try {
            Resource data = new ClassPathResource(filename);
            return Files.readString(data.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testGetPhoneAlert() throws Exception {
        int firestationNumber = 1;
        List<String> phoneNumbers = Arrays.asList("841-874-6512", "841-874-8547", "841-874-7462", "841-874-7784", "841-874-7784", "841-874-7784");

        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                        .param("firestation", String.valueOf(firestationNumber)))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"),
                        MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(phoneNumbers))
                );
    }
}
