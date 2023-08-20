/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.pojo.Faculty;
import com.tdkhoa.repository.FacultyRepository;
import com.tdkhoa.services.FacultyService;
import java.io.IOException;
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
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    private FacultyRepository facultyRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Faculty addFaculty(Map<String, String> params, MultipartFile image) {
        Faculty faculty = new Faculty();
        
        faculty.setName(params.get("name"));
        faculty.setDescription(params.get("description"));
        faculty.setWebsiteUrl(params.get("website_url"));
        faculty.setImage_url(params.get("video_url"));
        
         if (!image.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                faculty.setImage_url(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.facultyRepo.addFaculty(faculty);
        return faculty;
    }

    @Override
    public List<Faculty> getFaculties() {
        return this.facultyRepo.getFaculties();
    }

    @Override
    public Faculty getFacultyById(int id) {
        return this.facultyRepo.getFacultyById(id);
    }

    @Override
    public boolean deleteFaculty(int id) {
        return this.facultyRepo.deleteFaculty(id);
    }
    
}
