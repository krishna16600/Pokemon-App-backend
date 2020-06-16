package com.example.demo.Repository;

import com.example.demo.Model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Long> {
    String deleteFavouriteByUserIdAndPokemonId(Long userId, Long pokemonId);
    Boolean findByUserIdAndAndPokemonId(Long userId, Long pokemonId);

}
