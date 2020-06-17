package com.example.demo.Repository;

import com.example.demo.Model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Long> {
    String deleteFavouriteByUserIdAndPokemonId(Long userId, Long pokemonId);
    Favourite findByUserIdAndAndPokemonId(Long userId, Long pokemonId);
    List<Favourite> findAllByUserId(Long userId);

}
