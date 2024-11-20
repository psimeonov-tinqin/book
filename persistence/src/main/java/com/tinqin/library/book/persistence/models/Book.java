package com.tinqin.library.book.persistence.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "author", nullable = false)
  private UUID author;

  @Column(name = "pages", nullable = false)
  private String pages;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "stock", nullable = false)
  private Integer stock;

  @Column(name = "createdAd", nullable = false)
  private LocalDateTime createdAd;

  @Column(name = "isDeleted")
  private Boolean isDeleted;


}
