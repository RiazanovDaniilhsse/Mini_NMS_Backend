package org.project.catalog.service;

import org.junit.jupiter.api.Test;
import org.project.catalog.model.Site;
import org.project.catalog.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class SiteRepositoryTest {

  @Autowired
  private SiteRepository siteRepository;

  @Test
  void shouldSaveAndFindSite() {
    // Given
    Site site = new Site();
    site.setName("Тестовая площадка");
    site.setAddress("ул. Пушкина, 1");

    // When
    Site savedSite = siteRepository.save(site);

    // Then
    assertThat(savedSite.getId()).isNotNull();

    Optional<Site> foundSite = siteRepository.findById(savedSite.getId());
    assertThat(foundSite).isPresent();
    assertThat(foundSite.get().getName()).isEqualTo("Тестовая площадка");
  }

  @Test
  void shouldNotSaveSiteWithDuplicateName() {
    // Given
    Site site1 = new Site();
    site1.setName("Уникальное имя");
    siteRepository.save(site1);

    Site site2 = new Site();
    site2.setName("Уникальное имя");

    // When & Then
    assertThatThrownBy(() -> siteRepository.saveAndFlush(site2))
            .isInstanceOf(DataIntegrityViolationException.class);
  }
}