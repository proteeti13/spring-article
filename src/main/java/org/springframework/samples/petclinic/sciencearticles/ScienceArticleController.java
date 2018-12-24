package org.springframework.samples.petclinic.sciencearticles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.articles.Article;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController //Its adding @Controller and @ResponseBody behind the scene where @ResponseBody is responsible to deserialize using jackson rather google Gson
@RequestMapping("/science-articles")
class ScienceArticleController {

    @Autowired
    ScienceArticleRepository scienceArticleRepository;


    @GetMapping("")
    public List<Article> getAllArticles() {
        return (List<Article>) scienceArticleRepository.findAll();
    }

    @PostMapping("")
    public Article savArticle(@Valid @RequestBody Article article) {
        return scienceArticleRepository.save(article);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable(value = "id") Integer id) {
        return scienceArticleRepository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Article","id", id));
    }

    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable(value = "id") Integer id, @Valid @RequestBody Article article) {
        Article oldArticle = scienceArticleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));

        oldArticle.setTitle(article.getTitle());
        oldArticle.setDescription(article.getDescription());
        oldArticle.setAuthor(article.getAuthor());

        return scienceArticleRepository.save(oldArticle);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteArticle(@PathVariable(value = "id") Integer id) {
        Article oldArticle = scienceArticleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));

        scienceArticleRepository.delete(oldArticle);
       return true;
    }
}
