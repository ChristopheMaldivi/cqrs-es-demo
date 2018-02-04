package org.tophe.cqrses.infrastructure.persistence;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryRepositoryTest {

  private InMemoryRepository<Pojo, String> repo;

  class Pojo {
    String _id = null;
  }
  class PojoLongId {
    Long _id = null;
  }

  @Before
  public void setUp() {
    repo = new InMemoryRepository<>();
  }

  @Test
  public void add_an_element_to_the_repository_and_retrieve_it_with_a_valid_STRING_id() {
    // when
    Pojo pojo = repo.save(new Pojo());

    // then
    assertThat(pojo._id).isNotEmpty();
  }

  @Test
  public void add_an_element_to_the_repository_and_retrieve_it_with_a_valid_LONG_id() {
    // given
    InMemoryRepository<PojoLongId, Long> longIdRepo = new InMemoryRepository<>();

    // when
    PojoLongId pojo = longIdRepo.save(new PojoLongId());

    // then
    assertThat(pojo._id).isEqualTo(0);
  }

  @Test
  public void add_an_element_to_the_repository_with_an_existing_STRING_id_and_retrieve_it_with_this_id() {
    // given
    String id = UUID.randomUUID().toString();
    Pojo pojo = new Pojo();
    pojo._id = id;

    // when
    pojo = repo.save(pojo);

    // then
    assertThat(pojo._id).isEqualTo(id);
  }

  @Test
  public void add_an_element_to_the_repository_with_an_existing_LONG_id_and_retrieve_it_with_this_id() {
    // given
    InMemoryRepository<PojoLongId, Long> longIdRepo = new InMemoryRepository<>();
    int id = UUID.randomUUID().hashCode();
    PojoLongId pojo = new PojoLongId();
    pojo._id = Long.valueOf(id);

    // when
    pojo = longIdRepo.save(pojo);

    // then
    assertThat(pojo._id).isEqualTo(id);
  }

  @Test
  public void two_added_elements_have_distinct_ids() {
    // when
    Pojo pojo1 = repo.save(new Pojo());
    Pojo pojo2 = repo.save(new Pojo());

    // then
    assertThat(pojo1._id).isNotEmpty();
    assertThat(pojo2._id).isNotEmpty();
    assertThat(pojo1._id).isNotEqualTo(pojo2._id);
  }

  @Test
  public void find_an_element_by_id() {
    // when
    Pojo pojo = repo.save(new Pojo());
    Optional<Pojo> foundPojo = repo.findById(pojo._id);

    // then
    assertThat(foundPojo.isPresent()).isTrue();
    assertThat(foundPojo.get()._id).isEqualTo(pojo._id);
  }

  @Test
  public void find_an_element_by_id_list() {
    // when
    Pojo pojo = repo.save(new Pojo());
    Iterable<Pojo> foundPojos = repo.findAllById(Arrays.asList(new String[]{pojo._id}));

    // then
    assertThat(foundPojos).hasSize(1);
    assertThat(foundPojos.iterator().next()._id).isEqualTo(pojo._id);
  }

  @Test
  public void find_all_elements() {
    // when
    Pojo pojo = repo.save(new Pojo());
    Iterable<Pojo> foundPojo = repo.findAll();

    // then
    assertThat(foundPojo).hasSize(1);
    assertThat(foundPojo.iterator().next()._id).isEqualTo(pojo._id);
  }

  @Test
  public void delete_an_element_by_id() {
    // when
    Pojo pojo = repo.save(new Pojo());
    repo.deleteById(pojo._id);

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
