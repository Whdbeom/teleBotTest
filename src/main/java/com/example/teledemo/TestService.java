package com.example.teledemo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional
    public TestEntity saveMsg(TestEntity testEntity){
        System.out.println(testEntity);

        return testRepository.save(testEntity);
    }

}
