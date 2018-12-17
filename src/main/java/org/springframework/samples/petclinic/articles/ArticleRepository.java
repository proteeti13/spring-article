package org.springframework.samples.petclinic.articles;

import org.springframework.data.repository.Repository;


public interface ArticleRepository extends Repository<Article, Integer> {

    void save(Article article);
}
