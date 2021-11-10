package com.IndieAn.GoFundIndie.Domain.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "genreId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardGenre> boardGenres = new ArrayList<>();

    public Genre() {}

    public List<BoardGenre> getBoardGenres() {
        return boardGenres;
    }

    public void setBoardGenres(List<BoardGenre> boardGenres) {
        this.boardGenres = boardGenres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
