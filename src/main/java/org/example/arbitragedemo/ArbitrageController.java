package org.example.arbitragedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArbitrageController {

    @Autowired
    private ArbitrageService arbitrageService;

    @GetMapping(value = "")
    public String getArbitrage() {
        return "";
    }
}
