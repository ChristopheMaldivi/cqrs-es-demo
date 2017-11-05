package com.tophe.ddd.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryRepositoryTest {

  private InMemoryRepository<Pojo, String> repo;

  class Pojo {
    final String id = null;
  }
  class PojoLongId {
    final Long id = null;
  }

  @Before
  public void setUp() throws Exception {
    repo = new InMemoryRepository<>();
  }

  @Test
  public void add_an_element_to_the_repository_and_retrieve_it_with_a_valid_STRING_id() {
    // when
    Pojo pojo = repo.save(new Pojo());

    // then
    assertThat(pojo.id).isNotEmpty();
  }

  @Test
  public void add_an_element_to_the_repository_and_retrieve_it_with_a_valid_LONG_id() {
    // given
    InMemoryRepository<PojoLongId, Long> longIdRepo = new InMemoryRepository<>();

    // when
    PojoLongId pojo = longIdRepo.save(new PojoLongId());

    // then
    assertThat(pojo.id).isEqualTo(0);
  }

  @Test
  public void two_added_elements_have_distinct_ids() {
    // when
    Pojo pojo1 = repo.save(new Pojo());
    Pojo pojo2 = repo.save(new Pojo());

    // then
    assertThat(pojo1.id).isNotEmpty();
    assertThat(pojo2.id).isNotEmpty();
    assertThat(pojo1.id).isNotEqualTo(pojo2.id);
  }

  @Test
  public void find_an_element_by_id() {
    // when
    Pojo pojo = repo.save(new Pojo());
    Optional<Pojo> foundPojo = repo.findById(pojo.id);

    // then
    assertThat(foundPojo.isPresent()).isTrue();
    assertThat(foundPojo.get().id).isEqualTo(pojo.id);
  }

  @Test
  public void find_an_element_by_id_list() {
    // when
    Pojo pojo = repo.save(new Pojo());
    Iterable<Pojo> foundPojos = repo.findAllById(Arrays.asList(new String[]{pojo.id}));

    // then
    assertThat(foundPojos).hasSize(1);
    assertThat(foundPojos.iterator().next().id).isEqualTo(pojo.id);
  }

  @Test
  public void find_all_elements() {
    // when
    Pojo pojo = repo.save(new Pojo());
    Iterable<Pojo> foundPojo = repo.findAll();

    // then
    assertThat(foundPojo).hasSize(1);
    assertThat(foundPojo.iterator().next().id).isEqualTo(pojo.id);
  }

  @Test
  public void delete_an_element_by_id() {
    // when
    Pojo pojo = repo.save(new Pojo());
    repo.deleteById(pojo.id);

    // then
    assertThat(repo.findAll()).hasSize(0);
  }

  @Test
  public void delete_an_element_by_object() {
    // when
    Pojo pojo = repo.save(new Pojo());
    repo.delete(pojo);

    // then
    assertThat(repo.findAll()).hasSize(0);
  }

  @Test
  public void save_two_elements_at_a_time() {
    // given
    Pojo pojo1 = new Pojo();
    Pojo pojo2 = new Pojo();

    // when
    Iterable<Pojo> entities = repo.saveAll(Arrays.asList(new Pojo[]{pojo1, pojo2}));

    // then
    assertThat(repo.findAll()).hasSize(2);
    assertThat(entities).hasSize(2);
  }

  @Test
  public void delete_two_elements_at_a_time() {
    // given
    Pojo pojo1 = new Pojo();
    Pojo pojo2 = new Pojo();
    Iterable<Pojo> entities = repo.saveAll(Arrays.asList(new Pojo[]{pojo1, pojo2}));

    // when
    repo.deleteAll(entities);

    // then
    assertThat(repo.findAll()).hasSize(0);
  }

  @Test
  public void delete_all() {
    // given
    Pojo pojo1 = new Pojo();
    Pojo pojo2 = new Pojo();
    repo.saveAll(Arrays.asList(new Pojo[]{pojo1, pojo2}));

    // when
    repo.deleteAll();

    // then
    assertThat(repo.findAll()).hasSize(0);
  }
}
