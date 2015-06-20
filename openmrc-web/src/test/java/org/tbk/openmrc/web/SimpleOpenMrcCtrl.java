package org.tbk.openmrc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<Void> trackMapping(HttpServletRequest request) {
        try {
            HttpServletResponse response = openMrcService.apply(request);
            return ResponseEntity.status(response.getStatus()).build();
        } catch (RuntimeException | Error e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}