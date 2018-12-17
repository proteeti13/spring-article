package org.springframework.samples.petclinic.articles;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;


@Controller
class ArticleController {

    private static final String VIEWS_ARTICLE_NEW = "articles/new";
    private final ArticleRepository articleRepository;


    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/articles/new")
    public String initCreationForm(Map<String, Object> viewBag) {
        Article article = new Article();
        viewBag.put("article", article);
        return VIEWS_ARTICLE_NEW;
    }

    @PostMapping("/articles/new")
    public String processCreationForm(@Valid Article article, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_ARTICLE_NEW;
        } else {
            this.articleRepository.save(article);
            return "redirect:/articles/new";
        }
    }

}
