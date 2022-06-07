package ksy.webproj.domain.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends Item{

    private String director;
    private String actor;
}
