package com.emre.service;

import com.emre.repository.IPostResimRepository;
import com.emre.repository.entity.PostResim;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostResimService extends ServiceManager<PostResim,String> {

    private final IPostResimRepository postResimRepository;

    public PostResimService(IPostResimRepository postResimRepository) {
        super(postResimRepository);
        this.postResimRepository = postResimRepository;
    }
    public List<String> getUrlsByPostId(String postId) {
        List<PostResim> postResimList=postResimRepository.findAllByPostid(postId);
        List<String> urlList=new ArrayList<>();
        postResimList.forEach(x->{
            urlList.add(x.getUrl());
        });
        return urlList;
    }
}
