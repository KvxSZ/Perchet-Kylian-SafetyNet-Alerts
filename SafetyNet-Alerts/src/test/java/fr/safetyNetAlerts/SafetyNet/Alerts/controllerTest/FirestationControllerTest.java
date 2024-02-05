package fr.safetyNetAlerts.SafetyNet.Alerts.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.FireStation;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.EmergencyService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private EmergencyService emergencyService;

    public static String loadJson(String filename) {
        try {
            Resource data = new ClassPathResource(filename);
            return Files.readString(data.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException("e");
        }
    }

    @BeforeEach
    public void setUp() {
        emergencyService.readJsonFile();
    }

    @Test
    public void testGetFirestation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation/firestation"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"),
                        MockMvcResultMatchers.content().json(loadJson("resultGetFirestationController.json"))
                );
    }

    @Test
    public void testAddFireStation() throws Exception {
        FireStation testFireStation = new FireStation("123 Main St", "1");
        String jsonContent = objectMapper.writeValueAsString(testFireStation);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateFireStation() throws Exception {
        FireStation updatedFireStation = new FireStation("123 Main St", "2");
        String jsonContent = objectMapper.writeValueAsString(updatedFireStation);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .param("address", "1509 Culver St").content(jsonContent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteFireStation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation").param("address", "1509 Culver St"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetPeopleCoveredByStation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                        .param("stationNumber", "3"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"),
                        MockMvcResultMatchers.content().json(loadJson("resultGetPeopleCoveredByStationController.json"))
                );
    }
}
