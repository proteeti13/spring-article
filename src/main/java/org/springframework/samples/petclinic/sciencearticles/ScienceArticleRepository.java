package org.springframework.samples.petclinic.sciencearticles;


import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.articles.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceArticleRepository extends CrudRepository<Article, Integer> {


}
