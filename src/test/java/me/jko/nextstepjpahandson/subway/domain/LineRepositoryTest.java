package me.jko.nextstepjpahandson.subway.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Test;

@DataJpaTest
class LineRepositoryTest {

  @Autowired
  private LineRepository lines;

  @Test
  void save() {
    Line expected = new Line("1호선");
    lines.save(expected);
  }
}
