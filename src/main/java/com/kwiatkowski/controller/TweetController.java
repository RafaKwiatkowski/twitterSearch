package com.kwiatkowski.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Rafał on 2016-10-18.
 */
@Controller
public class TweetController {

    private Twitter twitter;

    @Autowired
    public TweetController(Twitter twitter) {
        this.twitter = twitter;
    }

    @GetMapping("/")
    public String home() {
        return "searchPage";
    }
    @GetMapping("/result")
    public String hello(@RequestParam(defaultValue =  "pokemon") String search,
                        Model model) {
        SearchResults searchResults = twitter.searchOperations().search(search);
        List<Tweet> tweets = searchResults.getTweets();
//        List<String> tweets = searchResults.getTweets()
//                .stream()
//                .map(Tweet::getText)
//                .collect(Collectors.toList());
        model.addAttribute("tweets", tweets);
        model.addAttribute("search", search);
        return "result";
    }
    @PostMapping(value = "/postSearch")
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String search = request.getParameter("search");
        if(search.toLowerCase().contains("error")) {
            redirectAttributes.addFlashAttribute("error", "Sprobuj wpisac inny wyraz!");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search", search);
        return "redirect:result";
    }
}
