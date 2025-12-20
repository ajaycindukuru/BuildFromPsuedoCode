package org.example.helper;

public class LoggingRefreshListener implements RefreshListener{
    @Override
    public void onRefreshComplete(long refreshCount) {
        System.out.println("Cache refresh completed. Entries refreshed " +refreshCount);
    }
}
