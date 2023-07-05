package com.cemonitor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wojciech Dębski
 * @date 07/05/2023
 **/

@Controller
public class LandingPageController {

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("pageTitle", "CEM - Crypto Exchange Monitor");
        model.addAttribute("pageContent", "Currently supported Cryptocurrencies: BTC. " +
                "\n" +
                "Currently supported currencies: USD, EUR" +
                "\n" +
                "Currently supported exchanges: Binance, Coinbase");

        return "landing-page.html";
    }

}
