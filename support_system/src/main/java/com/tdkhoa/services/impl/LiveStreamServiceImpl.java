/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.pojo.Faculty;
import com.tdkhoa.pojo.Livestream;
import com.tdkhoa.repository.LiveStreamRepository;
import com.tdkhoa.services.LiveStreamService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Khoa Tran
 */
@Service
public class LiveStreamServiceImpl implements LiveStreamService {
    @Autowired
    private LiveStreamRepository liveRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Livestream> getListLivestreams() {
        return this.liveRepo.getListLivestreams();
    }

     @Override
    public Livestream addOrUpdate(Map<String, String> params, MultipartFile thumbnail, Faculty fal) {
        int liveId;
        Livestream liveS;
        if(params.get("id") != null) {
            liveId = Integer.parseInt(params.get("id"));
            liveS = liveRepo.getLivestreamById(liveId);
        }
        else {
            liveS = new Livestream();
            liveS.setTime(0.0);
        }
        if(params.containsKey("title")) {
            liveS.setTitle(params.get("title"));
        }

        if(params.containsKey("facultyId")) {
            liveS.setFacultyId(fal);
        }
        
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(params.get("date"));
        } catch (ParseException ex) {
            Logger.getLogger(LiveStreamServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(params.containsKey("date")) {
            liveS.setDate(date);
        }
        
        if (!thumbnail.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(thumbnail.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                liveS.setThumbnail(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.liveRepo.addOrUpdate(liveS);
//        return null;
        return liveS;
    }

    @Override
    public boolean deleteLivestream(int id) {
        return this.liveRepo.deleteLivestream(id);
    }

    @Override
    public Livestream getLivestreamById(int id) {
        return this.liveRepo.getLivestreamById(id);
    }
    
}
