package com.eureka.consume;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {
    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/consume")
    public String consume(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("service-hi");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/cloudClient";
        System.out.println(url);
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/consumeActive")
    public String consumeActive() throws InterruptedException {
        Thread.sleep(5000L);
        System.out.println(123);
        return "consume";
    }
}
