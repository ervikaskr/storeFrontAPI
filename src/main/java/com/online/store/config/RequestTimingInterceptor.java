package com.online.store.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class RequestTimingInterceptor implements HandlerInterceptor {

    private final MeterRegistry meterRegistry;

    public RequestTimingInterceptor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", Instant.now());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Instant startTime = (Instant) request.getAttribute("startTime");
        long duration = Duration.between(startTime, Instant.now()).toMillis();
        String requestType = request.getMethod();
        Timer.builder("http.requests")
            .tag("type", requestType)
            .register(meterRegistry)
            .record(duration, TimeUnit.MILLISECONDS);
    }
}
