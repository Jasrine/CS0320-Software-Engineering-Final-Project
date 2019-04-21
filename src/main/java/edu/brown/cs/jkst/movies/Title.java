package edu.brown.cs.jkst.movies;

import java.util.List;

public class Title {

  private enum Genre {
    ACTION, ADULT, ADVENTURE, ANIMATION, BIOGRAPHY, COMEDY, CRIME, DOCUMENTARY,
    DRAMA, FAMILY, FANTASY, FILM_NOIR, GAME_SHOW, HISTORY, HORROR, MUSICAL,
    MUSIC, MYSTERY, NEWS, REALITY_TV, ROMANCE, SCI_FI, SHORT, SPORT, TALK_SHOW,
    THRILLER, WAR, WESTERN
    }

  /**
   * Similarity Metrics for Genres (assuming order of listing is significant)
   * A B C -- A B C
   * A B C -- A C B
   * A B C -- A B
   * A B C -- A C
   * A B C -- A
   * A B C -- B C
   * A B C -- C B
   * A B C -- B
   * A B C -- C
   *
   * Shares A
   * Shares B
   * Shares C
   * A primary or secondary
   * B
   *
   * ABC
   * ACB
   * ABX
   * AXB
   * ACX
   * AXC
   * AXX
   * AB
   * AC
   * AX
   * A
   *
   *
   * X X X -- |||
   * X X $
   * X $ X
   * $ X X
   * X $ $
   * $ X $
   * $ $ X
   *
   * POSITION relative to initial
   * DELETE A - cost 80
   * DELETE B - cost 40
   * DELETE C - cost 20
   * INSERT X - cost 10
   *
   * A B C -- 000
   * A B _ -- 020
   * A B X -- 030
   * A C _ -- 040
   * A C X -- 050
   * B C _ -- 080
   * B C X -- 090
   * A _ _ -- 060
   * A X _ -- 070
   * A X X -- 080
   * B _ _ -- 100
   * B X _ -- 110
   * B X X -- 120
   * C _ _ -- 140
   * C X _ -- 150
   * C X X -- 160
   *
   *
   * A B C -- 000
   * A B _ -- 020
   * A B X -- 030
   * A C _ -- 040
   * A C X -- 050
   * A _ _ -- 060
   * A X _ -- 070
   * B C _ -- 080 * A X X -- 080
   * B C X -- 090
   * B _ _ -- 100
   * B X _ -- 110
   * B X X -- 120
   * C _ _ -- 140
   * C X _ -- 150
   * C X X -- 160
   *
   *
   * A B C -- 000
   * A C B -- 005
   * B A C --
   * C A B -- 015
   * B C A --
   * C B A --
   *
   * A B C
   * A C B
   * B A C
   * C A B
   * B C A
   * C B A
   * A B
   * A C
   * B A
   * C A
   * B C
   * C B
   * A
   * B
   * C
   *
   *
   */

  //akas
  private String titleId; //tconst
  private int ordering;
  private String title;
  private String region;
  private String language;
  private List<String> types;
  private List<String> attributes;
  private boolean isOriginalTitle;

  //basics
  private String titleType;
  private String primaryTitle;
  private String originalTitle;
  private boolean isAdult;
  private int startYear;
  private int endYear;
  private int runtimeMinutes; //TODO: determine if this is an int
  private List<Genre> genres;

  //crew
  private List<String> directors;
  private List<String> writers;

  //episode
  private String parentTconst;
  private int seasonNumber;
  private int episodeNumber;

  //principals
  //TODO: the format of this sql table is confusing

  //ratings
  private double averageRating;
  private int numVotes;

  public Title() {
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }


}
