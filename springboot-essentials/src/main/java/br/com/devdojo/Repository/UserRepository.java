package br.com.devdojo.Repository;


import br.com.devdojo.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username );

}
