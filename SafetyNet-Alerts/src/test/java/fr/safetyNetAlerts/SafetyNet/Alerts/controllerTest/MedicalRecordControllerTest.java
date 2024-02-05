package fr.safetyNetAlerts.SafetyNet.Alerts.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.safetyNetAlerts.SafetyNet.Alerts.model.MedicalRecord;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.EmergencyService;
import fr.safetyNetAlerts.SafetyNet.Alerts.service.MedicalrecordService;
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
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicalrecordService medicalRecordService;

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
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecord"))
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType("application/json"),
                        MockMvcResultMatchers.content().json(loadJson("resultGetMedicalRecordController.json"))
                );
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord testMedicalRecord = new MedicalRecord("John", "Doe", "03/06/1984", Collections.emptyList(), Collections.emptyList());
        String jsonContent = objectMapper.writeValueAsString(testMedicalRecord);

        mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        MedicalRecord updatedMedicalRecord = new MedicalRecord("John", "Doe", "03/06/1984", Collections.singletonList("aznol:200mg"), Collections.emptyList());
        String jsonContent = objectMapper.writeValueAsString(updatedMedicalRecord);
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstName", "John");
        requestParams.add("lastName", "Boyd");

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstName", "John");
        requestParams.add("lastName", "Boyd");
        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                        .params(requestParams))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}