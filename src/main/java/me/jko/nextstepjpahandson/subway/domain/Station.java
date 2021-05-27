package me.jko.nextstepjpahandson.subway.domain;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "station")
@Entity
public class Station {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "station_id")
  private Long id;

  @NonNull                                  // NonNull : 내가 만든 application 상에서
  @Column(name = "name", nullable = false)  // nullable : 스키마상에서
  private String name;

  @ManyToOne
  @JoinColumn(name = "line_id") // foreign key name
  private Line line;

  public Station(String name) {
    this.name = name;
  }

  protected Station() {
  }

  public Long getId() {
    return id;
  }

  @NonNull
  public String getName() {
    return name;
  }

  // Convenient Method
  public void setLine(Line line) {
    this.line = line;
    if (line == null) {
      return;
    }

    this.line.getStations().add(this);
  }

  public void changeName(String name) {
    this.name = name;
  }

  public Line getLine() {
    return line;
  }
}
