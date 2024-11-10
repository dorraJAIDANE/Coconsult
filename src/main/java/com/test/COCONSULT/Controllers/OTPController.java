package com.test.COCONSULT.Controllers;

import com.test.COCONSULT.DTO.OTP;
import com.test.COCONSULT.Interfaces.OTPInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/OTP")
public class OTPController {
    @Autowired
    OTPInterface otpInterface;
@PostMapping("/GenerateOTp")
    public OTP GenerateOTp() {
        return otpInterface.GenerateOTp();
    }
@PostMapping("/VerifOTP/{identification}")
    public Boolean VerifOTP(@PathVariable   ("identification") String identification)  {
        return otpInterface.VerifOTP(identification);
    }
@PostMapping("/ResendOTP")
    public OTP ResendOTP(@RequestBody OTP existingOTP) {
        return otpInterface.ResendOTP(existingOTP);
    }
@DeleteMapping("/DeleteALLOTP")
    public void DeleteALLOTP() {
        otpInterface.DeleteALLOTP();
    }
}
