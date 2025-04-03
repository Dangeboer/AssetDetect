package com.intern.asset;

import com.intern.asset.model.UserEntity;
import com.intern.asset.model.UserRole;
import com.intern.asset.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DevRunner implements ApplicationRunner {
    static private final Logger logger = LoggerFactory.getLogger(DevRunner.class);

    private final UserRepository userRepository;

    public DevRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        generateSampleData();
    }

    private void generateSampleData() {

    }
}
