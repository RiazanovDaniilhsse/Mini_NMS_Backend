package org.project.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.catalog.dto.SiteRequestDto;
import org.project.catalog.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Поднимает весь Spring-контекст
@AutoConfigureMockMvc // Настраивает MockMvc для HTTP-запросов
@Transactional // Откатывает транзакцию БД после каждого теста
class SiteControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc; // Имитация HTTP-клиента

  @Autowired
  private ObjectMapper objectMapper; // Для преобразования Java-объектов в JSON

  @Autowired
  private SiteRepository siteRepository; // Чтобы проверить, что данные реально в БД

  @Test
  void createSite_ShouldReturnCreatedAndSaveToDatabase() throws Exception {
    // Given: готовим DTO
    SiteRequestDto requestDto = new SiteRequestDto();
    requestDto.setName("Интеграционная площадка");
    requestDto.setAddress("ул. Тестовая, 1");
    requestDto.setLatitude(55.75);
    requestDto.setLongitude(37.61);

    // When: делаем POST-запрос
    mockMvc.perform(post("/api/sites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            // Then: проверяем HTTP-статус и JSON-ответ
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Интеграционная площадка"))
            .andExpect(jsonPath("$.address").value("ул. Тестовая, 1"));

    // Then: проверяем, что запись реально сохранилась в БД
    assertThat(siteRepository.findAll()).hasSize(1);
    assertThat(siteRepository.findAll().get(0).getName()).isEqualTo("Интеграционная площадка");
  }

  @Test
  void createSite_ShouldReturnBadRequest_WhenNameIsBlank() throws Exception {
    // Given: DTO с пустым именем (должна сработать валидация)
    SiteRequestDto requestDto = new SiteRequestDto();
    requestDto.setName(""); // Пустое имя

    // When & Then: ожидаем 400 Bad Request и наш кастомный формат ошибки
    mockMvc.perform(post("/api/sites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
            .andExpect(jsonPath("$.details[0]").value("Название площадки не может быть пустым"));
  }
}