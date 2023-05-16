package com.tim23.webproject.service;

import com.tim23.webproject.repository.ZanrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZanrService {

    @Autowired
    private ZanrRepository zanrRepository;
}
