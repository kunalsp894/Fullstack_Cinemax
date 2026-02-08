package com.cinemax.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String slug;        // e.g. "mirai"

    private String title;
    private String rating;
    private String duration;
    private String genres;

    @Column(length = 2000)
    private String synopsis;

    private String image;      // relative path e.g. images/mirai.avif
    private String trailer;    // trailer URL (optional)

    @Column(name = "cast_list", length = 1000)
    private String castList;   // comma-separated cast

    @Column(length = 1000)
    private String showtimes;  // comma-separated times e.g. "10:30 AM,1:45 PM"

    public Movie() {}

    public Movie(String slug, String title, String rating, String duration,
                 String genres, String synopsis, String image, String trailer,
                 String castList, String showtimes) {
        this.slug = slug;
        this.title = title;
        this.rating = rating;
        this.duration = duration;
        this.genres = genres;
        this.synopsis = synopsis;
        this.image = image;
        this.trailer = trailer;
        this.castList = castList;
        this.showtimes = showtimes;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getTrailer() { return trailer; }
    public void setTrailer(String trailer) { this.trailer = trailer; }

    public String getCastList() { return castList; }
    public void setCastList(String castList) { this.castList = castList; }

    public String getShowtimes() { return showtimes; }
    public void setShowtimes(String showtimes) { this.showtimes = showtimes; }
}
