package org.springframework.samples.petclinic.articles;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import java.nio.ByteBufferAsCharBufferRB;


@Controller
class ArticleController {

    private static final String VIEWS_ARTICLE_NEW = "articles/new";
    private final ArticleRepository articleRepository;

    //@Autowired
    //ArticleRepository articleRepository;


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
            return "redirect:/articles/view";
        }
    }

    @GetMapping("/articles/json")
    @ResponseBody
    public List<Article>  viewArticle(Map<String, Object> viewBag) {

        Iterable<Article> articles = articleRepository.findAll();
        List<Article> articles1 = new ArrayList<Article>();


        for (Article article:articles) {
            articles1.add(article);
        }
        return articles1;

    }

    @GetMapping("/articles/ajax")
    public String  viewArticlesAjax(Map<String, Object> viewBag) {

        return  "articles/list-ajax";

    }

}
