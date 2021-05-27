package me.jko.nextstepjpahandson.subway.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class StationRepositoryTest {

  @Autowired
  private StationRepository stations;
  @Autowired
  private LineRepository lines;

  @Test
  void save() {
    Station expectedStation = new Station("주안역");
    assertThat(expectedStation.getId()).isNull();

    Station actualStation = stations.save(expectedStation);
    assertThat(actualStation.getId()).isNotNull();
    assertThat(actualStation.getName()).isEqualTo("주안역");
    assertThat(actualStation.getLine()).isNull();
  }

  @Test
  void saveWithLine() {
    Line line = new Line("1호선");
    Line savedLine = lines.save(line);
    assertNotNull(savedLine.getId());

    Station station = new Station("주안역");
    station.setLine(savedLine);
    Station actualStation = stations.save(station);
    assertNotNull(actualStation.getLine());
  }

  @Test
  void findByNameWithLine() {
    Station actual = stations.findByName("잠실역"); // initial dat by import.sql
    assertThat(actual).isNotNull();
    assertThat(actual.getLine().getName()).isEqualTo("2호선"); // initial dat by import.sql
  }

  @Test
//  @Rollback(value = false)
  void updateWithLine() {
    Station station = stations.findByName("잠실역");

    Line line = new Line("3호선");
    Line newLine = lines.save(line);

    station.setLine(newLine);
  }

  @Test
//  @Rollback(value = false)
  void removeLine() {
    Station expected = stations.findByName("잠실역");
    expected.setLine(null);
  }

  @Test
  void findByName() {
    Station expectedStation = new Station("주안역");
    stations.save(expectedStation);

    // name: key 값이 아님.
    // 1차 캐쉬에서 순회해야하지 찾으려면 부담.
    // 이때는, 그냥 query 동작
    // 중연한건, query 되기 전에 flush 발생 !!
    Station actual = stations.findByName("주안역");
    assertThat(actual.getId()).isNotNull();
    assertThat(actual.getName()).isEqualTo("주안역");

    // 동일성 검증 == 값만 같은게 아니라, 두 객체의 주소값이 같음
    assertThat(actual).isSameAs(expectedStation);
  }

  @Test
  @DisplayName("Dirty Checking")
  void update() {
    Station station = new Station("주안역");
    Station savedStation01 = stations.save(station);
    savedStation01.changeName("신림역");
//    savedStation01.changeName("주안역"); // 주석 풀면 update 발생하지 않고, 테스트 실패

    Station savedStation02 = stations.findByName("신림역");
    assertThat(savedStation02).isNotNull();
  }
}