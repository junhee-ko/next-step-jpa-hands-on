package me.jko.nextstepjpahandson.subway.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Line {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "line_id")
  private Long id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "line")
  private List<Station> stations = new ArrayList<>();

  public Line(String name) {
    this.name = name;
  }

  protected Line() {
  }

  public String getName() {
    return name;
  }

  public List<Station> getStations() {
    return stations;
  }

  public Long getId() {
    return id;
  }
}
