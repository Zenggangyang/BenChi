package com.soowin.staremblem.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by hxt on 2017/9/16.
 * activity 栈 管理类
 */

public class ScreenManager {
    private static Stack<Activity> activityStack;
    private static ScreenManager instance;

    private ScreenManager() {
    }

    public static ScreenManager getScreenManager() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * 关闭最后一个activity
     *
     * @param
     */
    public void popActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 在栈内移除一个activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获取栈顶的activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 向栈内添加一个activity
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除非当前activity和指定的activity外所有站内activity
     *
     * @param cls
     */
    /*public void popAllActivityExceptOne(Class cls) {//弃用
        while (activityStack.size() > 1) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }*/

    /**
     * 移除非指定的activity外所有站内activity
     *
     * @param cls
     */
    public void RemoveAllExceptedOne(Class cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
            } else {
                popActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 获取栈内actvity数量
     */
    public int getStackSize() {
        if (activityStack != null)
            return activityStack.size();
        return 0;
    }

    /**
     * 获取倒数第二个activity
     *
     * @return
     */
    public Activity getSecondToLast() {
        if (activityStack != null && activityStack.size() > 1) {
            return activityStack.get(activityStack.size() - 2);
        }
        return null;
    }
}
