package com.keca.AirVentureBack.activity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.keca.AirVentureBack.upload.services.UploadScalewayService;

import jakarta.servlet.http.Cookie;
import net.minidev.json.JSONObject;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.hasSize;







@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UploadScalewayService uploadScalewayService;

    public String getAuthenticationCookie() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "john@gmail.com");
        jsonUser.put("password", "Johndoe/00");

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON));

        MvcResult result = resultActions.andReturn();
        return result.getResponse().getCookie("token").getValue();

    }

    @Test
    public void testGetActivities() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/activities").cookie(new Cookie("token", getAuthenticationCookie()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetActivityById() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/activity/1")
                        .cookie(new Cookie("token", getAuthenticationCookie()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Parapente"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value("Le parapente se pratique avec une voile"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adress").value("Rue de baigura"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Ascin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("64400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxParticipants").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("AQUATIQUE"));
    }

 

//     @Test
//     void testUploadActivityPictures() throws Exception {
//         // Simuler le comportement du service Upload
//         List<String> mockUrls = Arrays.asList(
//             "https://airventure-images.scw.cloud/activities/2/LOGO_Guinguette_du_chateau_COUL.png",
//             "https://airventure-images.scw.cloud/activities/2/LOGO_Guinguette_du_chateau_COUL_2.png"
//         );
//         when(uploadScalewayService.uploadFiles(any(), any(), any())).thenReturn(mockUrls);

//         // Effectuer le test de l'upload
//         mockMvc.perform(MockMvcRequestBuilders.multipart("/activities/upload")  // Assurez-vous que l'URL est correcte ici
//                         .file("files", "file content".getBytes()) // Remplacer par un fichier d'exemple
//                         .param("identifier", "2")  // Identifiant de l'activité
//                         .param("folder", "activities")) // Dossier cible
//                 .andExpect(status().isOk()) // Vérifier que la réponse est 200 OK
//                 .andExpect(jsonPath("$.fileUrls[0]").value("https://airventure-images.scw.cloud/activities/2/LOGO_Guinguette_du_chateau_COUL.png")) // Vérifier la première URL
//                 .andExpect(jsonPath("$.fileUrls[1]").value("https://airventure-images.scw.cloud/activities/2/LOGO_Guinguette_du_chateau_COUL_2.png")); // Vérifier la deuxième URL
//     }
    

    




}
