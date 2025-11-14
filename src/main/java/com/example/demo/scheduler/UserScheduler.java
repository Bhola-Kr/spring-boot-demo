package com.example.demo.scheduler;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.NotificationService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    private final UserRepository userRepository;
  private final NotificationService notificationService;

    public UserScheduler(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    // ✅ Runs every 10 seconds
    @Scheduled(fixedRate = 60000)
    public void printUserCount() {
        long count = userRepository.count();
        notificationService.sendNotification("fzAILDsKahCNccIV9BFL-0:APA91bE7m2Lp0D_UgSw8qJ4U8gaaBFCOFPJnj1YKWYzt218C1CliGB_eq8w1jnG3pZ9GWCLse3bixLAc7DAbGpoltpS7QfupOJ2ofLF8LLhQhV9Ur4iF2j0","Notification from corn-jobs", "Total users in DB: " + count);
        System.out.println("🕒 Total users in DB: " + count);
    }

    // ✅ Runs every day at midnight (example of CRON expression)
    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyReport() {
        System.out.println("📅 Running daily report at midnight...");
        // Add your logic — e.g., backup, cleanup, send email, etc.
    }
}
