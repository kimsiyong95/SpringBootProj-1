package ksy.webproj.domain.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Item{

    private String author;
    private String isbn;
}
