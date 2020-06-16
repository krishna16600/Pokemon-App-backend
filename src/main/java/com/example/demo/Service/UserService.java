package com.example.demo.Service;

import com.example.demo.Model.Favourite;
import com.example.demo.Model.User;
import com.example.demo.Repository.FavouriteRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FavouriteRepository favRepo;


    public User addUserToDB(User user){
        List<User> allUsers = userRepo.findAll();
        for(User u: allUsers){
            if(u.getEmail().equals(user.getEmail()))
                return null;
        }
        userRepo.save(user);
        return user;
    }

    public User getUser(String email){
        return userRepo.findByEmail(email);
    }

    public String savePokemon(Long pokemonId, Long userId){
        if(favRepo.findByUserIdAndAndPokemonId(userId,pokemonId))
            return "\"Already Saved\"";

        Favourite fav = new Favourite();
        fav.setPokemonId(pokemonId);
        fav.setUserId(userId);
        favRepo.save(fav);
        return "\"Saved Pokemon\"";
    }

    public String deletePokemon(Long pokemonId, Principal principal){
        Long userId = getUser(principal.getName()).getUserId();
        System.out.println(userId);
        favRepo.deleteFavouriteByUserIdAndPokemonId(userId,pokemonId);
        return "\"Removed Pokemon\"";
    }

}
