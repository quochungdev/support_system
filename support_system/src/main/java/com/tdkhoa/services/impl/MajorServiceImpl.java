/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.services.impl;

import com.tdkhoa.pojo.Faculty;
import com.tdkhoa.pojo.Major;
import com.tdkhoa.repository.MajorRepository;
import com.tdkhoa.services.MajorService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    MajorRepository mjRepo;

    @Override
    public List<Major> getMajors() {
        return this.mjRepo.getMajors();
    }

    @Override
    public Major getMajorById(int major_id) {
        return this.mjRepo.getMajorById(major_id);
    }

    @Override
    public Major addOrUpdate(Map<String, String> params, Faculty f) {
        int majorId;
        Major major;
        if(params.get("id") != null) {
            majorId = Integer.parseInt(params.get("id"));
            major = this.mjRepo.getMajorById(majorId);
        }
        else {
            major = new Major();
        }
        if(params.containsKey("name")) {
            major.setName(params.get("name"));
        }
        if(params.containsKey("facultyId")) {
            major.setFacultyId(f);
        }
//        Major major = new Major();
//        
//        major.setName(params.get("name"));
//        major.setFacultyId(f);
        
        this.mjRepo.addOrUpdate(major);
        return major;
    }

    @Override
    public boolean deleteMajor(int major_id) {
        return this.mjRepo.deleteMajor(major_id);
    }

}
