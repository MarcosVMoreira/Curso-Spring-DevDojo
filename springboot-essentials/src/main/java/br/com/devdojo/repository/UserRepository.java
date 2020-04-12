package br.com.devdojo.repository;


import br.com.devdojo.model.DBUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<DBUser, Long> {

    DBUser findByUsername(String username );

}
