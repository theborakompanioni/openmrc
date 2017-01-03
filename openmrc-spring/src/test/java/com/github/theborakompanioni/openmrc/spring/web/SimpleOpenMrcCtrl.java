package com.github.theborakompanioni.openmrc.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test/openmrc")
public class SimpleOpenMrcCtrl {

    private final OpenMrcHttpRequestService openMrcService;

    @Autowired
    public SimpleOpenMrcCtrl(OpenMrcHttpRequestService openMrcService) {
        this.openMrcService = openMrcService;
    }

    @RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello world");
    }

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    public ResponseEntity<String> trackMapping(HttpServletRequest request) {
        try {
            final ResponseEntity<String> response = openMrcService.apply(request).blockingSingle();
            return response;
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }
    }
}
