package org.springframework.samples.petclinic.articles;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface ArticleRepository extends CrudRepository<Article, Integer> {

//    @Query("select id from petclinic.articles")
//    @Transactional(readOnly = true)
//    Optional<Article> findById(@Param("id") Integer id);

//    Article save(Article article);
}
