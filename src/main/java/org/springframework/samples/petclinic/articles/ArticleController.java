package org.springframework.samples.petclinic.articles;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
//import java.nio.ByteBufferAsCharBufferRB;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;



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

    @GetMapping("/articles/view")
    public String viewArticle(Map<String, Object> viewBag) {

        Iterable<Article> articles = articleRepository.findAll();

        System.out.println(articles);
        //Todo: json output to task no 3
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(articles);
        System.out.println(json);

        viewBag.put("articles", articles);
        return "articles/view";

    }































}
