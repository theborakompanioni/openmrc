package com.github.theborakompanioni.openmrc;

import com.github.theborakompanioni.openmrc.web.OpenMrcHttpRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/openmrc")
public class OpenMrcTestCtrl {

    private static final Logger log = LoggerFactory.getLogger(SpringOpenMrcConfigurationSupport.class);

    private final OpenMrcHttpRequestService openMrcService;

    @Autowired
    public OpenMrcTestCtrl(OpenMrcHttpRequestService openMrcService) {
        this.openMrcService = openMrcService;
    }

    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    public ResponseEntity<Void> trackMapping(HttpServletRequest request) {
        try {
            HttpServletResponse response = openMrcService.apply(request)
                    .blockingSingle();
            return ResponseEntity.status(response.getStatus()).build();
        } catch (RuntimeException | Error e) {
            log.error("", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.warn("", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
