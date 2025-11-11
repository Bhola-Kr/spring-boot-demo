package com.example.demo.scheduler;

import com.example.demo.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    private final UserRepository userRepository;

    public UserScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Runs every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void printUserCount() {
        long count = userRepository.count();
        System.out.println("🕒 Total users in DB: " + count);
    }

    // ✅ Runs every day at midnight (example of CRON expression)
    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyReport() {
        System.out.println("📅 Running daily report at midnight...");
        // Add your logic — e.g., backup, cleanup, send email, etc.
    }
}
