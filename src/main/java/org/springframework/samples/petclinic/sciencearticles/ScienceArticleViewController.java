package org.springframework.samples.petclinic.sciencearticles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;


@Controller
class ScienceArticleViewController {

    @GetMapping("/science-articles/view")
    public String getScienceArticlesNew(Map<String, Object> viewBag) {
       return "/science-articles/index";
    }

}
