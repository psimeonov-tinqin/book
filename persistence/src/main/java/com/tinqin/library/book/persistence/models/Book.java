package com.tinqin.library.book.persistence.models;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
@Table(name = "books")
public class Book {

  @Builder
  public Book(String title, Author author, String pages, BigDecimal price) {
    this.title = title;
    this.pages = pages;
    this.price = price;
    this.author = author;

    this.stock = 0;
    this.isDeleted = false;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToOne(fetch = FetchType.EAGER)
  private Author author;

  @Column(name = "pages", nullable = false)
  private String pages;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "stock", nullable = false)
  private Integer stock;

  @CreationTimestamp
  @Column(name = "createdAd", nullable = false)
  private LocalDateTime createdAd;

  @Column(name = "isDeleted")
  private Boolean isDeleted;


}
