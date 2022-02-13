package com.project.getinline.controller.api;


import com.project.getinline.constant.PlaceType;
import com.project.getinline.dto.APIDataResponse;
import com.project.getinline.dto.EventRequest;
import com.project.getinline.dto.PlaceDTO;
import com.project.getinline.dto.PlaceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@RequestMapping("/api")
//@RestController
public class APIPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceDTO>> getPlaces(){

        return APIDataResponse.of(List.of(PlaceDTO.of(
                PlaceType.COMMON,
                "챔피언스 필드",
                "광주광역시 어딘가",
                "010-6515-0000",
                2400,
                "야구장"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<String> createEvent(@RequestBody PlaceRequest placeRequest){
        //log.debug("보고 싶은 값: {}", eventRequest);

        return APIDataResponse.of(Boolean.toString(true));
    }

//    @PostMapping("/places")
//    public Boolean createPlace(){
//        return true;
//    }
//
//    @GetMapping("/places/{placeId}")
//    public APIDataResponse<PlaceDTO> getPlace(@PathVariable Integer placeId){
//
//        if(placeId.equals(2)){
//            return APIDataResponse.of(null);
//        }
//
//        return APIDataResponse.of(PlaceDTO.of(
//                PlaceType.COMMON,
//                "챔피언스 필드",
//                "광주광역시 어딘가",
//                "010-6515-0000",
//                2400,
//                "야구장"
//        ));
//    }

    @PostMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId){
        return true;
    }

    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer placeId){
        return true;
    }

}
