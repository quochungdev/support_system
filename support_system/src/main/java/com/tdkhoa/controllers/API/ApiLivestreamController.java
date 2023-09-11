/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.controllers.API;

import com.tdkhoa.pojo.Faculty;
import com.tdkhoa.pojo.Livestream;
import com.tdkhoa.services.FacultyService;
import com.tdkhoa.services.LiveStreamService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@RestController
@RequestMapping("/api")
public class ApiLivestreamController {
    @Autowired 
    private LiveStreamService liveServ;
    @Autowired
    private FacultyService falcultyServ;
    
    @GetMapping("/livestreams/")
    @CrossOrigin
    public ResponseEntity<List<Livestream>> list() {
        return new ResponseEntity<>(this.liveServ.getListLivestreams(), HttpStatus.OK);
    }
    
    @PostMapping("/create_livestream/")
    @CrossOrigin
    public ResponseEntity<Livestream> add(@RequestParam Map<String, String> params, @RequestPart MultipartFile thumbnail) throws ParseException {
        System.out.println("Date: " + params.get("facultyId"));
        Faculty faculty = falcultyServ.getFacultyById(Integer.parseInt(params.get("facultyId")));
        System.out.println(faculty);
        System.out.println("Date: " + params.get("date"));
        
        Livestream liveS = this.liveServ.addOrUpdate(params, thumbnail, faculty);
        return new ResponseEntity<>(liveS, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/delete_livestream/{livestream_id}")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "livestream_id") int livestream_id) {
        this.liveServ.deleteLivestream(livestream_id);
    }
}
