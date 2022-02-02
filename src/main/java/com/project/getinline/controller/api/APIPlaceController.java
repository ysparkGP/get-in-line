package com.project.getinline.controller.api;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class APIPlaceController {

    @GetMapping("/places")
    public List<String> getPlaces(){
        return List.of("places1", "place2");
    }

    @PostMapping("/places")
    public Boolean createPlace(){
        return true;
    }

    @GetMapping("/places/{placeId}")
    public String getPlace(@PathVariable Integer placeId){
        return "place" + placeId;
    }

    @PostMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId){
        return true;
    }

    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer placeId){
        return true;
    }

}
