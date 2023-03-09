package com.iaan.vup.module.sample.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.iaan.vup.module.sample.domain.Account.AccountId;

import lombok.NonNull;

/**
 * @apiNote 한 계좌에 대한 모든 활동(activity)들을 항상 메모리에 한꺼번에 올리는 것은 현명하지 않으므로 지난 며칠 혹은 몇 주간의 범위 활동만 보유
 * */
public class ActivityWindow {
    // The list of account activities within this window.
    private List<Activity> activities;

    // The timestamp of the first activity within this window.
    public LocalDateTime getStartTimestamp() {
        return activities.stream()
                .min(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    // The timestamp of the last activity within this window.
    public LocalDateTime getEndTimestamp() {
        return activities.stream()
                .max(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    /**
     * Calculates the balance by summing up the values of all activities within this window.
     */
    public Money calculateBalance(AccountId accountId) {
        Money depositBalance = activities.stream()
                                        .filter(a -> a.getTargetAccountId().equals(accountId))
                                        .map(Activity::getMoney)
                                        .reduce(Money.ZERO, Money::add);

        Money withdrawalBalance = activities.stream()
                                        .filter(a -> a.getSourceAccountId().equals(accountId))
                                        .map(Activity::getMoney)
                                        .reduce(Money.ZERO, Money::add);

        return Money.add(depositBalance, withdrawalBalance.negate());
    }

    public ActivityWindow(@NonNull List<Activity> activities) {
        this.activities = activities;
    }

    public ActivityWindow(@NonNull Activity... activities) {
        this.activities = new ArrayList<>(Arrays.asList(activities));
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(this.activities);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

}
